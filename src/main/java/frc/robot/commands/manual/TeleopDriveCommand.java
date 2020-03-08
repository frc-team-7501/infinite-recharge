package frc.robot.commands.manual;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TeleopDriveCommand extends CommandBase {
  private final DoubleSupplier zInput, yInput, tInput;
  private final BooleanSupplier qInput;
  private final DriveTrain driveTrain;

  public TeleopDriveCommand(DriveTrain driveTrain, DoubleSupplier yInput, DoubleSupplier zInput, BooleanSupplier qInput, DoubleSupplier tInput) {
    this.driveTrain = driveTrain;
    this.yInput     = yInput;
    this.zInput     = zInput;
    this.qInput     = qInput;
    this.tInput     = tInput;
    
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    var throttle  = tInput.getAsDouble();
    var ySpeed    = yInput.getAsDouble() * throttle;
    var zSpeed    = zInput.getAsDouble() * throttle;
    var isQuick   = qInput.getAsBoolean();

    driveTrain.curvatureDrive(ySpeed, zSpeed, isQuick);
  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
