package org.frc7501.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import org.frc7501.robot2020.Constants;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase {
  // Motor controllers
  private static final WPI_VictorSPX motorBottom = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorBottom);
  private final WPI_VictorSPX        motorTop    = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorTop);

  // Color sensor for indexing
  private final ColorSensorV3 sensorBottom = new ColorSensorV3(I2C.Port.kMXP);
  
  // Color matching for power cells
  private static final Color POWER_CELL_COLOR = ColorMatch.makeColor(0.337, 0.546, 0.117);
  private final ColorMatch powerCellMatcher = new ColorMatch();

  public Conveyor() {
    powerCellMatcher.addColorMatch(POWER_CELL_COLOR);
  }

  public boolean sensorState() {
    var matched = powerCellMatcher.matchClosestColor(sensorBottom.getColor());
    var proximity = sensorBottom.getProximity();
    return matched.color == POWER_CELL_COLOR && matched.confidence >= 0.95 && proximity >= 200;
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
    SmartDashboard.putBoolean("Index BOTTOM", sensorState());
  }
}
