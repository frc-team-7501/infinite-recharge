package frc.robot;

public final class Constants {
  public static final double drivetrainDefaultSpeedCoef = 0.65;
  public static final double drivetrainBoostSpeedCoef		= 1.0;

  
  public final class Ports {
    public final class I2C {
      public static final int multiplexer = 0x70;
    }
    public final class I2CMUX {
      public static final int colorSensorControlPanel   = 0;
      public static final int colorSensorConveyorBottom = 1;
      public static final int colorSensorConveyorMiddle = 2;
      public static final int colorSensorConveyorTop    = 3;
    }

    public final class CAN {
      // Misc. CAN devices
      public static final int pigeonIMU = 5;

      // Conveyor motors
      public static final int victorSPX = 6;
      public static final int talonConveyor = 10;  

      // DriveTrain motors
      public static final int talonFL = 1;
      public static final int talonBL = 2;
      public static final int talonFR = 3;
      public static final int talonBR = 4;

      // Shooter motors
      public static final int sparkMaxBrushlessShooterA = 41;
      public static final int sparkMaxBrushlessShooterB = 42;

      // Control Panel motor
      public static final int sparkMaxBrushlessControlPanel = 43;
    }
  }
}
