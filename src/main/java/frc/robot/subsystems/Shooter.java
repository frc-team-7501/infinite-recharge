package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
	private final CANSparkMax shooterA = new CANSparkMax(Constants.Ports.CAN.sparkMaxBrushlessShooterA, MotorType.kBrushless);
	private final CANSparkMax shooterB = new CANSparkMax(Constants.Ports.CAN.sparkMaxBrushlessShooterB, MotorType.kBrushless);
	
	public Shooter() {
		shooterA.setIdleMode(IdleMode.kCoast);
		shooterB.setIdleMode(IdleMode.kCoast);

		shooterB.follow(shooterA, true);
	}

	public void fire(double velocity) {
		shooterA.set(velocity);
	}

	public void stop() {
		shooterA.stopMotor();
		shooterB.stopMotor();
	}
}
