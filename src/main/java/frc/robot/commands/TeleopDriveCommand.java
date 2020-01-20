package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class TeleopDriveCommand extends CommandBase {
  private final DoubleSupplier xInput, yInput;
  private final DriveTrain driveTrain;
  private double speedCoef = Constants.defaultSpeedCoef;

  public TeleopDriveCommand(DriveTrain driveTrain, DoubleSupplier xInput, DoubleSupplier yInput) {
    this.driveTrain = driveTrain;
    this.xInput = xInput;
    this.yInput = yInput;
    addRequirements(driveTrain);
  }

  public double getSpeedCoef() {
    return speedCoef;
  }

  public void setSpeedCoef(double speedCoef) {
    this.speedCoef = speedCoef;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    driveTrain.arcadeDrive(xInput.getAsDouble() * speedCoef, yInput.getAsDouble() * speedCoef);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
