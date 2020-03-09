package org.frc7501.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.frc7501.utils.geometry.Point2D;

public class Limelight extends SubsystemBase {

  public static enum LEDState {
    kDefault,
    kOff,
    kBlink,
    kSolid,
  }

  private final NetworkTable limelightTable;
  
  public Limelight() {
    limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    setLEDState(LEDState.kOff);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Limelight X", getRawXOffset());
  }

  public boolean validTarget() {
    var tv = limelightTable.getEntry("tv").getDouble(0);
    return tv == 1;
  }

  public double getRawXOffset() {
    var tx = limelightTable.getEntry("tx").getDouble(0);
    return tx;
  }

  public double getRawYOffset() {
    var ty = limelightTable.getEntry("ty").getDouble(0);
    return ty;
  }

  /**
   * Gets a point corresponding to the center of the limelight
   * target's crosshair
   * @return the Point2D, or null if no target is visible.
   */
  public Point2D getTargetCenter() {
    if (!validTarget())
      return null;
    Point2D point = new Point2D(getRawXOffset(), getRawYOffset());
    return point;
  }

  public double getArea() {
    var ta = limelightTable.getEntry("ta").getDouble(0);
    return ta;
  }

  public double getLatency() {
    var tl = limelightTable.getEntry("tl").getDouble(0);
    return tl + 11.0; // 11 ms for image capture, per docs
  }

  public double getShortestSideLength() {
    var tshort = limelightTable.getEntry("tshort").getDouble(0);
    return tshort;
  }

  public double getLongestSideLength() {
    var tlong = limelightTable.getEntry("tlong").getDouble(0);
    return tlong;
  }
  
  public double getBoundingBoxHeight() {
    var tvert = limelightTable.getEntry("tvert").getDouble(0);
    return tvert;
  }

  public double getBoundingBoxWidth() {
    var thor = limelightTable.getEntry("thor").getDouble(0);
    return thor;
  }

  public void setLEDState(LEDState state) {
    int resolvedState;
    switch (state) {
      case kDefault:
        resolvedState = 0;
        break;
      case kOff:
        resolvedState = 1;
        break;
      case kBlink:
        resolvedState = 2;
        break;
      case kSolid:
        resolvedState = 3;
        break;
      default:
        throw new Error("Invalid LED state.");
    }
    limelightTable.getEntry("ledMode").setNumber(resolvedState);
  }
}
