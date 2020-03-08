package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.i2c.RevColorSensorV3Mux;

public class ConveyorTop extends SubsystemBase {
  private final WPI_VictorSPX motor = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorTop);
  private final RevColorSensorV3Mux sensorTop = new RevColorSensorV3Mux(Constants.Ports.I2CMUX.colorSensorConveyorTop);
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
    // SmartDashboard.putBoolean("pc pres", powerCellPresence());
  }
}
