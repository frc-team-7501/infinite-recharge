package org.frc7501.utils;

import com.revrobotics.ColorSensorV3;

import org.team696.TCA9548ADriver.TCA9548A;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.util.Color;

public class ThreadedColorSensor {
  protected ColorSensorV3 sensor;
  protected Notifier notifier;

  protected Object lock = new Object();

  protected Color color = null;
  protected int proximity = 0;

  public ThreadedColorSensor(TCA9548A mux, int channel) {
    sensor = new ColorSensorV3(mux, channel);
    notifier = new Notifier(() -> {
      synchronized (lock) {
        color = sensor.getColor();
        proximity = sensor.getProximity();
      }
    });
  }

  public void enable() {
    notifier.startPeriodic(1.0 / 50.0);
  }

  public void disable() {
    notifier.stop();
    synchronized (lock) {
      color = null;
      proximity = 0;
    }
  }

  public Color getColor() {
    synchronized (lock) {
      return color;
    }
  }

  public int getProximity() {
    synchronized (lock) {
      return proximity;
    }
  }
}
