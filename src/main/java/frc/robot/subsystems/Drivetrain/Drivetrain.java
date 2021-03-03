package frc.robot.subsystems.Drivetrain;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController.Axis;
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
    
    // Drives the robot using a joystick
    RonDrive(RobotContainer.OpStick);
    
    // Prints the gyro values to the SmartDashboard
    SmartDashboard.putNumber("Gyro Angle", GetGyroAngle());
    
    // Prints the encoder values to the SmartDashboard
    // SmartDashboard.putNumber("LeftFront", mFrontLeft.getSelectedSensorPosition());
    // SmartDashboard.putNumber("LeftRear", mRearLeft.getSelectedSensorPosition());
    // SmartDashboard.putNumber("RightFront", mFrontRight.getSelectedSensorPosition());
    // SmartDashboard.putNumber("RightRear", mRearRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("Average Encoder Ticks", EncoderAverage());
  
  }

  // ***** TELEOP DRIVE METHODS ***** //

  private static AxisAccel forwardbackwardaxis = new AxisAccel(0.04, 0.04);
  private static AxisAccel strafeaxis = new AxisAccel(0.06, 0.06);
  private static AxisAccel turnaxis = new AxisAccel(0.04, 0.04, 0.3, 0.7);

  private void RonDrive(Joystick pStick) {
    mDrive.driveCartesian(strafeaxis.periodic(pStick.getRawAxis(1)),
                          forwardbackwardaxis.periodic(pStick.getRawAxis(4)),
                          turnaxis.periodic(pStick.getRawAxis(0)));
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
}
