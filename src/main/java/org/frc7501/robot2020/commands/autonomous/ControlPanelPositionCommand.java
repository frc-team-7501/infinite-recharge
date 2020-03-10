package org.frc7501.robot2020.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc7501.robot2020.subsystems.ControlPanel;
import org.frc7501.robot2020.subsystems.ControlPanel.ControlPanelColor;

public class ControlPanelPositionCommand extends CommandBase {
  private final ControlPanel controlPanel;
  private ControlPanelColor targetColor;

  public ControlPanelPositionCommand(ControlPanel controlPanel) {
    this.controlPanel = controlPanel;
    addRequirements(controlPanel);
  }

  public void setTargetColor(ControlPanelColor targetColor) {
    this.targetColor = targetColor;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    SmartDashboard.putString("GameData", DriverStation.getInstance().getGameSpecificMessage());
    switch (DriverStation.getInstance().getGameSpecificMessage()) {
      case "R":
        targetColor = ControlPanelColor.blue;
        break;
      case "G":
        targetColor = ControlPanelColor.yellow;
        break;
      case "B":
        targetColor = ControlPanelColor.red;
        break;
      case "Y":
        targetColor = ControlPanelColor.green;
        break;
      default:
        this.targetColor = null;
        break;
    }
    controlPanel.spinMotor(0.5);
  }

  @Override
  public void end(boolean interrupted) {
    controlPanel.stopMotor();
    SmartDashboard.putBoolean("finished", true);
  }

  @Override
  public boolean isFinished() {
    if (targetColor == null)
      return true;
      
    SmartDashboard.putString("currentColorE", controlPanel.getCurrentColor().name());
    SmartDashboard.putString("targetColor", targetColor.name());
    SmartDashboard.putBoolean("cur = target", controlPanel.getCurrentColor() == targetColor);
    SmartDashboard.putBoolean("confident", controlPanel.getConfidence() >= 0.98);
    
    return controlPanel.getCurrentColor() == targetColor && controlPanel.getConfidence() >= 0.98;
  }
}
