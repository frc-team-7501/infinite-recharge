package org.frc7501.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc7501.robot.subsystems.ConveyorTop;

public class ConveyorTopMoveCommand extends CommandBase {
  private final ConveyorTop conveyorTop;
  private final DoubleSupplier supplier;
  
  public ConveyorTopMoveCommand(ConveyorTop conveyorTop, DoubleSupplier supplier) {
    this.conveyorTop = conveyorTop;
    this.supplier = supplier;
    addRequirements(conveyorTop);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    conveyorTop.move(supplier.getAsDouble());
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
