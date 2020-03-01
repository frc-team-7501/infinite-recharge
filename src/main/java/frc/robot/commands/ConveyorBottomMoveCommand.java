package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorBottom;

public class ConveyorBottomMoveCommand extends CommandBase {
  private final ConveyorBottom conveyorBottom;
  
  public ConveyorBottomMoveCommand(ConveyorBottom conveyorBottom) {
    this.conveyorBottom = conveyorBottom;
    addRequirements(conveyorBottom);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    conveyorBottom.move(0.5);
  }

  @Override
  public void end(boolean interrupted) {
    conveyorBottom.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
