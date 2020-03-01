package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorTop;

public class ConveyorTopMoveCommand extends CommandBase {
  private final ConveyorTop conveyorTop;
  
  public ConveyorTopMoveCommand(ConveyorTop conveyorTop) {
    this.conveyorTop = conveyorTop;
    addRequirements(conveyorTop);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    conveyorTop.move(0.5);
  }

  @Override
  public void end(boolean interrupted) {
    conveyorTop.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
