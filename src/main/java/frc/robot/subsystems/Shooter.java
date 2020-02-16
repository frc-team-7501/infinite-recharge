package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final CANSparkMax shooterA = new CANSparkMax(Constants.Ports.CAN.sparkMaxBrushlessShooterA, MotorType.kBrushless);
  private final CANSparkMax shooterB = new CANSparkMax(Constants.Ports.CAN.sparkMaxBrushlessShooterB, MotorType.kBrushless);

  private final double kP = 6e-5;
  private final double kI = 0;
  private final double kD = 0;
  private final double kFF = 0.000015;
  private final double maxOutput = 1;
  private final double minOutput = -1;
  private final double maxRPM = 5700;
  
  private CANPIDController pidControllerA = shooterA.getPIDController();
  private CANPIDController pidControllerB = shooterB.getPIDController();
  // private CANEncoder encoderA = shooterA.getEncoder();
  // private CANEncoder encoderB = shooterB.getEncoder();

  public Shooter() {
    shooterA.restoreFactoryDefaults();
    pidControllerA.setP(kP);
    pidControllerA.setI(kI);
    pidControllerA.setD(kD);
    pidControllerA.setFF(kFF);
    pidControllerA.setOutputRange(minOutput, maxOutput);


    shooterB.restoreFactoryDefaults();
    pidControllerB.setP(kP);
    pidControllerB.setI(kI);
    pidControllerB.setD(kD);
    pidControllerB.setFF(kFF);
    pidControllerB.setOutputRange(minOutput, maxOutput);
  }

  public void fire(double velocity) {
    pidControllerA.setReference(velocity * maxRPM, ControlType.kVelocity);
    pidControllerB.setReference(-velocity * maxRPM, ControlType.kVelocity);
  }

  public void stop() {
    shooterA.stopMotor();
    shooterB.stopMotor();
  }
}
