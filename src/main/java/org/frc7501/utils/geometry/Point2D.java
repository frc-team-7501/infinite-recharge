package org.frc7501.utils.geometry;

public final class Point2D {
  private double x, y;

  public Point2D(double x, double y) {
    setX(x);
    setY(y);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public void setX(double newX) {
    x = newX;
  }

  public void setY(double newY) {
    y = newY;
  }

  public void set(double newX, double newY) {
    x = newX;
    y = newY;
  }

  public Point2D() {
    this(0, 0);
  }

  public double getAngle() {
    return Math.atan2(y, x);
  }

  public double getMagnitude() {
    return Math.hypot(x, y);
  }

  public Vector2D getVector() {
    return new Vector2D(getAngle(), getMagnitude());
  }
}
