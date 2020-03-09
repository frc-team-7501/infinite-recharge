package org.frc7501.robot.commands.autonomous;

import java.util.Set;

import org.frc7501.robot.subsystems.Conveyor;
import org.frc7501.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShooterFire extends SequentialCommandGroup {
  public ShooterFire(Shooter shooter, Conveyor conveyor) {
    super(
      new WaitCommand(0.2)
        .deadlineWith(
          new Command() {
            @Override
            public Set<Subsystem> getRequirements() {
              return Set.of(conveyor);
            }
            @Override
            public void execute() {
              conveyor.move(-1.0, 0);
            }
            @Override
            public void end(boolean interrupted) {
              conveyor.stop();
            }
          }),
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
      }
        .raceWith(
          new WaitCommand(2.0)
            .raceWith(new Command() {
              @Override
              public Set<Subsystem> getRequirements() {
                return Set.of(conveyor);
              }
              @Override
              public void execute() {
                conveyor.move(1.0, 1.0);
              }
              @Override
              public void end(boolean interrupted) {
                conveyor.stop();
              }
            }))

      // Back off the conveyor
      // new ParallelDeadlineGroup(
      //   new WaitCommand(0.2),
      //   new Command() {
      //     @Override
      //     public Set<Subsystem> getRequirements() {
      //       return Set.of(conveyor);
      //     }
      //     @Override
      //     public void execute() {
      //       conveyor.move(-1.0, 0);
      //     }
      //     @Override
      //     public void end(boolean interrupted) {
      //       conveyor.stop();
      //     }
      //   }
      // ),
      // // Spin up the shooter and then shoot
      // new ParallelRaceGroup(
      //   new Command() {
      //     @Override
      //     public Set<Subsystem> getRequirements() {
      //       return Set.of(shooter);
      //     }
      //     @Override
      //     public void execute() {
      //       shooter.fire(1.0);
      //     }
      //     @Override
      //     public void end(boolean interrupted) {
      //       shooter.stop();
      //     }
      //   },
      //   // Feed the conveyor for 2.0 seconds
      //   new ParallelRaceGroup(
      //     new WaitCommand(2.0),
      //     new Command() {
      //       @Override
      //       public Set<Subsystem> getRequirements() {
      //         return Set.of(conveyor);
      //       }
      //       @Override
      //       public void execute() {
      //         conveyor.move(1.0, 1.0);
      //       }
      //       @Override
      //       public void end(boolean interrupted) {
      //         conveyor.stop();
      //       }
      //     }
      //   )
      // )
    );
  }
}
