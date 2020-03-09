package org.frc7501.robot;

import org.frc7501.robot.commands.autonomous.ControlPanelPosition;
import org.frc7501.robot.commands.autonomous.ControlPanelRotation;
import org.frc7501.robot.commands.autonomous.ConveyorManage;
import org.frc7501.robot.commands.autonomous.IntakeArmDown;
import org.frc7501.robot.commands.autonomous.IntakeArmUp;
import org.frc7501.robot.commands.autonomous.LimelightAlignTarget;
import org.frc7501.robot.commands.autonomous.ShooterFire;
import org.frc7501.robot.commands.manual.ClimberControl;
import org.frc7501.robot.commands.manual.TeleopDrive;
import org.frc7501.robot.subsystems.Climber;
import org.frc7501.robot.subsystems.ControlPanel;
import org.frc7501.robot.subsystems.Conveyor;
import org.frc7501.robot.subsystems.DriveTrain;
import org.frc7501.robot.subsystems.IntakeArm;
import org.frc7501.robot.subsystems.Limelight;
import org.frc7501.robot.subsystems.Shooter;
import org.team696.TCA9548ADriver.TCA9548A;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  // I2C Multiplexer (static)
  public static final TCA9548A mux = new TCA9548A(I2C.Port.kOnboard, 0x70);

  // Create joysticks
  private final Joystick        stick       = new Joystick(0);
  private final XboxController  controller  = new XboxController(1);
  // Create subsystems
  private final DriveTrain      driveTrain      = new DriveTrain();
  private final Limelight       limelight       = new Limelight();
  private final Shooter         shooter         = new Shooter();
  private final ControlPanel    controlPanel    = new ControlPanel();
  private final IntakeArm       intakeArm       = new IntakeArm();
  private final Climber         climber         = new Climber();
  private final Conveyor        conveyor        = new Conveyor();

  // Create commands
  private final TeleopDrive           teleopDriveCommand          = new TeleopDrive(driveTrain, () -> -stick.getY(), () -> stick.getX(), () -> stick.getRawButton(1), () -> 1 - stick.getThrottle());
  private final LimelightAlignTarget  limelightAlignTargetCommand = new LimelightAlignTarget(driveTrain, limelight);
  private final ControlPanelPosition  controlPanelPositionCommand = new ControlPanelPosition(controlPanel);
  private final ControlPanelRotation  controlPanelRotationCommand = new ControlPanelRotation(controlPanel);
  private final ClimberControl        climberControlCommand       = new ClimberControl(climber, () -> controller.getY(Hand.kLeft), () -> controller.getY(Hand.kRight) * 0.25);
  private final IntakeArmUp           intakeArmUpCommand          = new IntakeArmUp(intakeArm);
  private final IntakeArmDown         intakeArmDownCommand        = new IntakeArmDown(intakeArm);

  // Autonomous
  private final SequentialCommandGroup autonRight = new SequentialCommandGroup(
    new LimelightAlignTarget(driveTrain, limelight),
    new ShooterFire(shooter, conveyor),
    new ScheduleCommand(new IntakeArmDown(intakeArm))
  );

  // Intake/Conveyor state management
  private final SequentialCommandGroup intakeCommandGroup = new SequentialCommandGroup(
    new ParallelCommandGroup(
      new IntakeArmDown(intakeArm),
      new ConveyorManage(conveyor)
    ),
    new IntakeArmUp(intakeArm)
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

    // NOTE: stick button 1 is used inline in the drive command (see above)
    // final var stickThumbButton = new JoystickButton(stick, 2);

    // stickThumbButton
    // .whileActiveOnce(limelightAlignTargetCommand);

    // ===========================================================
    // Controller buttons
    // ===========================================================

    final var controllerAButton = new JoystickButton(controller, 1);
    final var controllerBButton = new JoystickButton(controller, 2);
    final var controllerXButton = new JoystickButton(controller, 3);
    final var controllerYButton = new JoystickButton(controller, 4);
    final var controllerLBButton = new JoystickButton(controller, 5);
    // final var controllerRBButton = new JoystickButton(controller, 6);
    final var controllerBackButton = new JoystickButton(controller, 7);
    final var controllerStartButton = new JoystickButton(controller, 8);
    
    controllerAButton
      .toggleWhenPressed(intakeCommandGroup);
    controllerBButton
      .whileActiveOnce(limelightAlignTargetCommand);
    controllerXButton
      .whenPressed(intakeArmDownCommand);
    controllerYButton
      .whenPressed(intakeArmUpCommand);

    controllerLBButton
      .whileActiveOnce(new ShooterFire(shooter, conveyor));

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
   * @return
   */
  public Command getAutonomousCommand() {
    return autonRight;
  }
}
