package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter.Shooter;

public class RampDownShooterWithProfile extends CommandBase {

  private Shooter sShooter;

  private LinearRampProfile LRP;

  private double mRampTime = 0.1;
  private double mCurrentTime = 0;


  public RampDownShooterWithProfile(Shooter pShooter, double pRampTime) {
    sShooter = pShooter;
    mRampTime = pRampTime;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sShooter);
  }

  public void initialize() {    
    LRP = new LinearRampProfile(sShooter.GetMotorOutputPercent(), 
                                Timer.getFPGATimestamp(), 
                                0, 
                                mRampTime + Timer.getFPGATimestamp());
  }

  public void execute() {
    mCurrentTime = Timer.getFPGATimestamp();
    sShooter.RunShooter(LRP.getVolt(mCurrentTime));
  }

  public void end(boolean interrupted) {
    sShooter.RunShooter(0);
  }

  public boolean isFinished() {
    return (mCurrentTime < mRampTime);
  }
}
