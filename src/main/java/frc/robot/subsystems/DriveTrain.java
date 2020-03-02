package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * The drive train. Consists of four motors that make the bot move.
 */
public class DriveTrain extends SubsystemBase {
  private final WPI_VictorSPX motorFL, motorFR, motorBL, motorBR;
  private final SpeedControllerGroup groupL, groupR;
  private final DifferentialDrive differentialDrive;
  private PigeonIMU pigeonIMU = new PigeonIMU(Constants.Ports.CAN.pigeonIMU);
  
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
    motorFL.setNeutralMode(NeutralMode.Coast);
    motorFR.setNeutralMode(NeutralMode.Coast);
    motorBL.setNeutralMode(NeutralMode.Coast);
    motorBR.setNeutralMode(NeutralMode.Coast);

    // Create groups and DifferentialDrive
    groupL = new SpeedControllerGroup(motorFL, motorBL);
    groupR = new SpeedControllerGroup(motorFR, motorBR);

    differentialDrive = new DifferentialDrive(groupL, groupR);
  }

  public void arcadeDrive(final double x, final double y) {
    differentialDrive.arcadeDrive(y, x);
  }

  public double getGyroYaw() {
    double[] ypr = new double[3];
    pigeonIMU.getYawPitchRoll(ypr);
    return ypr[0]; // TODO: may need to be normalized.
  }

  @Override
  public void periodic() {
  }
}
