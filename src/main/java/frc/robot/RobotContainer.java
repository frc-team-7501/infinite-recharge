package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ConveyorBottomMoveCommand;
import frc.robot.commands.ConveyorTopMoveCommand;
import frc.robot.commands.ShooterFireManual;
import frc.robot.commands.autonomous.ControlPanelPositionCommand;
import frc.robot.commands.autonomous.ControlPanelRotationCommand;
import frc.robot.commands.autonomous.IntakeArmDown;
import frc.robot.commands.autonomous.IntakeArmUp;
import frc.robot.commands.autonomous.LimelightAlignTarget;
import frc.robot.commands.autonomous.ShooterFireInfinite;
import frc.robot.commands.manual.ClimberControlCommand;
import frc.robot.commands.manual.TeleopDriveCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.ConveyorBottom;
import frc.robot.subsystems.ConveyorTop;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.i2c.TCA9548A;

public class RobotContainer {
  // I2C Multiplexer (static)
  public static final TCA9548A  multiplexer = new TCA9548A();

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
  private final TeleopDriveCommand          teleopDriveCommand          = new TeleopDriveCommand(driveTrain, () -> -stick.getY(), () -> stick.getX(), () -> stick.getRawButton(1), () -> 1 - stick.getThrottle());
  private final LimelightAlignTarget        limelightAlignTargetCommand = new LimelightAlignTarget(driveTrain, limelight);
  private final ShooterFireManual           shooterFireManualCommand    = new ShooterFireManual(shooter);
  private final ControlPanelPositionCommand controlPanelPositionCommand = new ControlPanelPositionCommand(controlPanel);
  private final ControlPanelRotationCommand controlPanelRotationCommand = new ControlPanelRotationCommand(controlPanel);
  private final ConveyorTopMoveCommand      conveyorTopMoveCommand      = new ConveyorTopMoveCommand(conveyorTop, () -> controller.getTriggerAxis(Hand.kLeft) * 0.5 * (controller.getRawButton(5) ? -1 : 1));
  private final ConveyorBottomMoveCommand   conveyorBottomMoveCommand   = new ConveyorBottomMoveCommand(conveyorBottom, () -> controller.getTriggerAxis(Hand.kRight) * 0.5 * (controller.getRawButton(6) ? -1 : 1));
  // private final ClimberControlCommand       climberControlCommand       = new ClimberControlCommand(climber, () -> controller.getY(Hand.kLeft), () -> controller.getY(Hand.kRight));
  private final IntakeArmUp                 intakeArmUpCommand          = new IntakeArmUp(intakeArm);
  private final IntakeArmDown               intakeArmDownCommand        = new IntakeArmDown(intakeArm);

  // Autonomous
  private final SequentialCommandGroup autonRight = new SequentialCommandGroup(
    new IntakeArmDown(intakeArm),
    new ParallelRaceGroup(
      new ShooterFireInfinite(shooter),
      new SequentialCommandGroup(
        new LimelightAlignTarget(driveTrain, limelight),
        new ParallelDeadlineGroup(new WaitCommand(1.0),
          // new ConveyorBottomMoveCommand(conveyorBottom, () -> 1),
          new ConveyorTopMoveCommand(conveyorTop, () -> 1)
        )
      )
    )
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
    // climber
    //   .setDefaultCommand(climberControlCommand);
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
  }
}
