package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TeleopDriveCommand extends CommandBase {
  private final DoubleSupplier xInput, yInput;
  private final DriveTrain driveTrain;

  public TeleopDriveCommand(DriveTrain driveTrain, DoubleSupplier xInput, DoubleSupplier yInput) {
    this.driveTrain = driveTrain;
    this.xInput = xInput;
    this.yInput = yInput;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    driveTrain.arcadeDrive(xInput.getAsDouble(), yInput.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
