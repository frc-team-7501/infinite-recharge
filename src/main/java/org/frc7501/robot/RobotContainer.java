package org.frc7501.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import org.team696.TCA9548ADriver.TCA9548A;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.frc7501.robot.commands.ConveyorBottomMove;
import org.frc7501.robot.commands.ConveyorTopMove;
import org.frc7501.robot.commands.ShooterFireManual;
import org.frc7501.robot.commands.autonomous.ControlPanelPosition;
import org.frc7501.robot.commands.autonomous.ControlPanelRotation;
import org.frc7501.robot.commands.autonomous.IntakeArmDownInfinite;
import org.frc7501.robot.commands.autonomous.IntakeArmUp;
import org.frc7501.robot.commands.autonomous.LimelightAlignTarget;
import org.frc7501.robot.commands.autonomous.ShooterFireInfinite;
import org.frc7501.robot.commands.manual.ClimberControl;
import org.frc7501.robot.commands.manual.TeleopDrive;
import org.frc7501.robot.subsystems.Climber;
import org.frc7501.robot.subsystems.ControlPanel;
import org.frc7501.robot.subsystems.ConveyorBottom;
import org.frc7501.robot.subsystems.ConveyorTop;
import org.frc7501.robot.subsystems.DriveTrain;
import org.frc7501.robot.subsystems.IntakeArm;
import org.frc7501.robot.subsystems.Limelight;
import org.frc7501.robot.subsystems.Shooter;

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
  private final ConveyorTop     conveyorTop     = new ConveyorTop();
  private final ConveyorBottom  conveyorBottom  = new ConveyorBottom();
  private final IntakeArm       intakeArm       = new IntakeArm();
  private final Climber         climber         = new Climber();

  // Create commands
  private final TeleopDrive           teleopDriveCommand          = new TeleopDrive(driveTrain, () -> -stick.getY(), () -> stick.getX(), () -> stick.getRawButton(1), () -> 1 - stick.getThrottle());
  private final LimelightAlignTarget  limelightAlignTargetCommand = new LimelightAlignTarget(driveTrain, limelight);
  private final ShooterFireManual     shooterFireManualCommand    = new ShooterFireManual(shooter);
  private final ControlPanelPosition  controlPanelPositionCommand = new ControlPanelPosition(controlPanel);
  private final ControlPanelRotation  controlPanelRotationCommand = new ControlPanelRotation(controlPanel);
  private final ConveyorTopMove       conveyorTopMoveCommand      = new ConveyorTopMove(conveyorTop, () -> controller.getTriggerAxis(Hand.kLeft) * 0.5 * (controller.getRawButton(5) ? -1 : 1));
  private final ConveyorBottomMove    conveyorBottomMoveCommand   = new ConveyorBottomMove(conveyorBottom, () -> controller.getTriggerAxis(Hand.kRight) * 0.5 * (controller.getRawButton(6) ? -1 : 1));
  private final ClimberControl        climberControlCommand       = new ClimberControl(climber, () -> controller.getY(Hand.kLeft), () -> controller.getY(Hand.kRight) * 0.25);
  private final IntakeArmUp           intakeArmUpCommand          = new IntakeArmUp(intakeArm);
  private final IntakeArmDownInfinite intakeArmDownCommand        = new IntakeArmDownInfinite(intakeArm);

  // Autonomous
  private final SequentialCommandGroup autonRight = new SequentialCommandGroup(
    new ParallelRaceGroup(
      new ShooterFireInfinite(shooter),                     // Start shooter motor
      new IntakeArmDownInfinite(intakeArm),                 // Lower the intake
      new SequentialCommandGroup(
        new LimelightAlignTarget(driveTrain, limelight),    // Align the limelight
        new ParallelDeadlineGroup(new WaitCommand(0.5),     // Wait 0.5s for shooter to get up to speed
          new ConveyorBottomMove(conveyorBottom, () -> 1),  // Advance the bottom conveyor
          new ConveyorTopMove(conveyorTop, () -> 1)         // Advance the top conveyor
        )
      )
    ),
    new IntakeArmUp(intakeArm)                              // Bring the intake arm up
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

    var stickThumbButton = new JoystickButton(stick, 2);

    stickThumbButton
      .whileActiveOnce(limelightAlignTargetCommand);

    // ===========================================================
    // Controller buttons
    // ===========================================================

    var controllerAButton     = new JoystickButton(controller, 1);
    var controllerBButton     = new JoystickButton(controller, 2);
    var controllerXButton     = new JoystickButton(controller, 3);
    var controllerYButton     = new JoystickButton(controller, 4);
    var controllerBackButton  = new JoystickButton(controller, 7);
    var controllerStartButton = new JoystickButton(controller, 8);
    
    controllerAButton
      .whenHeld(shooterFireManualCommand);
    controllerXButton
      .whenPressed(intakeArmDownCommand);
    controllerYButton
      .whenPressed(intakeArmUpCommand);

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
    conveyorBottom
      .setDefaultCommand(conveyorBottomMoveCommand);
    conveyorTop
      .setDefaultCommand(conveyorTopMoveCommand);
    driveTrain
      .setDefaultCommand(teleopDriveCommand);
  }

  /**
   * Returns the selected autonomous command.
   * @return
   */
  public Command getAutonomousCommand() {
    // TODO
    return autonRight;
    // return null;
  }
}
