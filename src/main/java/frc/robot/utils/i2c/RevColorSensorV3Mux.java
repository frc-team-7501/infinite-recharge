package frc.robot.utils.i2c;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotContainer;

public class RevColorSensorV3Mux {
  protected ColorSensorV3 sensor;
  protected final int channel;

  public RevColorSensorV3Mux(int channel) {
    if (channel > 7 || channel < 0)
      throw new Error("Invalid channel selection");
    this.channel = channel;
    RobotContainer.multiplexer.selectChannel(channel);
    this.sensor = new ColorSensorV3(Port.kOnboard);
  }

  public Color getColor() {
    RobotContainer.multiplexer.selectChannel(channel);
    var result = sensor.getColor();
    return result;
  }

  public int getProximity() {
    RobotContainer.multiplexer.selectChannel(channel);
    var result = sensor.getProximity();
    return result;
  }
}
