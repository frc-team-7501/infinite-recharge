package frc.robot;

public final class Constants {
  public static final double drivetrainDefaultSpeedCoef = 0.65;
  public static final double drivetrainBoostSpeedCoef		= 1.0;

  
  public final class Ports {
    public final class CAN {
      public static final int pigeonIMU = 5;

      // Conveyor motors
      public static final int victorSPX = 6;

      // DriveTrain motors
      // public static final int talonBR = 11;
      // public static final int talonFR = 12;
      // public static final int talonBL = 13;
      // public static final int talonFL = 14;
      public static final int talonC = 10;  
      public static final int talonBR = 4;
      public static final int talonFR = 3;
      public static final int talonBL = 2;
      public static final int talonFL = 1;

      // Shooter motors
      public static final int sparkMaxBrushlessShooterA = 41;
      public static final int sparkMaxBrushlessShooterB = 42;

      // Control Panel motor
      public static final int sparkMaxBrushlessControlPanel = 43;
    }
  }
}
