package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorFeedCommand extends CommandBase {
  private final Conveyor conveyor;
  
  public ConveyorFeedCommand(Conveyor conveyor) {
    this.conveyor = conveyor;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
