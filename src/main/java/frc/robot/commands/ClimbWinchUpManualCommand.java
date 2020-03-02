package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimbWinchUpManualCommand extends CommandBase {
  private final Climber climber;
  private final DoubleSupplier supplier;
  
  public ClimbWinchUpManualCommand(Climber climber, DoubleSupplier supplier) {
    this.climber = climber;
    this.supplier = supplier;
    addRequirements(climber);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    climber.moveWinch(supplier.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopWinch();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
