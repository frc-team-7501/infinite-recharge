package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;

public class AlignTargetCommand extends PIDCommand {
  private DriveTrain driveTrain;
  
  /**
   * Creates a new AlignTargetCommand.
   */
  public AlignTargetCommand(DriveTrain driveTrain) {
    super(
      // The controller that the command will use
      new PIDController(0.04, 0, 0.005),
      // This should return the measurement
      () -> {
        if (!driveTrain.getLimelightTargetValid()) {
          return 10.0;
        }
        return driveTrain.getLimelightX();
      },
      // This should return the setpoint (can also be a constant)
      () -> 0,
      // This uses the output
      output -> {
        driveTrain.arcadeDrive(-output, 0);
      }
    );

    getController().setTolerance(
      1);

    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return false;
    return getController().atSetpoint();
  }
}
