package org.frc7501.utils.controls;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimpleControllerSlow {
  protected boolean enabled = false;
  protected double setpoint = 0;
  protected double tolerance = 0;
  protected double kP;
  protected double minOutput;
  protected double maxSpeed;

  protected final DoubleSupplier inputSupplier;
  protected final DoubleConsumer outputConsumer;
  
  public SimpleControllerSlow(double kP, double minOutput, DoubleSupplier inputSupplier, DoubleConsumer outputConsumer, double maxSpeed) {
    this.inputSupplier = inputSupplier;
    this.outputConsumer = outputConsumer;
    this.kP = kP;
    this.minOutput = minOutput;
    this.maxSpeed = maxSpeed;
  }

  public void disable() {
    enabled = false;
  }

  public void enable() {
    enabled = true;
  }

  public double getSetpoint() {
    return setpoint;
  }

  public void setSetpoint(double setpoint) {
    this.setpoint = setpoint;
  }

  public double getTolerance() {
    return tolerance;
  }

  public void setTolerance(double tolerance) {
    this.tolerance = tolerance;
  }

  public double getkP() {
    return kP;
  }

  public void setkP(double kP) {
    this.kP = kP;
  }

  public double getMinOutput() {
    return minOutput;
  }

  public void setMinOutput(double minOutput) {
    this.minOutput = minOutput;
  }

  public void execute() {
    if (!enabled) {
      outputConsumer.accept(0);
      return;
    }
    var current = inputSupplier.getAsDouble();
    var error = current - setpoint;
    SmartDashboard.putNumber("Setpoint", setpoint);
    SmartDashboard.putNumber("Current", current);
    SmartDashboard.putNumber("error", error);

    double output;
    if (error > tolerance) {
      output = (kP * error + minOutput);
    } else if (error < -tolerance) {
      output = (kP * error - minOutput);
    } else {
      output = 0;
    }
    
    // Limit output speed to between 0.5 and -0.5
    if (output > maxSpeed) {
      output = maxSpeed; 
    } else if (output < maxSpeed * -1) {
      output = maxSpeed * -1; 
    }
    outputConsumer.accept(output);
  }

  public boolean atSetpoint() {
    var current = inputSupplier.getAsDouble();
    var error = current - setpoint;
    return Math.abs(error) <= Math.abs(tolerance);
  }
}
