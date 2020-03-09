package org.frc7501.robot.commands.autonomous;

import org.frc7501.robot.subsystems.Conveyor;
import org.frc7501.robot.subsystems.Conveyor.SensorLocation;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Manages the conveyor and makes sure balls don't bunch up. Ends when the top conveyor is full.
 */
public class ConveyorManage extends CommandBase {
  private final Conveyor conveyor;
  
  public ConveyorManage(final Conveyor conveyor) {
    this.conveyor = conveyor;
    addRequirements(conveyor);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Get sensor states
    final var bottom = conveyor.sensorState(SensorLocation.bottom);
    final var middle = conveyor.sensorState(SensorLocation.middle);
    final var top = conveyor.sensorState(SensorLocation.top);

    if (top && middle) {
      // The top conveyor is full! Do nothing.
      conveyor.stop();
    } else if (top && !middle) {
      // The top power cell has advanced too far. Reverse it!
      conveyor.move(-1.0, 0);
    } else if (middle && bottom) {
      // There is a power cell in the bottom and ahead of it. Advance both conveyors.
      conveyor.move(1.0, 1.0);
    } else if (bottom) {
      // There is a power cell in the bottom only. Advance only the bottom.
      conveyor.move(0, 1.0);
    } else {
      // Either there is a power cell in the middle, or no power cells at all. Advance
      // the bottom (intake).
      conveyor.move(0, 1.0);
    }
  }

  @Override
  public void end(final boolean interrupted) {
    conveyor.stop();
  }

  @Override
  public boolean isFinished() {
    // Get sensor states
    final var middle = conveyor.sensorState(SensorLocation.middle);
    final var top = conveyor.sensorState(SensorLocation.top);

    // If the bottom top is completely full, then end
    return top && middle;
  }
}
