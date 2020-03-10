package org.frc7501.robot2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.frc7501.robot2020.Constants;

public class Climber extends SubsystemBase {
  private final CANSparkMax winch = new CANSparkMax(Constants.Ports.CAN.sparkmax_Winch, MotorType.kBrushless);
  private final CANSparkMax hook  = new CANSparkMax(Constants.Ports.CAN.sparkmax_HookLift, MotorType.kBrushless);
  
  public Climber() {
    // Configure the Spark MAXs
    winch.restoreFactoryDefaults();
    hook.restoreFactoryDefaults();
  }

  public void moveWinch(double speed) {
    winch.set(speed);
  }

  public void moveHook(double speed) {
    hook.set(speed);
  }

  public void stopWinch() {
    winch.stopMotor();
  }

  public void stopHook() {
    hook.stopMotor();
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("winch current (A)", winch.getOutputCurrent());
  }
}
