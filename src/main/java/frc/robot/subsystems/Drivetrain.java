// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Drivetrain Subsystem.
 * 
 * <p>The Drivetrain Subsystem is reponsible for the Drivetrain motors and the gyro. Drive methods and
 * getter methods for the gyro values are located here.
 * 
 * @version February 6, 2021
 * @author Cece
 */
public class Drivetrain extends SubsystemBase {
  //Motors
  private WPI_TalonFX FrontLeft = new WPI_TalonFX(Constants.kFrontLeftID);
  private WPI_TalonFX FrontRight = new WPI_TalonFX(Constants.kFrontRightID);
  private WPI_TalonFX RearLeft = new WPI_TalonFX(Constants.kRearLeftID);
  private WPI_TalonFX RearRight = new WPI_TalonFX(Constants.kRearRightID);

  private MecanumDrive drive = new MecanumDrive(FrontLeft, RearLeft, FrontRight, RearRight);

  //Gyro
  private AHRS gyro = new AHRS();

  //Speed Variables
  private double XSpeed = 0;
  private double YSpeed = 0;
  private double ZSpeed = 0;
  //Multipliers
  private double XMultiplier = 1; //0.6
  private double YMultiplier = 1; //0.8
  private double ZMultiplier = 1; //0.6

  /**
   * Drivetrain Constructor.
   * 
   * <p>The Drivetrain of the robots. Contains Drivetrain motors and gyro.
   */
  public Drivetrain() {
    // Sets whether or not the motors are inverted
    FrontRight.setInverted(true);
    RearRight.setInverted(true);
    FrontLeft.setInverted(true);
    RearLeft.setInverted(true);

    // Sets the Neutral Mode of the motors (what the motors do when their recieved voltage is 0)
    FrontRight.setNeutralMode(NeutralMode.Brake);
    RearRight.setNeutralMode(NeutralMode.Brake);
    FrontLeft.setNeutralMode(NeutralMode.Brake);
    RearLeft.setNeutralMode(NeutralMode.Brake);

    // Zeros the gyro
    ResetGyro();
  }

  @Override
  public void periodic() {
    //Drives the robot using a joystick
    JoystickDrive(RobotContainer.OpStick);
    //Prints the gyro angle to the SmartDashboard
    SmartDashboard.putNumber("Gyro Angle", GetGyroAngle());
  }

  // ***** TELEOP DRIVE METHODS ***** //

  //Drives without ramp using the Joystick
  private void JoystickDrive(Joystick stick){
    //Calculates the Y Speed based on the joystick values
    if(stick.getRawAxis(Constants.kXboxRightTrigger) > 0.4){
      YSpeed = stick.getRawAxis(Constants.kXboxRightTrigger) * YMultiplier;
    } else if (stick.getRawAxis(Constants.kXboxLeftTrigger) > 0.4){
      YSpeed = -stick.getRawAxis(Constants.kXboxLeftTrigger) * YMultiplier;
    } else {
      YSpeed = 0;
    }
    //Calculates the X Speed based on the joystick values;
    if(Math.abs(stick.getRawAxis(Constants.kXboxLeftJoystickY)) > 0.4){
      XSpeed = -stick.getRawAxis(Constants.kXboxLeftJoystickY) * XMultiplier;
    } else {
      XSpeed = 0;
    }
    //Calculates the Z Speed based on the joystick values
    if(Math.abs(stick.getRawAxis(Constants.kXboxRightJoystickX)) > 0.4){
      ZSpeed = stick.getRawAxis(Constants.kXboxRightJoystickX) * ZMultiplier;
    } else {
      ZSpeed = 0;
    }
    //Sets the motors to the speed
    drive.driveCartesian(YSpeed, XSpeed, ZSpeed);
  }

  // ***** AUTONOMOUS DRIVE METHODS ***** //

  //Drives without strafe (arcadeDrive, essentially)
  public void DriveWithoutStrafe(double XSpeed, double ZSpeed){
    drive.driveCartesian(0, XSpeed, ZSpeed);
  }

  //Drives with strafe (cartesianDrive)
  public void DriveWithStrafe(double YSpeed, double XSpeed, double ZSpeed){
    drive.driveCartesian(YSpeed, XSpeed, ZSpeed);
  }

  //Stops the robot
  public void StopDrivetrain(){
    drive.driveCartesian(0, 0, 0);
  }

  // ***** GYRO METHODS ***** //

  // Returns the X displacement (distance) of the gyro
  public double GetGyroX(){
    return gyro.getDisplacementX();
  }

  // Returns the Y displacement (distance) of the gyro
  public double GetGyroY(){
    return gyro.getDisplacementY();
  }

  // Returns the angle (rotation) of the gyro
  public double GetGyroAngle(){
    return gyro.getAngle();
  }

  // Zeros the gyro
  public void ResetGyro(){
    gyro.reset();
    gyro.resetDisplacement();
  }
}
