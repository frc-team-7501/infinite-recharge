package org.frc7501.robot2020.commands.autonomous;

import org.frc7501.robot2020.subsystems.ControlPanel;
import org.frc7501.robot2020.subsystems.ControlPanel.ControlPanelColor;

import edu.wpi.first.wpilibj2.command.CommandBase;

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
    // Get current color
    var currentColor = controlPanel.getCurrentColor();

    if (controlPanel.getConfidence() >= 0.95) {
      if (indexColor == null)
        // Set index color
        indexColor = currentColor;
      else if (currentColor != lastColor && currentColor == indexColor)
        // Color has changed since last loop, and the color is now the index color.
        // Increment the counter.
        rotationCount++;
      // Set the last color to detect color changes
      lastColor = currentColor;
    }

    // Spin the control panel motor
    controlPanel.spinMotor(1);
  }

  @Override
  public void end(boolean interrupted) {
    controlPanel.stopMotor();
  }

  @Override
  public boolean isFinished() {
    // Because the index color will appear twice per rotation, rotation count is
    // double the number of actual rotatons needed.
    // In this case, 7 is used so that 3 full rotations occur, with an extra half
    // revolution allowing for error margin.
    return rotationCount > 7;
  }
}
