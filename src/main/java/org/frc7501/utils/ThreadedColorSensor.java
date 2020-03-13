package org.frc7501.utils;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.util.Color;

public class ThreadedColorSensor {
  private Color color = null;
  private int proximity = 0;
  private Object lock = new Object();
  private Notifier notifier;

  public ThreadedColorSensor(ColorSensorV3 colorSensor) {
    notifier = new Notifier(() -> {
      synchronized (lock) {
        color = colorSensor.getColor();
        proximity = colorSensor.getProximity();
      }
      notifier.startSingle(1.0 / 50.0);
    });
    notifier.startSingle(1.0 / 50.0);
  }

  public Color getColor() {
    return color;
  }

  public int getProximity() {
    return proximity;
  }
}