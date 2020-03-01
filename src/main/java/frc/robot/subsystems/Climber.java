package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private final CANSparkMax winch = new CANSparkMax(Constants.Ports.CAN.sparkmax_Winch, MotorType.kBrushless);
  private final CANSparkMax hook  = new CANSparkMax(Constants.Ports.CAN.sparkmax_HookLift, MotorType.kBrushless);
  
  public Climber() {
    // Configure the Spark MAXs
    winch.restoreFactoryDefaults();
    hook.restoreFactoryDefaults();
  }

  @Override
  public void periodic() {
  }
}
