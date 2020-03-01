package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
      new PIDController(0.025, 0.00029, 0.0019),
      // Measurement
      () -> limelight.validTarget() ? limelight.getRawXOffset() : 10.0,
      // The PID setpoint (0 so we can center the bot)
      () -> 0,
      // Output consumer
      output -> driveTrain.arcadeDrive(-Math.copySign(Math.min(Math.abs(output), 0.4), output), 0)
    );

    this.limelight = limelight;
    addRequirements(driveTrain, limelight);

    // Tune the PID
    getController().setTolerance(0.1);
  }

  @Override
  public void initialize() {
    super.initialize();
    limelight.setLEDState(LEDState.kSolid);
    SmartDashboard.putNumber("LL P", getController().getP());
    SmartDashboard.putNumber("LL I", getController().getI());
    SmartDashboard.putNumber("LL D", getController().getD());
  }

  @Override
  public void execute() {
    double p, i, d;
    p = SmartDashboard.getNumber("LL P", getController().getP());
    i = SmartDashboard.getNumber("LL I", getController().getI());
    d = SmartDashboard.getNumber("LL D", getController().getD());
    getController().setPID(p, i, d);
    super.execute();
  }

  @Override
  public void end(boolean interrupted) {
    // limelight.setLEDState(LEDState.kOff);
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
