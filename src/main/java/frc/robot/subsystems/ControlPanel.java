package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ControlPanel extends SubsystemBase {
  private final CANSparkMax spinnerMotor  = new CANSparkMax(Constants.Ports.CAN.sparkMaxBrushlessControlPanel, MotorType.kBrushless);
  private final I2C.Port colorSensorPort  = I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor = new ColorSensorV3(colorSensorPort);
  private final ColorMatch colorMatcher   = new ColorMatch();

  public final Color colorBlue    = ColorMatch.makeColor(0.143, 0.427, 0.429);
  public final Color colorGreen   = ColorMatch.makeColor(0.197, 0.561, 0.240);
  public final Color colorRed     = ColorMatch.makeColor(0.561, 0.232, 0.114);
  public final Color colorYellow  = ColorMatch.makeColor(0.361, 0.524, 0.113);

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
    switch (color) {
      case blue:
        return "blue";
      case green:
        return "green";
      case red:
        return "red";
      case yellow:
        return "yellow";
      case unknown:
      default:
        return "unknown";
    }
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
    SmartDashboard.putString("Color Sensor Color", getColorName(getCurrentColor()));
    SmartDashboard.putNumber("Color Sensor Confidence", getConfidence());
  }
}
