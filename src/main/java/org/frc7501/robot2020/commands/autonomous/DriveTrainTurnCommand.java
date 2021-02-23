/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.DriveTrain;
import org.frc7501.utils.controls.SimpleController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveTrainTurnCommand extends CommandBase {
  private final SimpleController controller;
  private final DriveTrain driveTrain;
  private double relativeSetpoint = 0;

  public DriveTrainTurnCommand(DriveTrain driveTrain, double setpoint) {
    this.driveTrain = driveTrain;
    this.relativeSetpoint = setpoint;
    controller = new SimpleController(0.05, 0.05, driveTrain::getGyroYaw,
        (output) -> driveTrain.curvatureDrive(0, output, true));

    controller.setTolerance(5);
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
