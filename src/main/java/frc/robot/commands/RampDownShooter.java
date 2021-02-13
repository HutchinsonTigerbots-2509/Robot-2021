// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.opencv.features2d.MSER;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RampDownShooter extends CommandBase {
 private Shooter sShooter;
 private double mRampTime;
 private double mCurrentTime;
 private double mStartTime;
 private double mVoltage;
 private double mStartingVoltage;
 private boolean mFinished;

  /** Creates a new RampDownShooter. */
  public RampDownShooter(Shooter pShooter, double pRampTime) {
    sShooter = pShooter;
    mRampTime = pRampTime;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sShooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mStartingVoltage = sShooter.GetMotorOutputPercent();
    mStartTime = Timer.getFPGATimestamp();
    mVoltage = mStartingVoltage;
    mCurrentTime = mStartTime;
    mFinished = false;

    SmartDashboard.putNumber("Ramp Start V", mStartingVoltage);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (mVoltage > 0){
      mVoltage = -(mStartingVoltage / mRampTime) * (mCurrentTime - mStartTime) + mStartingVoltage;
      sShooter.RunShooter(mVoltage);
    }else{
      mVoltage = 0;
      sShooter.RunShooter(mVoltage);
      mFinished = true;
    }

    SmartDashboard.putNumber("Shooter Voltage", mVoltage);
    mCurrentTime = Timer.getFPGATimestamp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sShooter.RunShooter(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return mFinished;
  }
}
