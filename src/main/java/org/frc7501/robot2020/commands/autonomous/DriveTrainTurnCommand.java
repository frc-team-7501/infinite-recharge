package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.DriveTrain;
import org.frc7501.utils.controls.SimpleControllerSlow;

import edu.wpi.first.wpilibj2.command.CommandBase;

// Change to PIDCommand
public class DriveTrainTurnCommand extends CommandBase {
  private final SimpleControllerSlow controller;
  private final DriveTrain driveTrain;
  private double relativeSetpoint = 0;

  public DriveTrainTurnCommand(DriveTrain driveTrain, double setpoint) {
    this.driveTrain = driveTrain;
    this.relativeSetpoint = setpoint;
    addRequirements(driveTrain);
    controller = new SimpleControllerSlow(0.008, 0.09, driveTrain::getGyroYaw,
        (output) -> driveTrain.curvatureDrive(0, output, true), 0.35);

    controller.setTolerance(1);
  }

  @Override
  public void initialize() {
    controller.setSetpoint(driveTrain.getGyroYaw() + relativeSetpoint);
    controller.enable();
  }

  @Override
  public void execute() {
    controller.execute();
  }

  @Override
  public void end(boolean interrupted) {
    controller.disable();
  }

  @Override
  public boolean isFinished() {
    return controller.atSetpoint();
  }
}
