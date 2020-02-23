package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Manages the conveyor subsystem, including belts and sensors.
 */
public class Conveyor extends SubsystemBase {
  private final ColorSensorV3 sensorTop = new ColorSensorV3(I2C.Port.kOnboard);
  private final ColorMatch colorMatcher = new ColorMatch();
  private final Color powerCellColor = ColorMatch.makeColor(0.337, 0.546, 0.117);
  // Test conveyor system 
  private final WPI_VictorSPX motorStaging = new WPI_VictorSPX(Constants.Ports.CAN.victorSPX);
  private final WPI_TalonSRX motorConveyor = new WPI_TalonSRX(Constants.Ports.CAN.talonC);
  
public void moveConveyor(double speed) {
  motorConveyor.set(ControlMode.PercentOutput, speed); 
  motorStaging.set(ControlMode.PercentOutput, speed);
}

  /**
   * Creates a new instance of the subsystem.
   */
  public Conveyor() {
    colorMatcher.addColorMatch(powerCellColor); // TODO
  }

  /**
   * Returns true if there is a power cell present.
   */
  public boolean isPowerCellPresent() {
    var matched = colorMatcher.matchClosestColor(sensorTop.getColor());
    SmartDashboard.putNumber("power cell confidence", matched.confidence);
    return matched.color == powerCellColor && matched.confidence >= 0.95; // TODO
  }

  /**
   * Returns the proximity reading from the color sensor.
   */
  public double getProximity() {
    return sensorTop.getProximity();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("isPowerCellPresent", isPowerCellPresent());
    SmartDashboard.putNumber("prox", getProximity());
  }
}
