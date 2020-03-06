package frc.robot.utils.i2c;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import frc.robot.Constants;

/**
 * Adafruit TCA9548A I2C Multiplexer
 */
public final class TCA9548A {
  protected static TCA9548A instance;

  public static TCA9548A getInstance() {
    if (instance == null)
      instance = new TCA9548A();
    return instance;
  }

  protected static final int ADDR = Constants.Ports.I2C.multiplexer;
  protected static final Port PORT = Port.kOnboard;
  protected boolean lockState = false;

  protected I2C i2c = new I2C(PORT, ADDR);

  protected TCA9548A() {
  }

  public void selectChannel(int channel) {
    while (lockState) {
      try {
        System.out.println("[TCA9548A] Waiting for lock on channel " + channel);
        wait(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
    }
    lockState = true;
    i2c.write(ADDR, 1 << channel);
  }

  public void release() {
    lockState = false;
  }
}
