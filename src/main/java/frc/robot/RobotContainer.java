package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.AlignTargetCommand;
import frc.robot.commands.ClimbWinchUpManualCommand;
import frc.robot.commands.ControlPanelPositionCommand;
import frc.robot.commands.ControlPanelRotationCommand;
import frc.robot.commands.ConveyorBottomMoveCommand;
import frc.robot.commands.ConveyorTopMoveCommand;
import frc.robot.commands.IntakeArmManualCommand;
import frc.robot.commands.ShooterRampUpCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.ConveyorBottom;
import frc.robot.subsystems.ConveyorTop;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
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
  private final AlignTargetCommand          alignTargetCommand          = new AlignTargetCommand(driveTrain, limelight);
  private final ShooterRampUpCommand        shooterRampUpCommand        = new ShooterRampUpCommand(shooter);
  private final ControlPanelPositionCommand controlPanelPositionCommand = new ControlPanelPositionCommand(controlPanel);
  private final ControlPanelRotationCommand controlPanelRotationCommand = new ControlPanelRotationCommand(controlPanel);
  private final ConveyorTopMoveCommand      conveyorTopMoveCommand      = new ConveyorTopMoveCommand(conveyorTop, () -> controller.getTriggerAxis(Hand.kLeft) * 0.5 * (controller.getRawButton(5) ? -1 : 1));
  private final ConveyorBottomMoveCommand   conveyorBottomMoveCommand   = new ConveyorBottomMoveCommand(conveyorBottom, () -> controller.getTriggerAxis(Hand.kRight) * (controller.getRawButton(6) ? -1 : 1));
  private final IntakeArmManualCommand      intakeArmManualCommand      = new IntakeArmManualCommand(intakeArm, () -> controller.getY(Hand.kLeft) * 0.25);
  private final ClimbWinchUpManualCommand   climbWinchUpManualCommand   = new ClimbWinchUpManualCommand(climber, () -> controller.getY(Hand.kRight));

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
      .whileActiveOnce(alignTargetCommand);

    // ===========================================================
    // Controller buttons
    // ===========================================================

    var controllerAButton     = new JoystickButton(controller, 1);
    var controllerBackButton  = new JoystickButton(controller, 7);
    var controllerStartButton = new JoystickButton(controller, 8);
    
    controllerAButton
      .whenHeld(shooterRampUpCommand);
    controllerBackButton
      .whenPressed(controlPanelPositionCommand);
    controllerStartButton
      .whenPressed(controlPanelRotationCommand);
  }

  /**
   * Set the default commands for subsystems.
   */
  private void setDefaultCommands() {
    driveTrain.setDefaultCommand(teleopDriveCommand);
    intakeArm.setDefaultCommand(intakeArmManualCommand); // TODO: this is for debugging only
    climber.setDefaultCommand(climbWinchUpManualCommand);
    conveyorBottom.setDefaultCommand(conveyorBottomMoveCommand);
    conveyorTop.setDefaultCommand(conveyorTopMoveCommand);
  }

  /**
   * Returns the selected autonomous command.
   * @return
   */
  public Command getAutonomousCommand() {
    // TODO
    return null;
  }
}
