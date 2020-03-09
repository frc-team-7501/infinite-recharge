package org.frc7501.utils.geometry;

public final class Vector2D {
  private double angle, magnitude;

  public Vector2D(double angle, double magnitude) {
    this.angle = angle;
    this.magnitude = magnitude;
  }

  public Vector2D() {
    this(0, 0);
  }

  public double getAngle() {
    return angle;
  }

  public double getMagnitude() {
    return magnitude;
  }

  public void setAngle(double newAngle) {
    angle = newAngle;
  }

  public void setMagnitude(double newMagnitude) {
    magnitude = newMagnitude;
  }

  public double getCartesianX() {
    return Math.sin(angle) * magnitude;
  }

  public double getCartesianY() {
    return Math.cos(angle) * magnitude;
  }

  public Point2D getPoint() {
    return new Point2D(getCartesianX(), getCartesianY());
  }
}
