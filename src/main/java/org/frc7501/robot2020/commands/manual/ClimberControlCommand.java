package org.frc7501.robot2020.commands.manual;

import java.util.function.DoubleSupplier;

import org.frc7501.robot2020.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

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
    var winchPower = winchSupplier.getAsDouble();
    var hookPower = hookSupplier.getAsDouble();

    if (Math.abs(winchPower) < 0.05)
      winchPower = 0;
    if (Math.abs(hookPower) < 0.05)
      hookPower = 0;

    climber.moveWinch(winchPower);
    climber.moveHook(hookPower);
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
