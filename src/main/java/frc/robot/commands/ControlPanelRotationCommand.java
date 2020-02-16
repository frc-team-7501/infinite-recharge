package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.ControlPanel.ControlPanelColor;

public class ControlPanelRotationCommand extends CommandBase {
  private final ControlPanel controlPanel;

  private ControlPanelColor indexColor;
  private ControlPanelColor lastColor;
  private int rotationCount;

  public ControlPanelRotationCommand(ControlPanel controlPanel) {
    this.controlPanel = controlPanel;
    addRequirements(controlPanel);
  }

  @Override
  public void initialize() {
    indexColor = null;
    lastColor = null;
    rotationCount = 0;
  }

  @Override
  public void execute() {
    if (indexColor == null && controlPanel.getConfidence() >= 0.95)
      indexColor = controlPanel.getCurrentColor();
    var currentColor = controlPanel.getCurrentColor();
    if (controlPanel.getConfidence() >= 0.95) {
      if (indexColor == null)
        indexColor = currentColor;
      else if (currentColor != lastColor && currentColor == indexColor)
        rotationCount++;
      lastColor = currentColor;
    }
    controlPanel.spinMotor(1);
  }

  @Override
  public void end(boolean interrupted) {
    controlPanel.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return rotationCount > 7;
  }
}
