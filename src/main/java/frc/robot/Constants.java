package frc.robot;

public final class Constants {
  public final class Setpoints {
    public static final double intakeArmUp = 0; // TODO
    public static final double intakeArmDown = -0.363; // TODO
  }
  public final class Ports {
    public final class I2C {
      public static final int multiplexer = 0x70;
    }
    public final class I2CMUX {
      public static final int colorSensorControlPanel   = 0;
      public static final int colorSensorConveyorTop    = 1;
      public static final int colorSensorConveyorMiddle = 2;
      public static final int colorSensorConveyorBottom = 3;
    }

    public final class CAN {
      // VictorSPX
      public static final int victor_DriveFL        = 1;
      public static final int victor_DriveBL        = 2;
      public static final int victor_DriveFR        = 3;
      public static final int victor_DriveBR        = 4;
      public static final int victor_ConveyorTop    = 5;
      public static final int victor_ConveyorBottom = 6;

      // Spark MAX
      public static final int sparkmax_ShooterR     = 41;
      public static final int sparkmax_ShooterL     = 42;
      public static final int sparkmax_ControlPanel = 43;
      public static final int sparkmax_IntakeArm    = 44;
      public static final int sparkmax_Winch        = 45;
      public static final int sparkmax_HookLift     = 46;

      // Misc. CAN devices
      public static final int pigeonIMU             = 50;
    }
  }
}
