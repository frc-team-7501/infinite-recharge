package org.frc7501.robot.commands.autonomous;

import org.frc7501.robot.subsystems.IntakeArm;
import org.frc7501.utils.ClosedLoopBoundaryController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeArmDown extends CommandBase {
  private final IntakeArm intakeArm;
  private final ClosedLoopBoundaryController boundaryController;
  
  public IntakeArmDown(IntakeArm intakeArm) {
    this.intakeArm = intakeArm;
    boundaryController = new ClosedLoopBoundaryController(-1, -0.36, 0.2, intakeArm::getPosition, intakeArm::move);
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
