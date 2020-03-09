package org.frc7501.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.frc7501.robot.Constants;
import org.frc7501.robot.RobotContainer;
import org.frc7501.utils.ThreadedColorSensor;

public class ConveyorTop extends SubsystemBase {
  private final WPI_VictorSPX motor = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorTop);
  private final ThreadedColorSensor sensorTop = new ThreadedColorSensor(RobotContainer.mux, Constants.Ports.I2CMUX.colorSensorConveyorTop);
  private final ColorMatch colorMatch = new ColorMatch();

  private static final Color POWER_CELL_COLOR = ColorMatch.makeColor(0.337, 0.546, 0.117);
  
  public ConveyorTop() {
    // Configure color match
    colorMatch.addColorMatch(POWER_CELL_COLOR);
  }

  public void move(double speed) {
    motor.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    motor.stopMotor();
  }

  public boolean powerCellPresence() {
    var rawColor = sensorTop.getColor();
    
    var match = colorMatch.matchClosestColor(rawColor);
    var prox = sensorTop.getProximity();
    
    return match.color == POWER_CELL_COLOR && match.confidence >= 0.95 && prox >= 120;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("pc pres", powerCellPresence());
  }
}
