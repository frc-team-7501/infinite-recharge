package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * The drive train. Consists of four motors that make the bot move.
 */
public class DriveTrain extends SubsystemBase {
  private WPI_VictorSPX motorFL, motorFR, motorBL, motorBR;
  private SpeedControllerGroup groupL, groupR;
  private DifferentialDrive differentialDrive;
  
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

  @Override
  public void periodic() {
  }
}
