package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeArm;

public class IntakeArmDown extends CommandBase {
  private final IntakeArm intakeArm;

  public IntakeArmDown(IntakeArm intakeArm) {
    this.intakeArm = intakeArm;
    addRequirements(intakeArm);
  }

  @Override
  public void initialize() {
    intakeArm.enable();
    intakeArm.getController().setTolerance(0.05);
    intakeArm.setBrakeMode(false);
    intakeArm.setSetpoint(Constants.Setpoints.intakeArmDown);
  }

  @Override
  public boolean isFinished() {
    return intakeArm.getController().atSetpoint();
  }
}
