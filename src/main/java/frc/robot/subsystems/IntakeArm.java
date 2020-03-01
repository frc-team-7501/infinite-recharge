package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeArm extends SubsystemBase {
  private final CANSparkMax motor = new CANSparkMax(Constants.Ports.CAN.sparkmax_IntakeArm, MotorType.kBrushless);
  
  public IntakeArm() {
    // Configure Spark MAX
    motor.restoreFactoryDefaults();
  }

  public void move(double speed) {
    motor.set(speed);
  }

  public void stop() {
    motor.stopMotor();
  }

  @Override
  public void periodic() {
  }
}
