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

import frc.robot.commands.Conveyor.ConveyorShiftDown;
import frc.robot.commands.Conveyor.ConveyorShiftUp;

import frc.robot.commands.Shooter.RampDownShooter;
import frc.robot.commands.Shooter.RampUpShooter;

import frc.robot.commands.Drivetrain.DriveStraight;
import frc.robot.commands.Drivetrain.Rotate;
import frc.robot.commands.Drivetrain.StrafeStraight;

import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Shooter;

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
  private Vision sVision = new Vision();
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
  private JoystickButton bConveyorUp;
  private JoystickButton bConveyorDown;
  private JoystickButton bIntakeIn;
  private JoystickButton bIntakeOut;

  //Autonomous Commands
  private double AutoStartTime;
  
  /**
   * This constructor calls configureButtonBindings(). When that method runs, 
   * it will assign commands to buttons on the joystick. 
   * 
   * <p>
   * This means you only have to create an instance of RobotContainer in 
   * Robot.java to make TeleOp work.
   */
  public RobotContainer() {
    configureButtonBindings();
  }

  /**
   * This method is where you connect the commands you wrote to the buttons on
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
    bRampUpShooter = new JoystickButton(OpStick, Constants.kXboxButtonA);
    bRampUpShooter.whenPressed(new RampUpShooter(sShooter, 0.8, 2.2));

    bRampDownShooter = new JoystickButton(OpStick, Constants.kXboxButtonB);
    bRampDownShooter.whenPressed(new RampDownShooter(sShooter, 2.2));

    bResetEncoders = new JoystickButton(OpStick, Constants.kXboxLeftBumper);
    bResetEncoders.whenPressed(new InstantCommand(() -> sDrivetrain.ResetEncoders()));

    bConveyorDown = new JoystickButton(OpStick, Constants.kXboxButtonX);
    //bConveyorDown.whenPressed(new ConveyorShiftDown(sConveyor));
    bConveyorDown.whenPressed(new InstantCommand(() -> sConveyor.MoveConveyor(-0.5)));
    bConveyorDown.whenReleased(new InstantCommand(() -> sConveyor.MoveConveyor(0)));
    
    bConveyorUp = new JoystickButton(OpStick, Constants.kXboxButtonY);
    //bConveyorUp.whenPressed(new ConveyorShiftUp(sConveyor));
    bConveyorUp.whenPressed(new InstantCommand(() -> sConveyor.MoveConveyor(0.5)));
    bConveyorUp.whenReleased(new InstantCommand(() -> sConveyor.MoveConveyor(0)));

    bIntakeIn = new JoystickButton(OpStick, Constants.kXboxRightBumper);
    bIntakeIn.whileHeld(new InstantCommand(() -> sIntake.IntakeIn()));
    bIntakeIn.whenReleased(new InstantCommand(() -> sIntake.IntakeStop()));
    //bIntakeIn.whileHeld(new InstantCommand(() -> sConveyor.setAgitator(.75)));
    //bIntakeIn.whenReleased(new InstantCommand(() -> sConveyor.setAgitator(0)));

    bIntakeOut = new JoystickButton(OpStick, Constants.kXboxButtonBack);
    bIntakeOut.whileHeld(new InstantCommand(() -> sIntake.IntakeIn()));
    bIntakeOut.whenReleased(new InstantCommand(() -> sIntake.IntakeOut()));
    //bIntakeOut.whileHeld(new InstantCommand(() -> sConveyor.setAgitator(-0.75)));
    //bIntakeOut.whenReleased(new InstantCommand(() -> sConveyor.setAgitator(0)));

    

    //bAutoCommands = new JoystickButton(OpStick, Constants.kXboxButtonStart);

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

    // ***** BARREL RACING PATH ***** //
    /*
    // Semi-functional. Will need to be redone once more weight is added to the robot.
    bAutoCommands.whenPressed(new SequentialCommandGroup(
      //Drives straight to set up for first marker
      new InstantCommand(() -> sDrivetrain.ResetGyro()),
      new DriveStraight(sDrivetrain, 1).withTimeout(0.5),
      new WaitCommand(0.2),
      //Rotates around first marker
      new Rotate(sDrivetrain, sVision, -0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() > 315), //320
      //Strafes + Drives to set up for second marker
      new StrafeStraight(sDrivetrain, -1).withTimeout(0.3),
      new DriveStraight(sDrivetrain, 1).withTimeout(0.6),
      new WaitCommand(0.2),
      //Turns if the robot isn't facing the marker
      new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, 0.5)).withInterrupt(() -> sVision.GetTargetFound()),
      //Rotates around second marker
      new Rotate(sDrivetrain, sVision, 0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() < 55),
      //Strafes + Drives to set up for third marker
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(0.9, 0.3, 0)).withTimeout(0.7),
      new DriveStraight(sDrivetrain, 1).withTimeout(0.4),
      // new WaitCommand(0.4),
      //Turns if the robot isn't facing the marker
      new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, -0.5)).withInterrupt(() -> sVision.GetTargetFound()),
      //Rotates around third marker
      new Rotate(sDrivetrain, sVision, 0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -170),
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
          new WaitCommand(0.35)
        )
      ),
      //Switches pipeline back to original pipeline
      new InstantCommand(() -> sVision.SwitchPipeline(Constants.kLimelightStartingPipeline))
    ));
    */

    // ***** SLALOM PATH ***** //
    // WILL NOT WORK LIKE AT ALL DO NOT RUN THIS WITHOUT CECE OR ELSE EXPECT CHAOS
    // LongDrive, GryoTurn, and DriveToVision commands all have to be rewritten
    /*
    bAutoCommands.whenPressed(new SequentialCommandGroup(
      //Diagonal strafes to the left of the first marker
      new DriveToVision(sDrivetrain, sVision, 12.9), //12.7
      new WaitCommand(0.2),
      //Drives forward until the robot doesn't see a target
      new LongDrive(sDrivetrain, sVision),
      //Turns right
      new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, -0.5)).withTimeout(0.3),
      new WaitCommand(0.2),
      new InstantCommand(() -> sDrivetrain.ResetGyro()),
      new WaitCommand(0.2),
      //Spins the robot around the marker
      new Rotate(sDrivetrain, sVision, 0.5, -23).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -250),//240
      new WaitCommand(0.2),
      //Strafes to the right until a target X & Y are hit
      new StrafeStraight(sDrivetrain, 0.7).withInterrupt(() -> sVision.getTargetX() < 27 && sVision.getTargetY() > 5),
      new WaitCommand(0.2),
      //Drives forward (time based)
      new DriveStraight(sDrivetrain, 0.7).withTimeout(1.25),
      new WaitCommand(0.2),
      //Turns to face the start using the gyro
      new GyroTurn(-217.5, sDrivetrain, 0.5), //-220
      new WaitCommand(0.2),
      //Drives forward until the robot doesn't see a target
      new LongDrive(sDrivetrain, sVision),
      //Drives forward until the robot is a certain distance from the final marker
      new DriveStraight(sDrivetrain, 0.7).withInterrupt(() -> sVision.getTargetY() < 10 && sVision.getTargetY() != 0),
      new WaitCommand(0.2),
      //Strafes forward and to the right based off vision
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(1, 0.3, 0)).withInterrupt(() -> sVision.getTargetY() < -20),
      //Strafes diagonally, then drives forward. Time based.
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(1, 0.3, 0)).withTimeout(0.35),
      new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(1, 0)).withTimeout(0.7)
      ));
      */

  }

  
  // ********** AUTONOMOUS COMMANDS ********* //

  //Barrel Racing Path
  private Command cAutoCommands = new SequentialCommandGroup(
    //Sets the start time for the timer
    new InstantCommand(() -> AutoStartTime = Timer.getFPGATimestamp()),
    //Drives straight to set up for first marker
    new InstantCommand(() -> sDrivetrain.ResetGyro()),
    new DriveStraight(sDrivetrain, 1).withTimeout(0.5),
    // new WaitCommand(0.2),
    //Rotates around first marker
    new Rotate(sDrivetrain, sVision, -0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() > 315), //320
    //Strafes + Drives to set up for second marker
    new StrafeStraight(sDrivetrain, -1).withTimeout(0.3),
    new DriveStraight(sDrivetrain, 1).withTimeout(0.6),
    // new WaitCommand(0.2),
    //Turns if the robot isn't facing the marker
    new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, 0.5)).withInterrupt(() -> sVision.GetTargetFound()),
    //Rotates around second marker
    new Rotate(sDrivetrain, sVision, 0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() < 55),
    //Strafes + Drives to set up for third marker
    new RunCommand(() -> sDrivetrain.DriveWithStrafe(0.9, 0.3, 0)).withTimeout(0.7),
    new DriveStraight(sDrivetrain, 1).withTimeout(0.4),
    //Turns if the robot isn't facing the marker
    new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, -0.5)).withInterrupt(() -> sVision.GetTargetFound()),
    //Rotates around third marker
    new Rotate(sDrivetrain, sVision, 0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -170),
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
        new WaitCommand(0.3)
      )
    ),
    //Switches pipeline back to original pipeline
    new InstantCommand(() -> sVision.SwitchPipeline(Constants.kLimelightStartingPipeline)),
    //Prints out the time it took to run the path
    new InstantCommand(() -> SmartDashboard.putNumber("AutoTime", Timer.getFPGATimestamp() - AutoStartTime))
  );

  /**
   * Test Strafe Command
   */
  private Command strafeTest = new SequentialCommandGroup(
    new StrafeStraight(sDrivetrain, 0.9).withTimeout(3)
    // new StrafeStraight(sDrivetrain, -0.9).withTimeout(3), new WaitCommand(1), new StrafeStraight(sDrivetrain, 0.9).withTimeout(3)
    // new StrafeStraight(sDrivetrain, -0.9).withTimeout(0.75), new WaitCommand(1), new StrafeStraight(sDrivetrain, 0.9).withTimeout(0.75), new WaitCommand(1), new StrafeStraight(sDrivetrain, -0.9).withTimeout(0.75), new WaitCommand(1), new StrafeStraight(sDrivetrain, 0.9).withTimeout(0.75)
  );

  //private Command ConveyorTest = new ConveyorShiftUp(sConveyor);
  
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
    return null;
  }
}
