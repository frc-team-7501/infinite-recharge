package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Manages the conveyor and makes sure balls don't bunch up. Ends when the top conveyor is full.
 */
public class ConveyorIntakeCommand extends CommandBase {
  private final Conveyor conveyor;
  
  public ConveyorIntakeCommand(Conveyor conveyor) {
    this.conveyor = conveyor;
    addRequirements(conveyor);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Get sensor state
    var sensor = conveyor.sensorState();
    if (sensor) {
      // Bottom sensor is triggered. Move both conveyors.
      conveyor.move(0.4, 0.5);
    } else {
      // Bottom is empty. Only move bottom (intake).
      conveyor.move(0, 0.5);
    }
  }

  @Override
  public void end(boolean interrupted) {
    conveyor.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
