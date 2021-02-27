package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.DriveTrain;
import org.frc7501.utils.controls.SimpleControllerSlow;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveTrainMoveCommand extends CommandBase {
  private final SimpleControllerSlow controller;
  private final DriveTrain driveTrain;
  private double relativeSetpoint = 0;
  private double maxSpeed;

  public DriveTrainMoveCommand(DriveTrain driveTrain, double setpoint, double maxSpeed) {
    this.driveTrain = driveTrain;
    this.relativeSetpoint = setpoint;
    this.maxSpeed = maxSpeed;
    addRequirements(driveTrain);
    controller = new SimpleControllerSlow(0.01, 0.05, driveTrain::getLeftDistance,
        (output) -> driveTrain.curvatureDrive(-output, 0, true), maxSpeed);

    controller.setTolerance(5);
  }

  @Override
  public void initialize() {
    controller.setSetpoint(driveTrain.getLeftDistance() + relativeSetpoint);
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
