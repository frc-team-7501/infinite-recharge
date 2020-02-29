package frc.robot.subsystems;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.i2c.RevColorSensorV3Mux;

/**
 * Manages the conveyor subsystem, including belts and sensors.
 */
public class Conveyor extends SubsystemBase {
  // Color sensors
  private final RevColorSensorV3Mux sensorBottom  = new RevColorSensorV3Mux(Constants.Ports.I2CMUX.colorSensorConveyorBottom);
  private final RevColorSensorV3Mux sensorMiddle  = new RevColorSensorV3Mux(Constants.Ports.I2CMUX.colorSensorConveyorMiddle);
  private final RevColorSensorV3Mux sensorTop     = new RevColorSensorV3Mux(Constants.Ports.I2CMUX.colorSensorConveyorTop);

  // Color processing
  private static final Color POWER_CELL_COLOR = ColorMatch.makeColor(0.337, 0.546, 0.117);
  private final ColorMatch colorMatcher       = new ColorMatch();

  // Motors
  // TODO

  public Conveyor() {
    // Configure color matcher
    colorMatcher.addColorMatch(POWER_CELL_COLOR);
  }


  public boolean getBottom() {
    var matched = colorMatcher.matchClosestColor(sensorBottom.getColor());
    var proximity = sensorBottom.getProximity();
    return matched.color == POWER_CELL_COLOR && matched.confidence >= 0.95 && proximity >= 200; // TODO: Tune
  }

  public boolean getMiddle() {
    var matched = colorMatcher.matchClosestColor(sensorMiddle.getColor());
    var proximity = sensorMiddle.getProximity();
    return matched.color == POWER_CELL_COLOR && matched.confidence >= 0.95 && proximity >= 200; // TODO: Tune
  }

  public boolean getTop() {
    var matched = colorMatcher.matchClosestColor(sensorTop.getColor());
    var proximity = sensorTop.getProximity();
    return matched.color == POWER_CELL_COLOR && matched.confidence >= 0.95 && proximity >= 200; // TODO: Tune
  }

  public void moveConveyor(double speed) {
    
  }

  public void stop() {

  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("conv state b", getBottom());
    SmartDashboard.putBoolean("conv state m", getMiddle());
    SmartDashboard.putBoolean("conv state t", getTop());
    
    SmartDashboard.putNumber("conv proximity b", sensorBottom.getProximity());
    SmartDashboard.putNumber("conv proximity m", sensorMiddle.getProximity());
    SmartDashboard.putNumber("conv proximity t", sensorTop.getProximity());
  }
}
