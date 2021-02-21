// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
 * <p>
 * The Drivetrain Subsystem is reponsible for the Drivetrain motors and the
 * gyro. Drive methods and getter methods for the gyro values are located here.
 * 
 * @version February 9, 2021
 * @author Cece
 * @author Grace
 */
public class Drivetrain extends SubsystemBase {
  // Motors
  private WPI_TalonFX mFrontLeft;
  private WPI_TalonFX mFrontRight;
  private WPI_TalonFX mRearLeft;
  private WPI_TalonFX mRearRight;

  private MecanumDrive mDrive;

  // Gyro
  private AHRS mGyro = new AHRS();

  // Speed Variables
  private double mXSpeed = 0;
  private double mYSpeed = 0;
  private double mZSpeed = 0;
  //Multipliers
  private double mXMultiplier = 1; //0.6
  private double mYMultiplier = 0.8; //0.8
  private double mZMultiplier = 1; //0.6
  //Previous Values
  private double mPreviousXSpeed = 0;
  private double mPreviousZSpeed = 0;
  //Ramp Down Values
  private double mRampDownX = 0.08;
  private double mRampDownZ = 0.08;

  /**
   * Drivetrain Constructor.
   * 
   * <p>
   * The Drivetrain of the robots. Contains Drivetrain motors and gyro.
   */
  public Drivetrain() {
    //Defines the Drivetrain motors
    mFrontLeft = new WPI_TalonFX(Constants.kFrontLeftID);
    mFrontRight = new WPI_TalonFX(Constants.kFrontRightID);
    mRearLeft = new WPI_TalonFX(Constants.kRearLeftID);
    mRearRight = new WPI_TalonFX(Constants.kRearRightID);

    //Defines the mecanumDrive
    mDrive = new MecanumDrive(mFrontLeft, mRearLeft, mFrontRight, mRearRight);

    // Zeros the gyro & encoders
    ResetGyro();
    ResetEncoders();
  }

  /**Periodic function */
  @Override
  public void periodic() {
    // Drives the robot using a joystick
    JoystickDrive(RobotContainer.OpStick);
    // Prints the gyro values to the SmartDashboard
    SmartDashboard.putNumber("Gyro Angle", GetGyroAngle());
    SmartDashboard.putNumber("Gyro X", GetGyroX());
    SmartDashboard.putNumber("Gyro Y", GetGyroY());
    // Prints the encoder values to the SmartDashboard
    SmartDashboard.putNumber("LeftFront", mFrontLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("LeftRear", mRearLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("RightFront", mFrontRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("RightRear", mRearRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("Average Encoder Ticks", EncoderAverage());
  }

  // ***** TELEOP DRIVE METHODS ***** //

  /**
   * Drives the robot without ramp using the Joystick.
   * @param pStick The Joystick to get inputs from
   */
  private void JoystickDrive(Joystick pStick) {
    // Calculates the Y Speed based on the joystick values
    if (pStick.getRawAxis(Constants.kXboxRightTrigger) > 0.4) {
      mYSpeed = pStick.getRawAxis(Constants.kXboxRightTrigger) * mYMultiplier;
    } else if (pStick.getRawAxis(Constants.kXboxLeftTrigger) > 0.4) {
      mYSpeed = -pStick.getRawAxis(Constants.kXboxLeftTrigger) * mYMultiplier;
    } else {
      mYSpeed = 0;
    }
    // Calculates the X Speed based on the joystick values;
    if (Math.abs(pStick.getRawAxis(Constants.kXboxLeftJoystickY)) > 0.4) {
      mXSpeed = -pStick.getRawAxis(Constants.kXboxLeftJoystickY) * mXMultiplier;
    } else {
      mXSpeed = 0;
    }
    // Calculates the Z Speed based on the joystick values
    if (Math.abs(pStick.getRawAxis(Constants.kXboxRightJoystickX)) > 0.4) {
      mZSpeed = pStick.getRawAxis(Constants.kXboxRightJoystickX) * mZMultiplier;
    } else {
      mZSpeed = 0;
    }
    //Sets the motors to the speed
    mDrive.driveCartesian(mYSpeed, mXSpeed, mZSpeed);
  }

  private void JoystickDriveRamp(Joystick pStick){
    // Calculates the Y Speed based on the joystick values
    if (pStick.getRawAxis(Constants.kXboxRightTrigger) > 0.4) {
      mYSpeed = pStick.getRawAxis(Constants.kXboxRightTrigger) * mYMultiplier;
    } else if (pStick.getRawAxis(Constants.kXboxLeftTrigger) > 0.4) {
      mYSpeed = -pStick.getRawAxis(Constants.kXboxLeftTrigger) * mYMultiplier;
    } else {
      mYSpeed = 0;
    }

    // Calculates the X Target Speed based on the joystick values;
    if (Math.abs(pStick.getRawAxis(Constants.kXboxLeftJoystickY)) > 0.4) {
      mXSpeed = -pStick.getRawAxis(Constants.kXboxLeftJoystickY) * mXMultiplier;
    } else {
      mXSpeed = 0;
    }
    // Calculates the Z Target Speed based on the joystick values
    if (Math.abs(pStick.getRawAxis(Constants.kXboxRightJoystickX)) > 0.4) {
      mZSpeed = pStick.getRawAxis(Constants.kXboxRightJoystickX) * mZMultiplier;
    } else {
      mZSpeed = 0;
    }

    if(Math.abs(mXSpeed) < Math.abs(mPreviousXSpeed)){
      mXSpeed = mPreviousXSpeed - mRampDownX * Normalize(mPreviousXSpeed);
    }
    if(Math.abs(mZSpeed ) < Math.abs(mPreviousZSpeed)){
      mZSpeed = mPreviousZSpeed - mRampDownZ * Normalize(mPreviousZSpeed);
    }

    if(Math.abs(mXSpeed) < 0.4){
      mXSpeed = 0;
    }
    if(Math.abs(mZSpeed) < 0.4){
      mZSpeed = 0;
    }

    mDrive.driveCartesian(mYSpeed, mXSpeed, mZSpeed);

    mPreviousXSpeed = mXSpeed;
    mPreviousZSpeed = mZSpeed;

    SmartDashboard.putNumber("Z Speed", mZSpeed);
    SmartDashboard.putNumber("X Speed", mXSpeed);

  }

  // ***** AUTONOMOUS DRIVE METHODS ***** //

  /**
   * Drives without strafe (arcadeDrive, essentially).
   * @param pXSpeed forward speed
   * @param pZSpeed rotational speed
   */
  public void DriveWithoutStrafe(double pXSpeed, double pZSpeed) {
    mDrive.driveCartesian(0, pXSpeed, pZSpeed);
  }

  /**
   * Drives with strafe (cartesianDrive).
   * @param pYSpeed strafe speed
   * @param pXSpeed forward speed
   * @param pZSpeed rotational speed
   */
  public void DriveWithStrafe(double pYSpeed, double pXSpeed, double pZSpeed) {
    mDrive.driveCartesian(pYSpeed, pXSpeed, pZSpeed);
  }

  /** Stops the robot. */
  public void StopDrivetrain() {
    mDrive.driveCartesian(0, 0, 0);
  }

  // ***** ENCODER METHODS ***** //

  /** Resets encoder values. */
  public void ResetEncoders(){
    mRearRight.setSelectedSensorPosition(0);
    mRearLeft.setSelectedSensorPosition(0);
    mFrontRight.setSelectedSensorPosition(0);
    mFrontLeft.setSelectedSensorPosition(0);
  }

  /** 
   * Gets the encoders average.
   * @return average of encoders
   */
  public double EncoderAverage(){
    return (Math.abs(mRearRight.getSelectedSensorPosition()) + 
    Math.abs(mRearLeft.getSelectedSensorPosition()) + 
    // Math.abs(mFrontRight.getSelectedSensorPosition()) + 
    Math.abs(mFrontLeft.getSelectedSensorPosition())) / 3;
  }

  // ***** GYRO METHODS ***** //

  /**
   * Gets gyro X displacement.
   * @return The X displacement (distance) of the gyro
   */
  public double GetGyroX() {
    return mGyro.getDisplacementX();
  }

  /**
   * Gets gyro Y displacement.
   * @return The Y displacement (distance) of the gyro
   */
  public double GetGyroY() {
    return mGyro.getDisplacementY();
  }

  /**
   * Gets gyro angle.
   * @return The gyro angle
   */
  public double GetGyroAngle() {
    return mGyro.getAngle();
  }

  /** Zeros the gyro. */
  public void ResetGyro() {
    mGyro.reset();
    mGyro.resetDisplacement();
  }

  /** Initializes the drivetrain. */
  public void InitializeDrivetrain(){
    // Sets the Neutral Mode of the motors (what the motors do when their recieved
    // voltage is 0)
    mFrontRight.setNeutralMode(NeutralMode.Brake);
    mRearRight.setNeutralMode(NeutralMode.Brake);
    mFrontLeft.setNeutralMode(NeutralMode.Brake);
    mRearLeft.setNeutralMode(NeutralMode.Brake);

    // Sets whether or not the motors are inverted
    mFrontRight.setInverted(true);
    mRearRight.setInverted(true);
    mFrontLeft.setInverted(true);
    mRearLeft.setInverted(true);
  }

  /**
   * Converts a double into 1 or -1 depending on if it is positive or negative.
   */
  private int Normalize(double pNum){
    if(pNum < 0){
      return -1;
    } else if (pNum > 0){
      return 1;
    } else {
      return 0;
    }
  }
}
