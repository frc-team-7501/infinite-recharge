package org.frc7501.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc7501.robot.subsystems.ConveyorBottom;

public class ConveyorBottomMoveCommand extends CommandBase {
  private final ConveyorBottom conveyorBottom;
  private final DoubleSupplier supplier;
  
  public ConveyorBottomMoveCommand(ConveyorBottom conveyorBottom, DoubleSupplier supplier) {
    this.conveyorBottom = conveyorBottom;
    this.supplier = supplier;
    addRequirements(conveyorBottom);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    conveyorBottom.move(supplier.getAsDouble());
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
