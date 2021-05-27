package frc.robot.subsystems.Drivetrain;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Drivetrain extends SubsystemBase {

  private WPI_TalonFX mFrontLeft;
  private WPI_TalonFX mFrontRight;
  private WPI_TalonFX mRearLeft;
  private WPI_TalonFX mRearRight;

  public MecanumDrive mDrive;

  private AHRS mGyro = new AHRS();

  private DrivetrainMode CurrentMode = DrivetrainMode.FULL;

  private boolean enableRonDriveModified = true;
  private boolean fieldOrientateWithRotation = false;


  public Drivetrain() {
  
    mFrontLeft = new WPI_TalonFX(Constants.kFrontLeftID);
    mFrontRight = new WPI_TalonFX(Constants.kFrontRightID);
    mRearLeft = new WPI_TalonFX(Constants.kRearLeftID);
    mRearRight = new WPI_TalonFX(Constants.kRearRightID);

    mDrive = new MecanumDrive(mFrontLeft, mRearLeft, mFrontRight, mRearRight);

    ResetGyro();
    ResetEncoders();
  }

  /**Periodic function */
  public void periodic() {

    switch(CurrentMode) {
      case CREEP:
        CreepDrive(RobotContainer.OpStick);
        break;
      default:
        ModifiedRonDrive(RobotContainer.OpStick);
        break;

        // Uncomment these for feild orientated drive
        // if (enableRonDriveModified) {
        //   ModifiedRonDrive(RobotContainer.OpStick);
        //   // RonDrive(RobotContainer.OpStick);
        // } else {
        //   if(fieldOrientateWithRotation){
        //     FieldOrientedDriveRotation(RobotContainer.OpStick);
        //   } else {
        //     FieldOrientedDrive(RobotContainer.OpStick);
        //   }
        // }
        // break;
    }

    // SmartDashboard.putString("Drivetrain Mode", CurrentMode.toString());
    SmartDashboard.putNumber("Gyro", GetGyroAngle());
    SmartDashboard.putNumber("DT FrontLeft", mFrontLeft.getMotorOutputVoltage());
    SmartDashboard.putNumber("DT FrontRight", mFrontRight.getMotorOutputVoltage());
    SmartDashboard.putNumber("DT RearLeft", mRearLeft.getMotorOutputVoltage());
    SmartDashboard.putNumber("DT RearRight", mRearRight.getMotorOutputVoltage());
  }

  // ***** TELEOP DRIVE METHODS ***** //

  private static AxisAccel forwardbackwardaxis = new AxisAccel(0.04, 0.04);
  private static AxisAccel strafeaxis = new AxisAccel(0.06, 0.06);
  private static AxisAccel turnaxis = new AxisAccel(0.04, 0.04, 0.3, 0.4); // 1

  private void RonDrive(Joystick pStick) {
    mDrive.driveCartesian(strafeaxis.periodic(-pStick.getRawAxis(2)),
                          forwardbackwardaxis.periodic(-pStick.getRawAxis(1)),
                          turnaxis.periodic(pStick.getRawAxis(0)));
  }

  private void ModifiedRonDrive(Joystick pStick) {
    if (pStick.getRawAxis(0) > 0.2) {
      mRearLeft.set(-1);
      mRearRight.set(-1);
      skip = true;
    } else if (pStick.getRawAxis(0) < -0.2) {
      mRearLeft.set(1);
      mRearRight.set(1);
      skip = true;
    } else {
      mDrive.driveCartesian(strafeaxis.periodic(pStick.getRawAxis(2)),
                          forwardbackwardaxis.periodic(-pStick.getRawAxis(1)),
                          0);
    }
  }

  private static double StrafeInput;
  private static double ForwardInput;
  private static boolean skip = false;

  private void CreepDrive(Joystick pStick) {
    if (pStick.getRawAxis(0) > 0.2) {
      mRearLeft.set(-0.2);
      mRearRight.set(-0.2);
      skip = true;
    } else if (pStick.getRawAxis(0) < -0.2) {
      mRearLeft.set(0.2);
      mRearRight.set(0.2);
      skip = true;
    } else {
      mRearLeft.set(0);
      mRearRight.set(0);
      skip = false;
    }

    if (pStick.getRawAxis(4) > 0.2 && !skip) {
      StrafeInput = 0.2;
    } else if (pStick.getRawAxis(4) < -0.2 && !skip) {
      StrafeInput = -0.2;
    } else {
      StrafeInput = 0;
    }

    // Needs to be reversed
    if (pStick.getRawAxis(1) > 0.2 && !skip) {
      ForwardInput = -0.2;
    } else if (pStick.getRawAxis(1) < -0.2 && !skip) {
      ForwardInput = 0.2;
    } else {
      ForwardInput = 0;
    }

    if (!skip) {
      mDrive.driveCartesian(StrafeInput, ForwardInput, 0);
    } else {
      // mDrive.driveCartesian(0, 0, 0);
      mFrontLeft.set(0);
      mFrontRight.set(0);
    }
  }

  public void SwitchMode() {
    if (CurrentMode == DrivetrainMode.CREEP) {
      CurrentMode = DrivetrainMode.FULL;
    } else {
      CurrentMode = DrivetrainMode.CREEP;
    }
  }

  // ***** AUTONOMOUS DRIVE METHODS ***** //

  /**
   * Drives without strafe (arcadeDrive, essentially)
   * @param pXSpeed forward speed
   * @param pZSpeed rotational speed
   */
  public void DriveWithoutStrafe(double pXSpeed, double pZSpeed) {
    mDrive.driveCartesian(0, pXSpeed, pZSpeed);
  }

  /**
   * Drives with strafe (cartesianDrive)
   * @param pYSpeed strafe speed
   * @param pXSpeed forward speed
   * @param pZSpeed rotational speed
   */
  public void DriveWithStrafe(double pYSpeed, double pXSpeed, double pZSpeed) {
    mDrive.driveCartesian(pYSpeed, pXSpeed, pZSpeed);
  }

  /** Stops the robot */
  public void StopDrivetrain() {
    mDrive.driveCartesian(0, 0, 0);
  }

  // ***** ENCODER METHODS ***** //

  /** Zeroes the encoders */
  public void ResetEncoders(){
    mRearRight.setSelectedSensorPosition(0);
    mRearLeft.setSelectedSensorPosition(0);
    mFrontRight.setSelectedSensorPosition(0);
    mFrontLeft.setSelectedSensorPosition(0);
  }

  /** Gets the average encoder count from 3 of the drivetrain motors */
  public double EncoderAverage(){
    return (Math.abs(mRearRight.getSelectedSensorPosition()) + 
            Math.abs(mRearLeft.getSelectedSensorPosition()) + 
            // Math.abs(mFrontRight.getSelectedSensorPosition()) + 
            Math.abs(mFrontLeft.getSelectedSensorPosition())) / 3;
  }

  // ***** GYRO METHODS ***** //

  /**
   * Gets gyro angle
   * @return The gyro angle
   */
  public double GetGyroAngle() {
    return mGyro.getAngle();
  }

  /** Zeros the gyro */
  public void ResetGyro() {
    mGyro.reset();
    mGyro.resetDisplacement();
  }

  /** Initializes the drivetrain motors. Called in Robot */
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
  
  public void StrafeSpeeds(double RightFrontSpeed, double LeftFrontSpeed, double RightRearSpeed, double LeftRearSpeed){
    mFrontLeft.set(LeftFrontSpeed);
    mFrontRight.set(RightFrontSpeed);
    mRearLeft.set(LeftRearSpeed);
    mRearRight.set(RightRearSpeed);
  }

  private double forward = 0;
  private double strafe = 0;
  private double theta = 0;
  private double YSpeed = 0;
  private double XSpeed = 0;
  private double ZSpeed = 0;
  private double ZMultiplier;
  private double ZMultiplierSmall = -0.025;
  private double ZMultiplierBig = -0.05;
  private double GyroTarget = GetGyroAngle();

  public void FieldOrientedDrive(Joystick stick){
    forward = stick.getRawAxis(1);
    strafe = stick.getRawAxis(0);
    theta = Math.toRadians(GetGyroAngle());

    XSpeed = -((forward * Math.cos(theta)) - (strafe * Math.sin(theta)));
    YSpeed = ((forward * Math.sin(theta)) - (strafe * -Math.cos(theta)));

    SmartDashboard.putNumber("Y", YSpeed);
    SmartDashboard.putNumber("X", XSpeed);

    mDrive.driveCartesian(
      strafeaxis.periodic(YSpeed), 
      forwardbackwardaxis.periodic(XSpeed),
      turnaxis.periodic(stick.getRawAxis(4)));
  }


  private void FieldOrientedDriveRotation(Joystick stick){
    forward = stick.getRawAxis(1);
    strafe = stick.getRawAxis(0);
    theta = Math.toRadians(GetGyroAngle());

    XSpeed = -((forward * Math.cos(theta)) - (strafe * Math.sin(theta)));
    YSpeed = ((forward * Math.sin(theta)) - (strafe * -Math.cos(theta)));
    ZSpeed = turnaxis.periodic(stick.getRawAxis(4));
    
    if(Math.abs(ZSpeed) > 0.05){
      GyroTarget = GetGyroAngle();
    } else if(Math.abs(YSpeed) + Math.abs(XSpeed) > 0.3){
      if(Math.abs(GetGyroAngle() - GyroTarget) > 10){
        ZMultiplier = ZMultiplierBig;
      } else {
        ZMultiplier = ZMultiplierSmall;
      }
      ZSpeed = ZMultiplier * (GetGyroAngle() - GyroTarget);
    } else {
      ZSpeed = 0;
    } 


    SmartDashboard.putNumber("Y", YSpeed);
    SmartDashboard.putNumber("X", XSpeed);
    SmartDashboard.putNumber("Z", ZSpeed);
    SmartDashboard.putNumber("Z targ", GyroTarget);

    mDrive.driveCartesian(
      strafeaxis.periodic(YSpeed), 
      forwardbackwardaxis.periodic(XSpeed),
      ZSpeed);
  }
}
