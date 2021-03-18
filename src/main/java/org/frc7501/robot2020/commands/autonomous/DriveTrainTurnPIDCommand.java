package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpiutil.math.MathUtil;

public class DriveTrainTurnPIDCommand extends PIDCommand {
  private final DriveTrain driveTrain;
  private double relativeSetpoint = 0;

private static double normalizedAngle(double angle) {
  return ((angle % 360) + 360) % 360;
}

  public DriveTrainTurnPIDCommand(DriveTrain driveTrain, double setpoint) {
    super(
      // PID controller
      new PIDController(0.035, 0.003, 0.015),
      // Measurement
      () -> normalizedAngle(driveTrain.getGyroYaw()), 
      // () -> limelight.validTarget() ? limelight.getNormalXOffset() -0.11 : 0.4,
      // The PID setpoint (0 so we can center the bot)
      0,
      // Output consumer
      // output -> driveTrain.curvatureDrive(0, -output, true)
      output -> {
        double clamped = MathUtil.clamp(output, -0.5, 0.5);
        SmartDashboard.putNumber("clamped", -clamped);
        SmartDashboard.putNumber("output", output);
        driveTrain.curvatureDrive(0, -clamped, true);
      }
    );

getController().setIntegratorRange(-0.5, 0.5);
getController().enableContinuousInput(0, 360);

    addRequirements(driveTrain);
    this.driveTrain = driveTrain;
    this.relativeSetpoint = setpoint;

    // Tune the PID
    getController().setTolerance(0.8, 15);
  }

  @Override
  public void initialize() {
    super.initialize();
    getController().setSetpoint(normalizedAngle(driveTrain.getGyroYaw() + relativeSetpoint));
    SmartDashboard.putNumber("setpoint", normalizedAngle(driveTrain.getGyroYaw() + relativeSetpoint));
  }

@Override
public void execute() {
  m_useOutput.accept(m_controller.calculate(m_measurement.getAsDouble(), m_controller.getSetpoint()));
}

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
