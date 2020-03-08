package org.frc7501.robot.commands.autonomous;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import org.frc7501.robot.subsystems.DriveTrain;
import org.frc7501.robot.subsystems.Limelight;
import org.frc7501.robot.subsystems.Limelight.LEDState;

public class LimelightAlignTarget extends PIDCommand {
  // private final DriveTrain driveTrain;
  private final Limelight limelight;
  
  public LimelightAlignTarget(DriveTrain driveTrain, Limelight limelight) {
    super(
      // PID controller
      new PIDController(0.075, 0.001, 0.00),
      // Measurement
      () -> limelight.validTarget() ? limelight.getRawXOffset() : 7.0,
      // The PID setpoint (0 so we can center the bot)
      () -> 0,
      // Output consumer
      // output -> driveTrain.curvatureDrive(0, -output, true)
      output -> driveTrain.curvatureDrive(0, -output / 2, true)
    );

    this.limelight = limelight;
    addRequirements(driveTrain, limelight);

    // Tune the PID
    getController().setTolerance(0.05);
  }

  @Override
  public void initialize() {
    super.initialize();
    limelight.setLEDState(LEDState.kSolid);
  }

  @Override
  public void end(boolean interrupted) {
    limelight.setLEDState(LEDState.kOff);
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
