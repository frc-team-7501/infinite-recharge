package frc.robot.utils.i2c;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

public class RevColorSensorV3Mux {
  protected ColorSensorV3 sensor = new ColorSensorV3(Port.kOnboard);
  protected final int channel;

  public RevColorSensorV3Mux(int channel) {
    if (channel > 7 || channel < 0)
      throw new Error("Invalid channel selection");
    this.channel = channel;
  }

  public Color getColor() {
    TCA9548A.getInstance().selectChannel(channel);
    var result = sensor.getColor();
    TCA9548A.getInstance().release();
    return result;
  }

  public int getProximity() {
    TCA9548A.getInstance().selectChannel(channel);
    var result = sensor.getProximity();
    TCA9548A.getInstance().release();
    return result;
  }
}
