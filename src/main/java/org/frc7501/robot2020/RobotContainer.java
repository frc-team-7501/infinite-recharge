package org.frc7501.robot2020;

import org.frc7501.robot2020.commands.autonomous.ControlPanelPosition;
import org.frc7501.robot2020.commands.autonomous.ControlPanelRotation;
import org.frc7501.robot2020.commands.autonomous.ConveyorIntake;
import org.frc7501.robot2020.commands.autonomous.IntakeArmDown;
import org.frc7501.robot2020.commands.autonomous.IntakeArmUp;
import org.frc7501.robot2020.commands.autonomous.LimelightAlignTarget;
import org.frc7501.robot2020.commands.autonomous.ShooterFire;
import org.frc7501.robot2020.commands.manual.ClimberControl;
import org.frc7501.robot2020.commands.manual.TeleopDrive;
import org.frc7501.robot2020.subsystems.Climber;
import org.frc7501.robot2020.subsystems.ControlPanel;
import org.frc7501.robot2020.subsystems.Conveyor;
import org.frc7501.robot2020.subsystems.DriveTrain;
import org.frc7501.robot2020.subsystems.IntakeArm;
import org.frc7501.robot2020.subsystems.Limelight;
import org.frc7501.robot2020.subsystems.Shooter;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;



public class RobotContainer {
  // Create joysticks
  private final Joystick        stick           = new Joystick(0);
  private final XboxController  controller      = new XboxController(1);

  // Create subsystems
  private final DriveTrain      driveTrain      = new DriveTrain();
  private final Limelight       limelight       = new Limelight();
  private final Shooter         shooter         = new Shooter();
  private final ControlPanel    controlPanel    = new ControlPanel();
  private final IntakeArm       intakeArm       = new IntakeArm();
  private final Climber         climber         = new Climber();
  private final Conveyor        conveyor        = new Conveyor();

  // Create commands
  private final ClimberControl        climberControlCommand       = new ClimberControl(climber, () -> controller.getY(Hand.kLeft), () -> controller.getY(Hand.kRight) * 0.25);
  private final ControlPanelPosition  controlPanelPositionCommand = new ControlPanelPosition(controlPanel);
  private final ControlPanelRotation  controlPanelRotationCommand = new ControlPanelRotation(controlPanel);
  private final ConveyorIntake        conveyorIntakeCommand       = new ConveyorIntake(conveyor);
  private final IntakeArmDown         intakeArmDownCommand        = new IntakeArmDown(intakeArm);
  private final IntakeArmUp           intakeArmUpCommand          = new IntakeArmUp(intakeArm);
  private final LimelightAlignTarget  limelightAlignTargetCommand = new LimelightAlignTarget(driveTrain, limelight);
  private final TeleopDrive           teleopDriveCommand          = new TeleopDrive(driveTrain, () -> -stick.getY(), () -> stick.getX(), () -> stick.getRawButton(1), () -> 1 - stick.getThrottle());

  // Autonomous
  private final SequentialCommandGroup autonRight = new SequentialCommandGroup(
    new LimelightAlignTarget(driveTrain, limelight),
    new ShooterFire(shooter, conveyor),
    new ScheduleCommand(new IntakeArmDown(intakeArm)),
    new ParallelDeadlineGroup(new WaitCommand(0.1), new TeleopDrive(driveTrain, () -> 0.25, () -> 0, () -> false, () -> 1))
  );

  public RobotContainer() {
    configureButtonBindings();
    setDefaultCommands();
  }

  /**
   * Attach joystick buttons to commands.
   */
  private void configureButtonBindings() {
    // ===========================================================
    // Joystick buttons
    // ===========================================================

    // NOTE: stick button 1 (triiger) is used inline in the drive command (see above)
    final var stickThumbButton = new JoystickButton(stick, 2);

    stickThumbButton
    .whileActiveOnce(limelightAlignTargetCommand);

    // ===========================================================
    // Controller buttons
    // ===========================================================

    final var controllerAButton     = new JoystickButton(controller, 1);
    final var controllerBButton     = new JoystickButton(controller, 2);
    final var controllerXButton     = new JoystickButton(controller, 3);
    final var controllerYButton     = new JoystickButton(controller, 4);
    final var controllerLBButton    = new JoystickButton(controller, 5);
    final var controllerRBButton    = new JoystickButton(controller, 6);
    final var controllerBackButton  = new JoystickButton(controller, 7);
    final var controllerStartButton = new JoystickButton(controller, 8);
    

    controllerAButton
      .whileActiveOnce(new ShooterFire(shooter, conveyor));
    controllerBButton
      .whileActiveOnce(intakeArmDownCommand.andThen(conveyorIntakeCommand))
      .whenInactive(intakeArmUpCommand);

    controllerBackButton
      .whenPressed(controlPanelPositionCommand);
    controllerStartButton
      .whenPressed(controlPanelRotationCommand);
  }

  /**
   * Set the default commands for subsystems.
   */
  private void setDefaultCommands() {
    climber
      .setDefaultCommand(climberControlCommand);
    driveTrain
      .setDefaultCommand(teleopDriveCommand);
  }

  /**
   * Returns the selected autonomous command.
   */
  public Command getAutonomousCommand() {
    return autonRight;
  }
}
