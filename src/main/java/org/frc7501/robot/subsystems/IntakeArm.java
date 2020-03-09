package org.frc7501.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.frc7501.robot.Constants;

public class IntakeArm extends SubsystemBase {
  private final CANSparkMax motor = new CANSparkMax(Constants.Ports.CAN.sparkmax_IntakeArm, MotorType.kBrushless);
  
  public IntakeArm() {
    // super(new PIDController(2, 0, 0.15));
    // Configure Spark MAX
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kCoast);
    motor.getEncoder().setPosition(0.0);
    motor.getEncoder().setPositionConversionFactor(1.0 / 87.5);
    // Configure PID controller
    // getController().setTolerance(0.1);
  }

  public double getPosition() {
    var pos = motor.getEncoder().getPosition();
    if (pos > 0 || Math.abs(pos) < 0.01)
      return 0;
    else
      return pos;
  }

  public void setBrakeMode(boolean brakeEnabled) {
    if (brakeEnabled)
      motor.setIdleMode(IdleMode.kBrake);
    else
      motor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
    super.periodic();
    SmartDashboard.putNumber("Arm position", getPosition());
    // SmartDashboard.putNumber("Arm setpoint", getController().getSetpoint());
  }

  // @Override
  // protected void useOutput(double output, double setpoint) {
  //   SmartDashboard.putNumber("Arm PID output", output);
  //   if (Math.abs(output) < 0.005)
  //     output = 0;
  //   motor.set(output);
  // }

  // @Override
  // protected double getMeasurement() {
  //   return getPosition();
  // }
}
