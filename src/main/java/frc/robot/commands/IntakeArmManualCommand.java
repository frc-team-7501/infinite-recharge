package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;

public class IntakeArmManualCommand extends CommandBase {
  private final IntakeArm intakeArm;
  private final DoubleSupplier supplier;
  
  public IntakeArmManualCommand(IntakeArm intakeArm, DoubleSupplier supplier) {
    this.intakeArm = intakeArm;
    this.supplier = supplier;
    addRequirements(intakeArm);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    var speed = supplier.getAsDouble();
    if (Math.abs(speed) < 0.02)
      speed = 0;
    intakeArm.move(speed);
  }

  @Override
  public void end(boolean interrupted) {
    intakeArm.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
