// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.Rotate;

/**
 * RobotContainer class.
 * 
 * <p>This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 * 
 * @version 0.1.1 February 6, 2021
 * @author First Generated
 * @author Noah Sturges
 * @author Quinton MacMullan
 */
public class RobotContainer {
  // Subsystems
  private Drivetrain sDrivetrain = new Drivetrain();
  private Vision sVision = new Vision();

  // Joysticks
  public static Joystick OpStick = new Joystick(Constants.kOpStickID);
  public static Joystick CoOpStick = new Joystick(Constants.kCoOpStickID);

  //Joystick Buttons
  private JoystickButton AutoButton; // A temporary button for running Autonomous Commands

  /**
   * RobotContainer Constructor.
   * 
   * <p>The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Configure Button Bindings.
   * 
   * <p>Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    AutoButton = new JoystickButton(OpStick, Constants.kXboxButtonStart);

    // ***** BARREL RACING PATH ***** //
    // DOES NOT WORK LIKE AT ALL
    AutoButton.whenPressed(new SequentialCommandGroup(
      //Drives straight to set up for first marker
      new InstantCommand(() -> sDrivetrain.ResetGyro()),
      new DriveStraight(sDrivetrain, 1).withTimeout(0.6),
      new WaitCommand(0.2),
      //Rotates around first marker
      new Rotate(sDrivetrain, sVision, -0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() > 320),
      //Strafes + Drives to set up for second marker
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(-0.9, 0, 0)).withTimeout(0.35),
      new DriveStraight(sDrivetrain, 1).withTimeout(0.9),
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(0.4, 0.5, 0)).withTimeout(0.6),
      new WaitCommand(0.2),
      //Rotates around second marker
      new Rotate(sDrivetrain, sVision, 0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() < 55),
      //Strafes + Drives to set up for third marker
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(0.9, -0.3, 0)).withTimeout(0.7),
      new DriveStraight(sDrivetrain, 1).withTimeout(0.7),
      new WaitCommand(0.4),
      //Turns if the robot isn't facing the marker
      new RunCommand(() -> sDrivetrain.DriveWithoutStrafe(0, 0.5)).withInterrupt(() -> sVision.GetTargetFound()),
      //Rotates around third marker
      new Rotate(sDrivetrain, sVision, 0.7, -25).withInterrupt(() -> sDrivetrain.GetGyroAngle() < -170),
      //Strafes to the side for final run
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(1, 0, 0)).withTimeout(0.3),
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
      new InstantCommand(() -> sVision.SwitchPipeline(Constants.kLimelightStartingPipeline))
    ));

    // ***** SLALOM PATH ***** //
    // WILL NOT WORK LIKE AT ALL DO NOT RUN THIS WITHOUT CECE OR ELSE EXPECT CHAOS
    /*
    AutoButton.whenPressed(new SequentialCommandGroup(
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
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(0.7, 0, 0)).withInterrupt(() -> sVision.getTargetX() < 27 && sVision.getTargetY() > 5),
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
      new RunCommand(() -> sDrivetrain.DriveWithStrafe(0, 1, 0)).withTimeout(0.7)
      ));
      */
      

  }

  /**
   * Get Auto Command.
   * 
   * <p>Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // The Command to run in Autonomous
    return null;
  }
}
