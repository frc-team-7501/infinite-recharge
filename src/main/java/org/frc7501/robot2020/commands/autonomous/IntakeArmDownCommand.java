package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.IntakeArm;
import org.frc7501.utils.controls.BoundaryController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeArmDownCommand extends CommandBase {
  private final IntakeArm intakeArm;
  private final BoundaryController boundaryController;
  
  public IntakeArmDownCommand(IntakeArm intakeArm) {
    this.intakeArm = intakeArm;
    boundaryController = new BoundaryController(-1, -0.36, 0.5, intakeArm::getPosition, intakeArm::move); // TODO: change correction to be more responsive
    addRequirements(intakeArm);
  }

  @Override
  public void initialize() {
    intakeArm.setBrakeMode(false);
  }

  @Override
  public void execute() {
    boundaryController.execute();
  }

  @Override
  public void end(boolean interrupted) {
    intakeArm.stop();
  }

  @Override
  public boolean isFinished() {
    return boundaryController.inRange();
  }
}
