package frc.robot.commands.Shooter;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter.Shooter;

public class RampUpShooterWithProfile extends CommandBase {

  private Shooter sShooter;

  private LinearRampProfile LRP;

  private double mRampTime;
  private double mCurrentTime;
  private double mTargetVoltage;

  /**
   * Creates a new RampUpShooter.
   * 
   * @param pShooter       shooter subsystem
   * @param pTargetVoltage target voltage
   * @param pRampTime      time taken to ramp up.
   */
  public RampUpShooterWithProfile(Shooter pShooter, double pTargetVoltage, double pRampTime) {
    sShooter = pShooter;
    mTargetVoltage = pTargetVoltage;
    mRampTime = pRampTime;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sShooter);
  }

  /** Called when the command is initially scheduled. */
  public void initialize() {
    LRP = new LinearRampProfile(0.8, 
                                Timer.getFPGATimestamp(), 
                                mTargetVoltage, 
                                mRampTime + Timer.getFPGATimestamp());
  }
  
  /** Called every time the scheduler runs while the command is scheduled. */
  public void execute() {
    mCurrentTime = Timer.getFPGATimestamp();
    sShooter.RunShooter(LRP.getVolt(mCurrentTime));
  }

  /** Called once the command ends or is interrupted. */
  public void end(boolean interrupted) {
    sShooter.RunShooter(mTargetVoltage);
  }

  /** Returns true when the command should end. */
  public boolean isFinished() {
    return (mCurrentTime < mRampTime);

  }
}
