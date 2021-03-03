package frc.robot.commands.Shooter;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter.Shooter;

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
  public void initialize() {
    mStartTime = Timer.getFPGATimestamp();
    mCurrentTime = mStartTime;
    mVoltage = 0;
  }
  
  /** Called every time the scheduler runs while the command is scheduled. */
  public void execute() {
    if(mVoltage < mTargetVoltage){
      mVoltage = (mTargetVoltage / mRampTime) * (mCurrentTime - mStartTime);
      sShooter.RunShooter(mVoltage);
    } else{
      mVoltage = mTargetVoltage;
      sShooter.RunShooter(mVoltage);
    }
    

    SmartDashboard.putNumber("Shooter Voltage", mVoltage);
    mCurrentTime = Timer.getFPGATimestamp();
  }

  /** Called once the command ends or is interrupted. */
  public void end(boolean interrupted) {
    
  }

  /** Returns true when the command should end. */
  public boolean isFinished() {
    return false;
  }
}
