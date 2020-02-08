package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.LEDState;

public class AlignTargetCommand extends PIDCommand {
  // private final DriveTrain driveTrain;
  private final Limelight limelight;
  
  public AlignTargetCommand(DriveTrain driveTrain, Limelight limelight) {
    super(
      // PID controller
      new PIDController(0.04, 0, 0.005),
      // Measurement
      () -> limelight.getValidTarget() ? limelight.getRawXOffset() : 10.0,
      // The PID setpoint (0 so we can center the bot)
      () -> 0,
      // Output consumer
      output -> driveTrain.arcadeDrive(-output, 0)
    );

    // this.driveTrain = driveTrain; TODO: assignment is unneeded
    this.limelight  = limelight;
    addRequirements(driveTrain, limelight);

    // Tune the PID
    getController().setTolerance(1);
  }

  @Override
  public void execute() {
    limelight.setLEDState(LEDState.kSolid);
    super.execute();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
