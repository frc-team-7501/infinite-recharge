package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.DriveTrain;

public class RobotContainer {
  // Create joysticks
  private final Joystick stick = new Joystick(1);
  private final XboxController controller = new XboxController(2);
  // Create subsystems
  private DriveTrain driveTrain = new DriveTrain();
  // Create commands
  private TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand(driveTrain, stick::getX, stick::getY);
  
  public RobotContainer() {
    configureButtonBindings();
    setDefaultCommands();
  }

  /**
   * Attach joystick buttons to commands.
   */
  private void configureButtonBindings() {
    // TODO
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
