package org.frc7501.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.frc7501.robot.Constants;

public class ConveyorBottom extends SubsystemBase {
  private static final WPI_VictorSPX motor = new WPI_VictorSPX(Constants.Ports.CAN.victor_ConveyorBottom);
  
  public ConveyorBottom() {
    // Configure VictorSPX
    motor.setInverted(true);
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