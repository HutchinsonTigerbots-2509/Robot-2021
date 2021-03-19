package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


import frc.robot.commands.Shooter.RampDownShooter;
import frc.robot.commands.Shooter.RampUpShooter;
import frc.robot.commands.Conveyor.ConveyorUp;
import frc.robot.commands.Conveyor.ConeyorDown;
import frc.robot.commands.Drivetrain.DriveBackwardAutonomous;
import frc.robot.commands.Drivetrain.DriveStraight;
import frc.robot.commands.Drivetrain.Rotate;
import frc.robot.commands.Drivetrain.StrafeStraight;

import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Vision.LimelightVision;
import frc.robot.subsystems.Shooter.Shooter;

/**
 * We declare a majority of our robot here. This includes
 * 
 * <p>
 *  <li> - Button Bindings for TeleOp
 *  <li> - Joysticks
 *  <li> - Subsystems
 *  <li> - Autonomous Commands, as well as selecting the preferred one
 * </p>
 * 
 * The RobotContainer class is a neat way to store all of the objects concerning your
 * robot. This is also where the magic of the code happens. It connects the code that
 * you wrote into how it actually functions with the robot, whether a through a button 
 * or an autonomous command.
 */
public class RobotContainer {
  
  // Subsystems
  public Drivetrain sDrivetrain = new Drivetrain();
  private LimelightVision sVision = new LimelightVision();
  private Shooter sShooter = new Shooter();
  private Conveyor sConveyor = new Conveyor();
  private Intake sIntake = new Intake();

  // Joysticks
  public static Joystick OpStick = new Joystick(Constants.kOpStickID);
  public static Joystick CoOpStick = new Joystick(Constants.kCoOpStickID);


  // Joystick Buttons
  private JoystickButton bRampUpShooter;
  private JoystickButton bRampDownShooter;
  private JoystickButton bResetEncoders;
  private JoystickButton RUNCONVEYoR;
  private JoystickButton RUNCONVEYoR2;
  private JoystickButton bConveyorUp;
  private JoystickButton bConveyorDown;
  private JoystickButton bIntakeIn;
  private JoystickButton bIntakeOut;
  private JoystickButton bAutoCommands;
  private JoystickButton bExtendIntake;
  private JoystickButton bRetractIntake;

  private JoystickButton bSliderGreen;
  private JoystickButton bSliderYellow;
  private JoystickButton bSliderBlue;
  private JoystickButton bSliderRed;


  //Autonomous Commands
  private double AutoStartTime;
  
  /**
   * This constructor calls configureButtonBindings(). When that method runs, 
   * it will assign commands to buttons on the joystick. Create an instance
   * of RobotContainer in Robot.java to use. 
   */
  public RobotContainer() {
    configureButtonBindings();
  }

  /**
   * This method is where you connect commands to buttons on
   * the joysticks.
   * 
   * <p>
   * OpStick is generally reserved for driving the robot around, and other
   * rarely used buttons.
   * 
   * <p>
   * CoOpStick is used for all of the commands that are used a lot during the
   * round. 
   */
  private void configureButtonBindings() {
      /* OpStick Buttons */
      bExtendIntake = new JoystickButton(OpStick, Constants.kXboxButtonA);
      bExtendIntake.whenPressed(new InstantCommand(() -> sIntake.Extend()));

      bRetractIntake = new JoystickButton(OpStick, Constants.kXboxButtonX);
      bRetractIntake.whenPressed(new InstantCommand(() -> sIntake.Retract()));

      bResetEncoders = new JoystickButton(OpStick, Constants.kXboxLeftBumper);
      bResetEncoders.whenPressed(new InstantCommand(() -> sDrivetrain.ResetEncoders()));

      /* CoOpStick Buttons */
      //Intake
      /*
      bIntakeIn = new JoystickButton(OpStick, Constants.kXboxRightBumper);
      bIntakeIn.whileHeld(new RunCommand(() -> sIntake.IntakeIn()));
      bIntakeIn.whenReleased(new InstantCommand(() -> sIntake.IntakeStop())); 
      
      bIntakeOut = new JoystickButton(OpStick, Constants.kXboxLeftBumper);
      bIntakeOut.whileHeld(new RunCommand(() -> sIntake.IntakeOut()));
      bIntakeOut.whenReleased(new InstantCommand(() -> sIntake.IntakeStop()));
      */
      
      // Shooter
      // bRampUpShooter = new JoystickButton(CoOpStick, Constants.kXboxRightBumper);
      // bRampUpShooter.whenPressed(new RampUpShooter(sShooter, 1, 2.2));

      // bRampDownShooter = new JoystickButton(CoOpStick, Constants.kXboxLeftBumper);
      // bRampDownShooter.whenPressed(new RampDownShooter(sShooter, 2.2));

      // Zone Switchers
      /*
      bSliderBlue = new JoystickButton(CoOpStick, Constants.kXboxButtonX);
      bSliderBlue.whenPressed(new InstantCommand(() -> sShooter.setFlapToBlue()));

      bSliderYellow = new JoystickButton(CoOpStick, Constants.kXboxButtonY);
      bSliderYellow.whenPressed(new InstantCommand(() -> sShooter.setFlapToYellow()));

      bSliderGreen = new JoystickButton(CoOpStick, Constants.kXboxButtonA);
      bSliderGreen.whenPressed(new InstantCommand(() -> sShooter.setFlapToGreen()));

      bSliderRed = new JoystickButton(CoOpStick, Constants.kXboxButtonB);
      bSliderRed.whenPressed(new InstantCommand(() -> sShooter.setFlapToRed()));
      */

      // Conveyor
      /*
      bConveyorDown = new JoystickButton(CoOpStick, Constants.kXboxButtonStart);
      bConveyorDown.whenPressed(new ConeyorDown(sConveyor));
      // bConveyorDown.whenPressed(new InstantCommand(() -> sConveyor.MoveConveyor(-0.5)));
      // bConveyorDown.whenReleased(new InstantCommand(() -> sConveyor.MoveConveyor(0)));
      
      bConveyorUp = new JoystickButton(CoOpStick, Constants.kXboxButtonBack);
      bConveyorUp.whenPressed(new ConveyorUp(sConveyor));
      // bConveyorUp.whenPressed(new InstantCommand(() -> sConveyor.MoveConveyor(0.5)));
      // bConveyorUp.whenReleased(new InstantCommand(() -> sConveyor.MoveConveyor(0)));
      RUNCONVEYoR = new JoystickButton(CoOpStick, 9);
      RUNCONVEYoR.whenPressed(new InstantCommand(() -> sConveyor.setConveyor(-1)));
      RUNCONVEYoR.whenReleased(new InstantCommand(() -> sConveyor.setConveyor(0)));

      RUNCONVEYoR2 = new JoystickButton(CoOpStick, 10);
      RUNCONVEYoR2.whenPressed(new InstantCommand(() -> sConveyor.setConveyor(0.7)));
      RUNCONVEYoR2.whenReleased(new InstantCommand(() -> sConveyor.setConveyor(0)));
      */
  }

  // ***** BOUNCE PATH ***** //
  // LIL SKETCHY, BUT MOSTLY WORKS. ~14.2 SECONDS, REGARDLESS OF BATTERY CHARGE
  // Red Markers on A3, A6, and A9
  // Markers with vision tape are B4, B7, B8, B10, D5, D7, & D8
  // Markers without vision tape are B1, B2, B5, & B11
  // Markers that don't matter are D1, D2, D3, D10, D11, & E3
  private Command AutoCommand = new SequentialCommandGroup(
    //Sets the start time for the timer
    new InstantCommand(() -> AutoStartTime = Timer.getFPGATimestamp()),
    //Resets the gyro and stops compressor
    new InstantCommand(() -> sDrivetrain.ResetGyro()),
    new InstantCommand(() -> Robot.mCompressor.stop()),
    new InstantCommand(() -> sVision.SwitchPipeline(1)), // Targets largest
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 1)),
    // Strafes right until it sees the first marker
    new StrafeStraight(sDrivetrain, 1, 0, false).withInterrupt(() -> sVision.getTargetX() < 18 && sVision.getTargetY() > 2), //TargetX 18, true
    // Knocks over the first marker
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 2)),
    new DriveStraight(sDrivetrain, 0.7, 0, false).withInterrupt(() -> !sVision.GetTargetFound()),
    new WaitCommand(0.1),
    // Backs up (encoders)
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 3)),
    new InstantCommand(() -> sDrivetrain.ResetEncoders()),
    // new DriveStraight(sDrivetrain, -0.7, 0, true).withInterrupt(() -> sDrivetrain.EncoderAverage() > 59000),
    new DriveBackwardAutonomous(sDrivetrain, -0.7).withInterrupt(() -> sDrivetrain.EncoderAverage() > 55000), //59000
    // Strafes right until it finds the marker
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 4)),
    new StrafeStraight(sDrivetrain, 1, 0, false).withTimeout(0.2), //true?
    new StrafeStraight(sDrivetrain, 1, 0, false).withInterrupt(()-> sVision.getTargetX() < 15 && sVision.getTargetX() > 11 && sVision.GetTargetFound()),
    // Drives backward
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 5)),
    new DriveStraight(sDrivetrain, -1, 0, true).withInterrupt(()-> sVision.getTargetY() > 16.5),
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Y", sVision.getTargetY())),
    // Strafes right until it sees the second marker
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 6)),
    new InstantCommand(() -> sVision.SwitchPipeline(5)), // Targets highest
    new StrafeStraight(sDrivetrain, 1, 0, true).withInterrupt(() -> sVision.getTargetX() > 5 && sVision.getTargetX() < 10&& sVision.getTargetY() > 5),
    new StrafeStraight(sDrivetrain, 1, 0, false).withInterrupt(() -> sVision.getTargetX() < 1),
    // Knocks over the second marker
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 7)),
    new DriveStraight(sDrivetrain, 0.8, 0, true).withInterrupt(() -> !sVision.GetTargetFound()),
    // Drives backwards
    new InstantCommand(() -> sVision.SwitchPipeline(7)), // Targets Rightmost
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 8)),
    new DriveStraight(sDrivetrain, -0.8, 0, true).withInterrupt(() -> sVision.getTargetY() > 14 && sVision.getTargetX() > 5),
    // Strafes right until it sees the final marker
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 9)),
    new InstantCommand(() -> sVision.SwitchPipeline(6)), // Tri target
    new StrafeStraight(sDrivetrain, 1, 0, true).withInterrupt(() -> sVision.getTargetX() < 5 && sVision.GetTargetFound()),
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 10)),
    new InstantCommand(() -> sVision.SwitchPipeline(5)), // Targest highest
    new StrafeStraight(sDrivetrain, 1, 0, false).withInterrupt(() -> sVision.getTargetX() < 7 && sVision.GetTargetFound()),
    // Knocks over third marker
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 11)),
    new DriveStraight(sDrivetrain, 1, 0, true).withInterrupt(() -> !sVision.GetTargetFound()),
    // Drives backward
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 12)),
    new WaitCommand(0.1),
    new InstantCommand(() -> sDrivetrain.ResetEncoders()),
    new DriveBackwardAutonomous(sDrivetrain, -0.7).withInterrupt(() -> sDrivetrain.EncoderAverage() > 71000), // 70000
    // Strafes right
    new InstantCommand(() -> SmartDashboard.putNumber("Auto Sequence", 13)),
    new InstantCommand(() -> sVision.SwitchPipeline(1)),
    new StrafeStraight(sDrivetrain, 1, 0, true).withInterrupt(() -> sVision.getTargetX() < -24 && sVision.getTargetY() < 5),
    //Prints out the time it took to run the path
    new InstantCommand(() -> SmartDashboard.putNumber("AutoTime", Timer.getFPGATimestamp() - AutoStartTime)
  ));

  // ***** BARREL RACING PATH ***** //
  // FULLY FUNCTIONAL. ~16.3 SECONDS WITH NEW BATTERY.
  // Markers with Vision tape are B2, D2, D5, B8, and D10
  // Markers without vision tape are B1 and D2
  /*
  private Command AutoCommand = new SequentialCommandGroup(
    //Sets the start time for the timer
    new InstantCommand(() -> AutoStartTime = Timer.getFPGATimestamp()),
    //Drives straight to set up for first marker
    new InstantCommand(() -> sDrivetrain.ResetGyro()),
    new DriveStraight(sDrivetrain, 0.8).withTimeout(0.5),
    // new WaitCommand(0.2),
    //Rotates around first marker
    new Rotate(sDrivetrain, sVision, -0.6, -13.5).withInterrupt(() -> sDrivetrain.GetGyroAngle() > 350), //315
    //Strafes + Drives to set up for second marker
    new StrafeStraight(sDrivetrain, -1).withTimeout(0.3),
    new DriveStraight(sDrivetrain, 1).withTimeout(0.4), //0.6
    // new WaitCommand(0.2),
    //Turns if the robot isn't facing the marker or is facing the wrong marker
    new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, -0.5)).withInterrupt(() -> sVision.getTargetX() <= 0),
    //Rotates around second marker
    new Rotate(sDrivetrain, sVision, 0.6, -13.5).withInterrupt(() -> sDrivetrain.GetGyroAngle() < 55),
    //Strafes + Drives to set up for third marker
    new RunCommand(() -> sDrivetrain.DriveWithStrafe(0.9, 0.3, 0)).withTimeout(0.4), //0.7 (timeout)
    new DriveStraight(sDrivetrain, 1).withTimeout(0.4),
    //Turns if the robot isn't facing the marker
    new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, -0.5)).withTimeout(0.2),
    new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, -0.5)).withInterrupt(() -> sVision.GetTargetFound()),
    //Rotates around third marker
    new Rotate(sDrivetrain, sVision, 0.6, -13.5).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -170),
    //Strafes to the side for final run
    new StrafeStraight(sDrivetrain, 1).withTimeout(0.2),
    //Switches pipeline to dual target mode
    new InstantCommand(() -> sVision.SwitchPipeline(2)),
    //Drives forward until several requirements have been satisfied
    new ParallelRaceGroup(
      new DriveStraight(sDrivetrain, 1, -180),
      new SequentialCommandGroup(
        new WaitCommand(0.5),
        new WaitCommand(1000).withInterrupt(() -> sVision.GetTargetFound()),
        new WaitCommand(0.5),
        new WaitCommand(1000).withInterrupt(() -> !sVision.GetTargetFound()),
        // CHECK THIS WAITCOMMAND BEFORE RUNNING
        new WaitCommand(0.2)
      )
    ),
    //Switches pipeline back to original pipeline
    new InstantCommand(() -> sVision.SwitchPipeline(Constants.kLimelightStartingPipeline)),
    //Prints out the time it took to run the path
    new InstantCommand(() -> SmartDashboard.putNumber("AutoTime", Timer.getFPGATimestamp() - AutoStartTime))
  );
  */
  

  // ***** SLALOM PATH
  // Mostly functional. ~14.0 seconds with a new battery
  // Markers with vision tape are D2, D4, D5, D6, D7, D8, and D10
  // Markers without vision tape are B1, B2, and D1
  /*
  private Command AutoCommand = new SequentialCommandGroup(
    //Sets the start time for the timer
    new InstantCommand(() -> AutoStartTime = Timer.getFPGATimestamp()),
    // Zeros the gyro and encoders
    new InstantCommand(() -> sDrivetrain.ResetEncoders()),
    new InstantCommand(() -> sDrivetrain.ResetGyro()),
    // Drives forward (encoder based)
    new DriveStraight(sDrivetrain, 1).withInterrupt(() -> sDrivetrain.EncoderAverage() > 41500),
    // Strafes to the left
    new StrafeStraight(sDrivetrain, -1, 0).withInterrupt(() -> sVision.getTargetX() > 24 && sVision.getTargetY() > 0),
    // Drives until no more vision targets are found
    new DriveStraight(sDrivetrain, 1, 0).withInterrupt(() -> !sVision.GetTargetFound()),
    // Turns until the robot sees the final marker
    new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, 0.5)).withInterrupt(() -> sVision.GetTargetFound()),
    // Rotates around the final marker
    new Rotate(sDrivetrain, sVision, 0.6, -20).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -20),
    new Rotate(sDrivetrain, sVision, 0.6, -14).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -170),
    // Strafes to the right & drives forward
    new StrafeStraight(sDrivetrain, 1, -180).withTimeout(0.3),
    new InstantCommand(() -> sDrivetrain.ResetEncoders()),
    new DriveStraight(sDrivetrain, 1, -180).withInterrupt(() -> sDrivetrain.EncoderAverage() > 65000),
    // Strafes to the left
    new StrafeStraight(sDrivetrain, -1, -180).withInterrupt(() -> sVision.getTargetX() > 8 && sVision.getTargetY() > -5),
    new DriveStraight(sDrivetrain, 1, -180).withInterrupt(() -> !sVision.GetTargetFound()),
    // Turns until the robot sees the marker
    new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, 0.5)).withInterrupt(() -> sVision.GetTargetFound()),
    // Rotate around the marker
    new Rotate(sDrivetrain, sVision, 0.6, -16).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -260),
    // Strafe into the end zone
    new StrafeStraight(sDrivetrain, 1, -270).withTimeout(0.3),
    //Prints out the time it took to run the path
    new InstantCommand(() -> SmartDashboard.putNumber("AutoTime", Timer.getFPGATimestamp() - AutoStartTime))
  );
  */

  /**
   * Get Auto Command.
   * 
   * <p>
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // The Command to run in Autonomous
    return AutoCommand;
  }
}
