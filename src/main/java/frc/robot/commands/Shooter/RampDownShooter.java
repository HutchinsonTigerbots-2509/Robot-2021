package frc.robot.commands.Shooter;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RampDownShooter extends CommandBase {

 private Shooter sShooter;
 
 private double mRampTime;
 private double mCurrentTime;
 private double mStartTime;
 
 private double mVoltage;
 private double mStartingVoltage;
 private boolean mFinished;

  public RampDownShooter(Shooter pShooter, double pRampTime) {
    sShooter = pShooter;
    mRampTime = pRampTime;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sShooter);
  }

  /**
   * 
   */
  public void initialize() {
    
    mStartingVoltage = sShooter.GetMotorOutputPercent();
    mStartTime = Timer.getFPGATimestamp();
    
    mVoltage = mStartingVoltage;
    mCurrentTime = mStartTime;

    mFinished = false;

  }

  /**
   * 
   */
  public void execute() {
    if (mVoltage > 0) {
      mVoltage = -(mStartingVoltage / mRampTime) * (mCurrentTime - mStartTime) + mStartingVoltage;
      sShooter.RunShooter(mVoltage);
    } else {
      sShooter.RunShooter(0);
      mFinished = true;
    }

    SmartDashboard.putNumber("Shooter Voltage", mVoltage);
    mCurrentTime = Timer.getFPGATimestamp();
  }

  public void end(boolean interrupted) {
    sShooter.RunShooter(0);
  }

  public boolean isFinished() {
    return mFinished;
  }
}
