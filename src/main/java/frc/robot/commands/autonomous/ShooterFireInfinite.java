package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterFireInfinite extends CommandBase {
  private final Shooter shooter;

  public ShooterFireInfinite(Shooter shooter) {
    this.shooter = shooter;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.fire(1.0);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
