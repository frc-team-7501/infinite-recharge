package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Staging;

public class ConveyorStagingCommand extends CommandBase {
  private final Staging staging; 

  public ConveyorStagingCommand(Staging staging) {
    this.staging = staging; 
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    staging.moveStaging(-0.65);
  }

  @Override
  public void end(boolean interrupted) {
    staging.stop(); 
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
