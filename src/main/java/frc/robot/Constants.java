package frc.robot;

public final class Constants {
  public static final double drivetrainDefaultSpeedCoef = 0.65;
  public static final double drivetrainBoostSpeedCoef		= 1.0;

  
  public final class Ports {
    public final class CAN {
      public static final int pigeonIMU = 5;

      // DriveTrain motors
      public static final int talonBR = 11;
      public static final int talonFR = 12;
      public static final int talonBL = 13;
      public static final int talonFL = 14;

      // Shooter motors
      public static final int sparkMaxBrushlessShooterA = 41;
      public static final int sparkMaxBrushlessShooterB = 42;

      // Control Panel motor
      public static final int sparkMaxBrushlessControlPanel = 43;
    }
  }
}
