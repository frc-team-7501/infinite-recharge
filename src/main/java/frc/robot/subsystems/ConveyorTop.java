package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorTop extends SubsystemBase {
  private static final WPI_VictorSPX motor = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorTop);
  
  public ConveyorTop() {

  }

  public void move(double speed) {
    motor.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    motor.stopMotor();
  }

  @Override
  public void periodic() {
  }
}
