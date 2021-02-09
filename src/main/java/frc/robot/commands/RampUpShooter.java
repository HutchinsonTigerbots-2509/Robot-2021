// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

/**
 * RampUpShooter Command
 * 
 * <p>This command uses the Shooter subsystem to ramp the shooter up to the target Voltage.
 * 
 * @version February 9, 2021
 * @author Noah Sturges
 * @author Quinton MacMullan
 */
public class RampUpShooter extends CommandBase {
  private Shooter sShooter;

  private double mStartTime;
  private double mRampTime;
  private double mCurrentTime;
  private double mVoltage;
  private double mTargetVoltage;

  /** Creates a new RampUpShooter. 
   * @param pShooter shooter subsystem
   * @param pTargetVoltage target voltage
   * @param pRampTime time taken to ramp up.
  */
  public RampUpShooter(Shooter pShooter, double pTargetVoltage, double pRampTime) {
    sShooter = pShooter;
    mTargetVoltage = pTargetVoltage;
    mRampTime = pRampTime;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sShooter);
  }

  /** Called when the command is initially scheduled. */
  @Override
  public void initialize() {
    mStartTime = Timer.getFPGATimestamp();
    mCurrentTime = mStartTime;
    mVoltage = 0;
  }
  
  /** Called every time the scheduler runs while the command is scheduled. */
  @Override
  public void execute() {
    if(mVoltage <= mTargetVoltage){
      mVoltage = (mTargetVoltage / mRampTime) * (mCurrentTime - mStartTime);
      sShooter.RunShooter(mVoltage);
    } else{
      sShooter.RunShooter(mTargetVoltage);
    }
    

    SmartDashboard.putNumber("Shooter Voltage", mVoltage);
    mCurrentTime = Timer.getFPGATimestamp();
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) {
    
  }

  /** Returns true when the command should end. */
  @Override
  public boolean isFinished() {
    return false;
  }
}
