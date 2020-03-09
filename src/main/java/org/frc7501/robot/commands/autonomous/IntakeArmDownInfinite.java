package org.frc7501.robot.commands.autonomous;

import org.frc7501.robot.subsystems.IntakeArm;
import org.frc7501.utils.ClosedLoopBoundaryController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeArmDownInfinite extends CommandBase {
  private IntakeArm intakeArm;
  private ClosedLoopBoundaryController boundaryController = new ClosedLoopBoundaryController(-1, -0.36, 0.2, intakeArm::getPosition, intakeArm::move);
  
  public IntakeArmDownInfinite(IntakeArm intakeArm) {
    this.intakeArm = intakeArm;
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
    return false;
  }
}
