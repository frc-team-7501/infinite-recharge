package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Staging extends SubsystemBase {
  private final WPI_VictorSPX motorStaging = new WPI_VictorSPX(Constants.Ports.CAN.victorSPX);

public void moveStaging(double speed) {
  motorStaging.set(ControlMode.PercentOutput, speed );
}

public void stop() {
  motorStaging.stopMotor();
}

  public Staging() {

  }

  @Override
  public void periodic() {
  }
}
