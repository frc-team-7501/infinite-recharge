package org.frc7501.robot2020;

import org.frc7501.robot2020.commands.autonomous.ControlPanelPositionCommand;
import org.frc7501.robot2020.commands.autonomous.ControlPanelRotationCommand;
import org.frc7501.robot2020.commands.autonomous.ConveyorIntakeCommand;
import org.frc7501.robot2020.commands.autonomous.DriveTrainMoveCommand;
import org.frc7501.robot2020.commands.autonomous.DriveTrainTurnCommand;
import org.frc7501.robot2020.commands.autonomous.DriveTrainTurnPIDCommand;
import org.frc7501.robot2020.commands.autonomous.DriveTrainTurnPID2Command;
import org.frc7501.robot2020.commands.autonomous.IntakeArmDownCommand;
import org.frc7501.robot2020.commands.autonomous.IntakeArmUpCommand;
import org.frc7501.robot2020.commands.autonomous.LimelightAlignTargetCommand;
import org.frc7501.robot2020.commands.autonomous.ShooterFireCommand;
import org.frc7501.robot2020.commands.manual.ClimberControlCommand;
import org.frc7501.robot2020.commands.manual.TeleopDriveCommand;
import org.frc7501.robot2020.subsystems.Climber;
import org.frc7501.robot2020.subsystems.ControlPanel;
import org.frc7501.robot2020.subsystems.Conveyor;
import org.frc7501.robot2020.subsystems.DriveTrain;
import org.frc7501.robot2020.subsystems.IntakeArm;
import org.frc7501.robot2020.subsystems.Limelight;
import org.frc7501.robot2020.subsystems.Shooter;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  // Create joysticks
  private final Joystick stick = new Joystick(0);
  private final XboxController controller = new XboxController(1);

  // Create subsystems
  private final DriveTrain driveTrain = new DriveTrain();
  private final Limelight limelight = new Limelight();
  private final Shooter shooter = new Shooter();
  private final ControlPanel controlPanel = new ControlPanel();
  private final IntakeArm intakeArm = new IntakeArm();
  private final Climber climber = new Climber();
  private final Conveyor conveyor = new Conveyor();

  // Create commands
  private final ClimberControlCommand climberControlCommand = new ClimberControlCommand(climber,
      () -> controller.getY(Hand.kLeft), () -> controller.getY(Hand.kRight) * 0.25);
  private final ControlPanelPositionCommand controlPanelPositionCommand = new ControlPanelPositionCommand(controlPanel);
  private final ControlPanelRotationCommand controlPanelRotationCommand = new ControlPanelRotationCommand(controlPanel);
  private final ConveyorIntakeCommand conveyorIntakeCommand = new ConveyorIntakeCommand(conveyor);
  private final IntakeArmDownCommand intakeArmDownCommand = new IntakeArmDownCommand(intakeArm);
  private final IntakeArmUpCommand intakeArmUpCommand = new IntakeArmUpCommand(intakeArm);
  private final LimelightAlignTargetCommand limelightAlignTargetCommand = new LimelightAlignTargetCommand(driveTrain,
      limelight);
  private final TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand(driveTrain, () -> -stick.getY(),
      () -> stick.getX(), () -> stick.getRawButton(1), () -> 1 - stick.getThrottle());

  // Auton variable
  private SendableChooser<Command> autonChooser = new SendableChooser<>();


  // Autonomous

  // Competition Auton 
  private final Command auton0 = new SequentialCommandGroup(
      new ShooterFireCommand(shooter, conveyor),
      new InstantCommand(() -> driveTrain.setBrakeMode(true)), 
      new DriveTrainTurnPIDCommand(driveTrain, -90),
      new WaitCommand(0.7), 
      new DriveTrainMoveCommand(driveTrain, 1040, 0.28), 
      new WaitCommand(1),
      new DriveTrainTurnPIDCommand(driveTrain, -91), 
      new WaitCommand(0.7),
      new ParallelDeadlineGroup(new DriveTrainMoveCommand(driveTrain, 3800, 0.25), new IntakeArmDownCommand(intakeArm), new ConveyorIntakeCommand(conveyor)), 
      new ParallelDeadlineGroup(new WaitCommand(0.5), new ConveyorIntakeCommand(conveyor)),
      new IntakeArmUpCommand(intakeArm),
      new DriveTrainTurnPID2Command(driveTrain, -160),
      new DriveTrainMoveCommand(driveTrain, 3800, 0.53),
      new WaitCommand(0.5),
      new LimelightAlignTargetCommand(driveTrain, limelight),
      new DriveTrainMoveCommand(driveTrain, 0, 0),
      new ShooterFireCommand(shooter, conveyor)
  );

  // AutoNav challenge 1 (Barrel Racing Path)
  private final Command auton1 = new SequentialCommandGroup(
    new InstantCommand(() -> driveTrain.setBrakeMode(true)), 
    new DriveTrainMoveCommand(driveTrain, 2250, 0.6),
    new DriveTrainTurnPIDCommand(driveTrain, -90),
    new DriveTrainMoveCommand(driveTrain, 650, 0.4),
    new DriveTrainTurnPIDCommand(driveTrain, -90),
    new DriveTrainMoveCommand(driveTrain, 650, 0.4),
    new DriveTrainTurnPIDCommand(driveTrain, -90),
    new DriveTrainMoveCommand(driveTrain, 650, 0.4),
    new DriveTrainTurnPIDCommand(driveTrain, -90),
    new DriveTrainMoveCommand(driveTrain,2250 , 0.6)

  );

  // AutoNav challenge 2 (Slalom Path)
  private final Command auton2 = new SequentialCommandGroup(
  );
  
  // AutoNav challenge 3 (Bounce Path)
  private final Command auton3 = new SequentialCommandGroup(
    new InstantCommand(() -> driveTrain.setBrakeMode(true)), 
    new DriveTrainMoveCommand(driveTrain, 675, 0.4),
    new WaitCommand(.7),
    new DriveTrainTurnPIDCommand(driveTrain, 90),
    new WaitCommand(.7),
    new DriveTrainMoveCommand(driveTrain, 630, 0.5),
    new WaitCommand(.7),
    new DriveTrainMoveCommand(driveTrain, -400, 0.5),
    new WaitCommand(.7),
    new DriveTrainTurnPIDCommand(driveTrain, 40),
    new WaitCommand(.7),
    new DriveTrainMoveCommand(driveTrain, -1000, 0.5),
    new WaitCommand(.7),
    new DriveTrainTurnPIDCommand(driveTrain, -60)
  );

  // Autonav Challenge 4 (Lightspeed Circuit Path)
  private final Command auton4 = new SequentialCommandGroup(
  );

  public RobotContainer() {
    configureButtonBindings();
    setDefaultCommands();

    autonChooser.setDefaultOption("Auton0(Competition auton)", auton0);
    autonChooser.addOption("Auton1 (barrel race)", auton1);
    autonChooser.addOption("Auton2 (Slalom Path)", auton2);
    autonChooser.addOption("Auton3 (Bounce)", auton3);
    autonChooser.addOption("Auton4 (Lightspeed Circuit)", auton4);

    SmartDashboard.putData("auton", autonChooser);
  }

  /**
   * Attach joystick buttons to commands.
   */
  private void configureButtonBindings() {
    // ===========================================================
    // Joystick buttons
    // ===========================================================

    // NOTE: stick button 1 (trigger) is used inline in the drive command (see
    // above)

    final var stickButton_Thumb = new JoystickButton(stick, 2);

    stickButton_Thumb.whileActiveOnce(limelightAlignTargetCommand);

    // ===========================================================
    // Controller buttons
    // ===========================================================

    final var controllerButton_A = new JoystickButton(controller, 1);
    final var controllerButton_B = new JoystickButton(controller, 2);
    final var controllerButton_X = new JoystickButton(controller, 3);
    final var controllerButton_Y = new JoystickButton(controller, 4);
    final var controllerButton_LB = new JoystickButton(controller, 5);
    final var controllerButton_RB = new JoystickButton(controller, 6);
    final var controllerButton_Back = new JoystickButton(controller, 7);
    final var controllerButton_Start = new JoystickButton(controller, 8);

    controllerButton_A.whileActiveOnce(new ShooterFireCommand(shooter, conveyor));
    controllerButton_B.whileActiveOnce(intakeArmDownCommand.andThen(conveyorIntakeCommand))
        .whenInactive(intakeArmUpCommand);

    controllerButton_Back.whenPressed(controlPanelPositionCommand);
    controllerButton_Start.whenPressed(controlPanelRotationCommand);
    controllerButton_X.whenPressed(new DriveTrainTurnPIDCommand(driveTrain, -90));
  }

  /**
   * Set the default commands for subsystems.
   */
  private void setDefaultCommands() {
    climber.setDefaultCommand(climberControlCommand);
    driveTrain.setDefaultCommand(teleopDriveCommand);
  }

  /**
   * Returns the selected autonomous command.
   */
  public Command getAutonomousCommand() {
    // TODO: SendableChooser?
    return autonChooser.getSelected();
    // whichAuton = 0; 

    // Competition Auton
    // if(whichAuton == 0) { 
      // return auton0;
    // }
    // return auton1;

    // AutoNav 3 - Bounce
    // return auton3; 
  }
}
