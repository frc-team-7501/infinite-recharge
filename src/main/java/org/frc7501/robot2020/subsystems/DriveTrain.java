package org.frc7501.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import org.frc7501.robot2020.Constants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The drive train. Consists of four motors that make the bot move.
 */
public class DriveTrain extends SubsystemBase {
  private final WPI_VictorSPX motorFL, motorFR, motorBL, motorBR;
  private final SpeedControllerGroup groupL, groupR;
  private final DifferentialDrive differentialDrive;
  private final PigeonIMU pigeonIMU = new PigeonIMU(Constants.Ports.CAN.pigeonIMU);
  private final Encoder encoderRight = new Encoder(0, 1);
  private final Encoder encoderLeft = new Encoder(2, 3);

  public DriveTrain() {
    // Initialize motor controllers
    motorFL = new WPI_VictorSPX(Constants.Ports.CAN.victor_DriveFL);
    motorFR = new WPI_VictorSPX(Constants.Ports.CAN.victor_DriveFR);
    motorBL = new WPI_VictorSPX(Constants.Ports.CAN.victor_DriveBL);
    motorBR = new WPI_VictorSPX(Constants.Ports.CAN.victor_DriveBR);

    // Configure motor controllers
    motorFL.configFactoryDefault();
    motorFR.configFactoryDefault();
    motorBL.configFactoryDefault();
    motorBR.configFactoryDefault();

    setBrakeMode(false);

    // Create groups and DifferentialDrive
    groupL = new SpeedControllerGroup(motorFL, motorBL);
    groupR = new SpeedControllerGroup(motorFR, motorBR);

    differentialDrive = new DifferentialDrive(groupL, groupR);
    differentialDrive.setSafetyEnabled(false);
  }
  public void setBrakeMode(boolean brake) {
     motorFL.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
     motorFR.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
     motorBL.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
     motorBR.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
  }
  public void curvatureDrive(final double y, final double z, final boolean isQuickTurn) {
    differentialDrive.curvatureDrive(y, z, isQuickTurn);
    SmartDashboard.putNumber("curveY", y);
    SmartDashboard.putNumber("curveZ", z);
    SmartDashboard.putBoolean("curveQT", isQuickTurn);
  }

  public void stop() {
    differentialDrive.stopMotor();
  }

  // Returns Left Encoder current distance
public double getLeftDistance() {
    double LeftD = 0;
    LeftD = encoderLeft.getDistance();
    return LeftD;  
}

 // Returns Right Encoder current distance
 public double getRightDistance() {
  double RightD = 0;
  RightD = encoderRight.getDistance();
  return RightD;  
 }

  public double getGyroYaw() {
    double[] ypr = new double[3];
    pigeonIMU.getYawPitchRoll(ypr);
    return ypr[0]; // TODO: may need to be normalized.
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Yaw", getGyroYaw());
    SmartDashboard.putNumber("EncoderR", encoderRight.getDistance());
    SmartDashboard.putNumber("EncoderL", encoderLeft.getDistance());
  }
}
