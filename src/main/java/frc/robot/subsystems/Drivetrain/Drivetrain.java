package frc.robot.subsystems.Drivetrain;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
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

  private DrivetrainMode CurrentMode = DrivetrainMode.FULL;

  private boolean enableRonDriveModified = true;


  public Drivetrain() {
  
    mFrontLeft = new WPI_TalonFX(Constants.kFrontLeftID);
    mFrontRight = new WPI_TalonFX(Constants.kFrontRightID);
    mRearLeft = new WPI_TalonFX(Constants.kRearLeftID);
    mRearRight = new WPI_TalonFX(Constants.kRearRightID);

    mDrive = new MecanumDrive(mFrontLeft, mRearLeft, mFrontRight, mRearRight);
  }

  /**Periodic function */
  public void periodic() {

    switch(CurrentMode) {
      case CREEP:
        CreepDrive(RobotContainer.OpStick);
        break;
      default:
        if (enableRonDriveModified) {
          ModifiedRonDrive(RobotContainer.OpStick);
        } else {
          RonDrive(RobotContainer.OpStick);
        }
        break;
    }

    SmartDashboard.putString("Drivetrain Mode", CurrentMode.toString());
  }

  // ***** TELEOP DRIVE METHODS ***** //

  private static AxisAccel forwardbackwardaxis = new AxisAccel(0.04, 0.04);
  private static AxisAccel strafeaxis = new AxisAccel(0.06, 0.06);
  private static AxisAccel turnaxis = new AxisAccel(0.04, 0.04, 0.3, 0.4);

  private void RonDrive(Joystick pStick) {
    mDrive.driveCartesian(strafeaxis.periodic(pStick.getRawAxis(4)),
                          forwardbackwardaxis.periodic(-pStick.getRawAxis(1)),
                          turnaxis.periodic(pStick.getRawAxis(0)));
  }

  private void ModifiedRonDrive(Joystick pStick) {
    if (pStick.getRawAxis(0) > 0.2) {
      mRearLeft.set(-0.2);
      mRearRight.set(-0.2);
      skip = true;
    } else if (pStick.getRawAxis(0) < -0.2) {
      mRearLeft.set(0.2);
      mRearRight.set(0.2);
      skip = true;
    } else {
      mDrive.driveCartesian(strafeaxis.periodic(pStick.getRawAxis(4)),
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

  /** Initializes the drivetrain motors. Called in Robot */
  public void InitializeDrivetrain(){
    // This means when the motors are set to 0, the motors will
    // automatically stop. NeutralMode.Coast will cause the
    // motors to continue spinning.
    mFrontRight.setNeutralMode(NeutralMode.Brake);
    mRearRight.setNeutralMode(NeutralMode.Brake);
    mFrontLeft.setNeutralMode(NeutralMode.Brake);
    mRearLeft.setNeutralMode(NeutralMode.Brake);

    // Sets the motors to flip inputs (forwards for the motors is backwards
    // for the program now)
    mFrontRight.setInverted(true);
    mRearRight.setInverted(true);
    mFrontLeft.setInverted(true);
    mRearLeft.setInverted(true);
  }

}
