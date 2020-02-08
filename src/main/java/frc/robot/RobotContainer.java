package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AlignTargetCommand;
import frc.robot.commands.ShooterRampUpCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  // Create joysticks
  private final Joystick stick            = new Joystick(1);
  private final XboxController controller = new XboxController(2);
  // Create subsystems
  private final DriveTrain driveTrain = new DriveTrain();
  private final Limelight limelight   = new Limelight();
  private final Shooter shooter       = new Shooter();

  // Create commands
  private final TeleopDriveCommand teleopDriveCommand     = new TeleopDriveCommand(driveTrain, () -> stick.getZ(), () -> stick.getY());
  private final AlignTargetCommand alignTargetCommand     = new AlignTargetCommand(driveTrain, limelight);
  private final ShooterRampUpCommand shooterRampUpCommand = new ShooterRampUpCommand(shooter);

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
    var stickAlignButton    = new JoystickButton(stick, 2);

    stickTriggerButton
      .whenPressed(()   -> teleopDriveCommand.setSpeedCoef(Constants.drivetrainBoostSpeedCoef))
      .whenReleased(()  -> teleopDriveCommand.setSpeedCoef(Constants.drivetrainDefaultSpeedCoef));

    stickAlignButton
      .whenHeld(alignTargetCommand);

    // Controller buttons
    var controllerAButton   = new JoystickButton(controller, 1);
    
    controllerAButton
      .whenHeld(shooterRampUpCommand);
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
