package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command autonomousCommand;

  private RobotContainer robotContainer;
  private NetworkTable limelightNT;
  private NetworkTableEntry limelightX, limelightY, limelightA;

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    limelightNT = NetworkTableInstance.getDefault().getTable("limelight");
    limelightX = limelightNT.getEntry("tx");
    limelightY = limelightNT.getEntry("ty");
    limelightA = limelightNT.getEntry("ta");
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("Limelight tx", limelightX.getDouble(0.0));
    SmartDashboard.putNumber("Limelight ty", limelightY.getDouble(0.0));
    SmartDashboard.putNumber("Limelight ta", limelightA.getDouble(0.0));
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
