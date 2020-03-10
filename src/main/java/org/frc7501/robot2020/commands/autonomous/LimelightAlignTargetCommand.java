package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.DriveTrain;
import org.frc7501.robot2020.subsystems.Limelight;
import org.frc7501.robot2020.subsystems.Limelight.LEDState;
import org.frc7501.utils.controls.SimpleController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimelightAlignTargetCommand extends CommandBase {
  private final Limelight limelight;
  private final SimpleController controller;

  public LimelightAlignTargetCommand(DriveTrain driveTrain, Limelight limelight) {
    this.limelight = limelight;
    controller = new SimpleController(
      0.075, 0.07,
      () -> limelight.validTarget() ? limelight.getRawXOffset() : 7.0,
      output -> driveTrain.curvatureDrive(0, -output, true)
    );

    addRequirements(driveTrain, limelight);

    // Tune the controller
    controller.setTolerance(0.05);
  }

  @Override
  public void initialize() {
    super.initialize();
    limelight.setLEDState(LEDState.kSolid);
  }

  @Override
  public void end(boolean interrupted) {
    limelight.setLEDState(LEDState.kOff);
  }

  @Override
  public boolean isFinished() {
    return controller.atSetpoint();
  }
}
