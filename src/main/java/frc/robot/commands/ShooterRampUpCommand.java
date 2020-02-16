package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterRampUpCommand extends CommandBase {
  private final Shooter shooter;

  private double velocity = 0.80; // TODO: get this from a DoubleProvider that gets data from the Limelight
  
  public ShooterRampUpCommand(Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.fire(velocity);
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
