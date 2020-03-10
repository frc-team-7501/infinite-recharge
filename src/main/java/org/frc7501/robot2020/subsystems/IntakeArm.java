package org.frc7501.robot2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.frc7501.robot2020.Constants;

public class IntakeArm extends SubsystemBase {
  private final CANSparkMax motor = new CANSparkMax(Constants.Ports.CAN.sparkmax_IntakeArm, MotorType.kBrushless);
  
  public IntakeArm() {
    // Configure Spark MAX
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kCoast);
    motor.getEncoder().setPosition(0.0);
    motor.getEncoder().setPositionConversionFactor(1.0 / 87.5);
  }

  public double getPosition() {
    var pos = motor.getEncoder().getPosition();
    if (pos > 0 || Math.abs(pos) < 0.01)
      return 0;
    else
      return pos;
  }

  public void move(double speed) {
    motor.set(speed);
  }

  public void stop() {
    motor.stopMotor();
  }

  public void setBrakeMode(boolean brakeEnabled) {
    if (brakeEnabled)
      motor.setIdleMode(IdleMode.kBrake);
    else
      motor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Arm position", getPosition());
  }
}
