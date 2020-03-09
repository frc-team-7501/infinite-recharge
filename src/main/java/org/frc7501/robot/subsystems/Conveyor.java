package org.frc7501.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;

import org.frc7501.robot.Constants;
import org.frc7501.robot.RobotContainer;
import org.frc7501.utils.ThreadedColorSensor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase {
  // Motor controllers
  private static final WPI_VictorSPX motorBottom = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorBottom);
  private final WPI_VictorSPX        motorTop    = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorTop);

  // Color sensors used for soft-indexing
  private ThreadedColorSensor sensorBottom = new ThreadedColorSensor(RobotContainer.mux, Constants.Ports.I2CMUX.colorSensorConveyorBottom);
  private ThreadedColorSensor sensorMiddle = new ThreadedColorSensor(RobotContainer.mux, Constants.Ports.I2CMUX.colorSensorConveyorMiddle);
  private ThreadedColorSensor sensorTop    = new ThreadedColorSensor(RobotContainer.mux, Constants.Ports.I2CMUX.colorSensorConveyorTop);
  
  // Color matching for power cells
  private static final Color POWER_CELL_COLOR = ColorMatch.makeColor(0.337, 0.546, 0.117);
  private final ColorMatch powerCellMatcher = new ColorMatch();

  public Conveyor() {
  }

  public enum SensorLocation {
    top, middle, bottom
  }
  
  public boolean sensorState(SensorLocation sensorLocation) {
    ThreadedColorSensor sensor;
    switch (sensorLocation) {
      case top:
        sensor = sensorTop;
        break;
      case middle:
        sensor = sensorMiddle;
        break;
      case bottom:
        sensor = sensorBottom;
        break;
      default:
        System.err.println("Invalid SensorLocation value passed to sensorState()");
        return false;
    }
    var color = sensor.getColor();
    if (color == null)
      return false;
    var matched = powerCellMatcher.matchClosestColor(color);
    var proximity = sensor.getProximity();
    return matched.color == POWER_CELL_COLOR && matched.confidence >= 0.95 && proximity >= 120; // TODO: tune these
  }

  public void moveTop(double speed) {
    motorTop.set(speed);
  }

  public void moveBottom(double speed) {
    motorBottom.set(speed);
  }

  public void move(double speedTop, double speedBottom) {
    moveTop(speedTop);
    moveBottom(speedBottom);
  }

  public void stopTop() {
    motorTop.stopMotor();
  }

  public void stopBottom() {
    motorBottom.stopMotor();
  }

  public void stop() {
    stopTop();
    stopBottom();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Index BOTTOM", sensorState(SensorLocation.bottom));
    SmartDashboard.putBoolean("Index MIDDLE", sensorState(SensorLocation.middle));
    SmartDashboard.putBoolean("Index TOP",    sensorState(SensorLocation.top));
  }
}
