package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Vision.LimelightVision;

/**
 * Rotate Command
 * 
 * <p>This command uses the Drivetrain and Vision subsystems to rotate the robot around a vision target.
 * 
 * @version February 6, 2021
 * @author Cece
 */
public class RotateTeleop extends CommandBase {
  //Subsystems
  private Drivetrain sDrivetrain;
  private LimelightVision sVision;
  // Speed variables
  private double mStrafeSpeed;
  private double mForwardSpeed;
  private double mMaxForwardSpeed = 0.6;
  //Multipliers
  private double mForwardMultiplier = 0.06; // 0.05
  private double mRotationMultiplier = 0.02; //0.02
  private double mStrafeMultiplier = 0.6;
  //Y Target
  private double mYTarget;

  /**
   * The Rotate Constructor
   * 
   * <p> Rotates the robot around a vision target, using a specified speed and Y Target
   * @param pDrivetrain Drivetrain subsystem
   * @param pVision Vision subsystem
   * @param pYTarget Y Value to Target
   */
  public RotateTeleop(Drivetrain pDrivetrain, LimelightVision pVision, double pYTarget) {
    sDrivetrain = pDrivetrain;
    sVision = pVision;
    mYTarget = pYTarget;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
    addRequirements(sVision);
  }

  /** Called when the command is initially scheduled. */
  @Override
  public void initialize() {}

  /** Called every time the scheduler runs while the command is scheduled. */
  @Override
  public void execute() {
    //Sets the strafe speed based on user input
    mStrafeSpeed = RobotContainer.OpStick.getRawAxis(4) * mStrafeMultiplier;

    //Sets the forward speed based on the Y position of the target
    if(mYTarget < sVision.getTargetY()){
      mForwardSpeed = (Math.abs(mYTarget - sVision.getTargetY())) * mForwardMultiplier;
    }else if(mYTarget > sVision.getTargetY()){
      mForwardSpeed = -(Math.abs(mYTarget - sVision.getTargetY())) * mForwardMultiplier;
    }else{
      mForwardSpeed = 0;
    }
    //Checks to make sure the speed is less than the max speed
    if(mForwardSpeed > mMaxForwardSpeed){
      mForwardSpeed = mMaxForwardSpeed;
    } else if (mForwardSpeed < -mMaxForwardSpeed){
      mForwardSpeed = -mMaxForwardSpeed;
    }

    //Updates the speed of the Drivetrain motors
    if(Math.abs(mStrafeSpeed) > 0.3){
      sDrivetrain.DriveWithStrafe(mStrafeSpeed, mForwardSpeed, sVision.getTargetX() * mRotationMultiplier);
    } else {
      sDrivetrain.StopDrivetrain();
    }
    
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) {}

  /** Returns true when the command should end. */
  @Override
  public boolean isFinished() {
    return false;
  }
}