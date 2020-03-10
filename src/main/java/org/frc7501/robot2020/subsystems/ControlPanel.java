package org.frc7501.robot2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import org.frc7501.robot2020.Constants;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControlPanel extends SubsystemBase {
  private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  private final CANSparkMax spinnerMotor  = new CANSparkMax(Constants.Ports.CAN.sparkmax_ControlPanel, MotorType.kBrushless);
  
  public final Color colorBlue          = ColorMatch.makeColor(0.125, 0.429, 0.446);
  public final Color colorGreen         = ColorMatch.makeColor(0.170, 0.578, 0.251);
  public final Color colorRed           = ColorMatch.makeColor(0.509, 0.352, 0.139);
  public final Color colorYellow        = ColorMatch.makeColor(0.315, 0.563, 0.122);
  private final ColorMatch colorMatcher = new ColorMatch();

  public ControlPanel() {
    // Configure Spark MAX
    spinnerMotor.setIdleMode(IdleMode.kBrake);
 
    // Add colors to match
    colorMatcher.addColorMatch(colorBlue);
    colorMatcher.addColorMatch(colorGreen);
    colorMatcher.addColorMatch(colorRed);
    colorMatcher.addColorMatch(colorYellow);
  }

  public static enum ControlPanelColor {
    blue, green, red, yellow, unknown
  }

  public static String getColorName(ControlPanelColor color) {
    return color.name();
  }

  public ControlPanelColor getCurrentColor() {
    var detectedColor = colorSensor.getColor();
    var matchedColor  = colorMatcher.matchClosestColor(detectedColor);
    if (matchedColor.color == colorBlue)
      return ControlPanelColor.blue;
    else if (matchedColor.color == colorGreen)
      return ControlPanelColor.green;
    else if (matchedColor.color == colorRed)
      return ControlPanelColor.red;
    else if (matchedColor.color == colorYellow)
      return ControlPanelColor.yellow;
    else
      return ControlPanelColor.unknown;
  }

  public double getConfidence() {
    var detectedColor = colorSensor.getColor();
    var matchedColor  = colorMatcher.matchClosestColor(detectedColor);
    return matchedColor.confidence;
  }

  public void spinMotor(double speed) {
    spinnerMotor.set(speed);
  }

  public void stopMotor() {
    spinnerMotor.stopMotor();
  }
  
  @Override
  public void periodic() {
    var color = colorSensor.getColor();
    SmartDashboard.putString("CP Color", getColorName(getCurrentColor()));
    SmartDashboard.putNumber("CP R", color.red);
    SmartDashboard.putNumber("CP G", color.green);
    SmartDashboard.putNumber("CP B", color.blue);
    SmartDashboard.putNumber("CP Confidence", getConfidence());
  }
}
