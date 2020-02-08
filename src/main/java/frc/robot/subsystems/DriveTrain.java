package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
  // Limelight NetworkTable entries
  private final NetworkTableEntry limelightX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
  private final NetworkTableEntry limelightY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
  private final NetworkTableEntry limelightA = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta");
  private final NetworkTableEntry limelightV = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv");
  // Pigeon IMU (gyro)
  // private PigeonIMU pigeonIMU = new PigeonIMU(Constants.Ports.CAN.pigeonIMU);
  
  public DriveTrain() {
    motorFL = new WPI_VictorSPX(Constants.Ports.CAN.talonFL);
    motorFR = new WPI_VictorSPX(Constants.Ports.CAN.talonFR);
    motorBL = new WPI_VictorSPX(Constants.Ports.CAN.talonBL);
    motorBR = new WPI_VictorSPX(Constants.Ports.CAN.talonBR);

    // Configure the Talons
    motorFL.setNeutralMode(NeutralMode.Brake);
    motorFR.setNeutralMode(NeutralMode.Brake);
    motorBL.setNeutralMode(NeutralMode.Brake);
    motorBR.setNeutralMode(NeutralMode.Brake);

    // Create groups and DifferentialDrive
    groupL = new SpeedControllerGroup(motorFL, motorBL);
    groupR = new SpeedControllerGroup(motorFR, motorBR);
    differentialDrive = new DifferentialDrive(groupL, groupR);
  }

  public void arcadeDrive(final double x, final double y) {
    differentialDrive.arcadeDrive(y, x);
  }

  // public double getGyroYaw() {
  //   double[] ypr = new double[3];
  //   pigeonIMU.getYawPitchRoll(ypr);
  //   return ypr[0]; // TODO: may need to be normalized.
  // }

  public boolean getLimelightTargetValid() {
    return limelightV.getDouble(0) == 1;
  }

  public double getLimelightX() {
    if (!getLimelightTargetValid())
      // return null;
      return 0.0;
    return limelightX.getDouble(0.0);
  }

  public double getLimelightY() {
    if (!getLimelightTargetValid())
      return 0;
    return limelightY.getDouble(0);
  }

  public double getLimelightA() {
    if (!getLimelightTargetValid())
      return 0;
    return limelightA.getDouble(0);
  }

  public double getLimelightDistance() {
    // TODO: This should take into account the gyro angle and calculate the distance using this.
    return getLimelightA();
  }

  @Override
  public void periodic() {
  }
}
