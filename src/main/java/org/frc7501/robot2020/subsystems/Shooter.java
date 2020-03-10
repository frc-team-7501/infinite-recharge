package org.frc7501.robot2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frc7501.robot2020.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private final CANSparkMax shooterR = new CANSparkMax(Constants.Ports.CAN.sparkmax_ShooterR, MotorType.kBrushless);
  private final CANSparkMax shooterL = new CANSparkMax(Constants.Ports.CAN.sparkmax_ShooterL, MotorType.kBrushless);

  public Shooter() {
    // Configure Spark MAXs
    shooterR.restoreFactoryDefaults();
    shooterL.restoreFactoryDefaults();
    shooterL.follow(shooterR, true);
  }

  public void fire(double velocity) {
    shooterR.set(velocity);
  }

  public void stop() {
    shooterR.stopMotor();
  }
}
