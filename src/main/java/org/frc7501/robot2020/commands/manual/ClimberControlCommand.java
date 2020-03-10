package org.frc7501.robot2020.commands.manual;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc7501.robot2020.subsystems.Climber;

public class ClimberControlCommand extends CommandBase {
  private final Climber climber;
  private final DoubleSupplier winchSupplier;
  private final DoubleSupplier hookSupplier;
  
  public ClimberControlCommand(Climber climber, DoubleSupplier winchSupplier, DoubleSupplier hookSupplier) {
    this.climber = climber;
    this.winchSupplier = winchSupplier;
    this.hookSupplier = hookSupplier;
    addRequirements(climber);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    climber.moveWinch(winchSupplier.getAsDouble());
    climber.moveHook(hookSupplier.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopWinch();
    climber.stopHook();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
