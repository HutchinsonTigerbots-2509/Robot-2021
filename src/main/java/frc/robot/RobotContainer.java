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
import frc.robot.commands.Shooter.RampDownShooterWithProfile;
import frc.robot.commands.Shooter.RampUpShooter;
import frc.robot.commands.Shooter.RampUpShooterWithProfile;
import frc.robot.commands.Conveyor.ConveyorUp;
import frc.robot.commands.Conveyor.ConeyorDown;
import frc.robot.commands.Drivetrain.DriveStraight;
import frc.robot.commands.Drivetrain.Rotate;
import frc.robot.commands.Drivetrain.StrafeStraight;

import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Vision.LimelightVision;
import frc.robot.subsystems.Vision.USBVision2;
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
  private USBVision2 USBV = new USBVision2();

  // Joysticks
  public static Joystick OpStick = new Joystick(Constants.kOpStickID);
  public static Joystick CoOpStick = new Joystick(Constants.kCoOpStickID);


  // Joystick Buttons
  private JoystickButton bRampUpShooter;
  private JoystickButton bRampDownShooter;
  private JoystickButton bResetEncoders;
  private JoystickButton ManualConveyorUp;
  private JoystickButton ManualConveyorDown;
  private JoystickButton bConveyorUp;
  private JoystickButton bConveyorDown;
  private JoystickButton bIntakeIn;
  private JoystickButton bIntakeOut;
  // private JoystickButton bAutoCommands;
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

      /* CoOpStick Buttons */
      //Intake
      bIntakeIn = new JoystickButton(OpStick, Constants.kXboxRightBumper);
      bIntakeIn.whileHeld(new RunCommand(() -> sIntake.IntakeIn()));
      bIntakeIn.whenReleased(new InstantCommand(() -> sIntake.IntakeStop())); 
      
      bIntakeOut = new JoystickButton(OpStick, Constants.kXboxLeftBumper);
      bIntakeOut.whileHeld(new RunCommand(() -> sIntake.IntakeOut()));
      bIntakeOut.whenReleased(new InstantCommand(() -> sIntake.IntakeStop()));
      
      // Shooter
      bRampUpShooter = new JoystickButton(CoOpStick, Constants.kXboxRightBumper);
      bRampUpShooter.whenPressed(new RampUpShooter(sShooter, 1, 2.2)); // 0.7 FOR RED
      //bRampUpShooter.whenPressed(new RampUpShooterWithProfile(sShooter, 0.7, 2.2));


      bRampDownShooter = new JoystickButton(CoOpStick, Constants.kXboxLeftBumper);
      bRampDownShooter.whenPressed(new RampDownShooter(sShooter, 1.5));
      // bRampDownShooter.whenPressed(new RampDownShooterWithProfile(sShooter, 1.5));
      

      // Slider
      bSliderBlue = new JoystickButton(CoOpStick, Constants.kXboxButtonX);
      bSliderBlue.whenPressed(new InstantCommand(() -> sShooter.setFlapToBlue()));

      bSliderYellow = new JoystickButton(CoOpStick, Constants.kXboxButtonY);
      bSliderYellow.whenPressed(new InstantCommand(() -> sShooter.setFlapToYellow()));

      bSliderGreen = new JoystickButton(CoOpStick, Constants.kXboxButtonA);
      bSliderGreen.whenPressed(new InstantCommand(() -> sShooter.setFlapToGreen()));

      bSliderRed = new JoystickButton(CoOpStick, Constants.kXboxButtonB);
      bSliderRed.whenPressed(new InstantCommand(() -> sShooter.setFlapToRed()));

      // Conveyor
      bConveyorDown = new JoystickButton(CoOpStick, Constants.kXboxButtonStart);
      bConveyorDown.whenPressed(new ConeyorDown(sConveyor));

      bConveyorUp = new JoystickButton(CoOpStick, Constants.kXboxButtonBack);
      bConveyorUp.whenPressed(new ConveyorUp(sConveyor));

      // backwards
      ManualConveyorUp = new JoystickButton(CoOpStick, 9);
      ManualConveyorUp.whenPressed(new InstantCommand(() -> sConveyor.setConveyor(-0.7)));
      ManualConveyorUp.whenReleased(new InstantCommand(() -> sConveyor.setConveyor(0)));

      ManualConveyorDown = new JoystickButton(CoOpStick, 10);
      ManualConveyorDown.whenPressed(new InstantCommand(() -> sConveyor.setConveyor(0.7)));
      ManualConveyorDown.whenReleased(new InstantCommand(() -> sConveyor.setConveyor(0)));

    
    // ***** BOUNCE PATH ***** //
    // Description
    // bAutoCommands.whenPressed(new SequentialCommandGroup(
    //   //Resets the gyro and Encoders
    //   new InstantCommand(() -> sDrivetrain.ResetGyro()),
    //   new InstantCommand(() -> sDrivetrain.ResetEncoders()),
    //   //Drives straight until the average encoder count is above 64,000
    //   new DriveStraight(sDrivetrain, 0.7).withInterrupt(() -> sDrivetrain.EncoderAverage() > 64000),
    //   //Strafes left until the average encoder count is above 100,000
    //   new InstantCommand(() -> sDrivetrain.ResetEncoders()),
    //   new StrafeStraight(sDrivetrain, -0.8, 0).withInterrupt(() -> sDrivetrain.EncoderAverage() > 100000)
    // ));
  }

  // ***** BARREL RACING PATH ***** //
  // FULLY FUNCTIONAL. ~16.3 SECONDS WITH NEW BATTERY.
  // Markers with Vision tape are B2, D2, D5, B8, and D10
  // Markers without vision tape are B1 and D2
  
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
