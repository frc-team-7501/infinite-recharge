package org.frc7501.robot2020.commands.autonomous;

import java.util.Set;

import org.frc7501.robot2020.subsystems.Conveyor;
import org.frc7501.robot2020.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShooterFireCommand extends SequentialCommandGroup {
  public ShooterFireCommand(Shooter shooter, Conveyor conveyor) {
    super(
      // Back off the conveyor
      new ParallelDeadlineGroup(
        new WaitCommand(0.35),
        new Command() {
          @Override
          public Set<Subsystem> getRequirements() {
            return Set.of(conveyor);
          }
          @Override
          public void execute() {
            conveyor.move(-0.5, 0);
          }
          @Override
          public void end(boolean interrupted) {
            conveyor.stop();
          }
        }
      ),
      // Spin up the shooter and then shoot
      new ParallelRaceGroup(
        new Command() {
          @Override
          public Set<Subsystem> getRequirements() {
            return Set.of(shooter);
          }
          @Override
          public void execute() {
            shooter.fire(1.0);
          }
          @Override
          public void end(boolean interrupted) {
            shooter.stop();
          }
        },
        new SequentialCommandGroup(
          // Let the shooter spin up
          new WaitCommand(1),
          // Feed the conveyor for 2.0 seconds
          new ParallelDeadlineGroup(
            new WaitCommand(5.0),
            new Command() {
              @Override
              public Set<Subsystem> getRequirements() {
                return Set.of(conveyor);
              }
              @Override
              public void execute() {
                conveyor.move(0.2, 0.5);
              }
              @Override
              public void end(boolean interrupted) {
                conveyor.stop();
              }
            }
          )
        )
      )
    );
  }
}
