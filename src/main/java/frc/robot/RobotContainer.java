package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AlignTargetCommand;
import frc.robot.commands.ControlPanelPositionCommand;
import frc.robot.commands.ControlPanelRotationCommand;
import frc.robot.commands.ConveyorFeedCommand;
import frc.robot.commands.ShooterRampUpCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  // Create joysticks
  private final Joystick stick = new Joystick(1);
  private final XboxController controller = new XboxController(2);
  // Create subsystems
  private final DriveTrain driveTrain = new DriveTrain();
  private final Limelight limelight = new Limelight();
  private final Shooter shooter = new Shooter();
  private final ControlPanel controlPanel = new ControlPanel();
  private final Conveyor conveyor = new Conveyor();

  // Create commands
  private final TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand(driveTrain, () -> stick.getX(),
      () -> -stick.getY());
  private final AlignTargetCommand alignTargetCommand = new AlignTargetCommand(driveTrain, limelight);
  private final ShooterRampUpCommand shooterRampUpCommand = new ShooterRampUpCommand(shooter);
  private final ControlPanelPositionCommand controlPanelPositionCommand = new ControlPanelPositionCommand(controlPanel);
  private final ControlPanelRotationCommand controlPanelRotationCommand = new ControlPanelRotationCommand(controlPanel);
  private final ConveyorFeedCommand conveyorFeedCommand = new ConveyorFeedCommand(conveyor);

  public RobotContainer() {
    configureButtonBindings();
    setDefaultCommands();
  }

  /**
   * Attach joystick buttons to commands.
   */
  private void configureButtonBindings() {
    // Joystick buttons
    var stickTriggerButton  = new JoystickButton(stick, 1);
    var stickThumbButton    = new JoystickButton(stick, 2);

    stickTriggerButton
      .whenPressed(()   -> teleopDriveCommand.setSpeedCoef(Constants.drivetrainBoostSpeedCoef))
      .whenReleased(()  -> teleopDriveCommand.setSpeedCoef(Constants.drivetrainDefaultSpeedCoef));

    stickThumbButton
      .whileActiveOnce(alignTargetCommand);

    // Controller buttons
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
