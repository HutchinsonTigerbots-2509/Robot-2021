package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


/**
 * Strafe Straight Command
 * 
 * <p>This commmand uses the Drivetrain and Gyro to strafe while maintaining rotation
 * 
 * @version February 9, 2021
 * @author Cece
 */
public class StrafeStraight extends CommandBase {
  private Drivetrain sDrivetrain;
  private double mYSpeed;
  private double mRotationMultiplier = 0.06;
  private Double mGyroTarget;

  /** 
   * Creates a new StrafeStraight.
   * <p> Strafes while correcting for rotation using the current gyro angle
   * @param pDrivetrain Drivetrain subsystem
   * @param pYSpeed strafe speed
   */
  public StrafeStraight(Drivetrain pDrivetrain, double pYSpeed) {
    sDrivetrain = pDrivetrain;
    mYSpeed = pYSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
  }

  public StrafeStraight(Drivetrain pDrivetrain, double pYSpeed, double pGyroTarget){
    sDrivetrain = pDrivetrain;
    mYSpeed = pYSpeed;
    mGyroTarget = pGyroTarget;
    // Use addRequirements() here to delcare subsystem dependencies
    addRequirements(sDrivetrain);
  }

  public void CustomDriveCartesian(double YValue, double XValue, double ZValue){
    Vector2d input = new Vector2d(YValue, XValue);
    input.rotate(0);

    double[] wheelSpeeds = new double[4];
    wheelSpeeds[0] = input.x + input.y + ZValue;
    wheelSpeeds[1] = -input.x + input.y - ZValue;
    wheelSpeeds[2] = -input.x + input.y + ZValue;
    wheelSpeeds[3] = input.x + input.y - ZValue;

    sDrivetrain.StrafeSpeeds(-wheelSpeeds[1], wheelSpeeds[0], -wheelSpeeds[3], wheelSpeeds[2]);
  }

  /** Called when the command is initially scheduled. */
  @Override
  public void initialize() {
    if(mGyroTarget == null){
      mGyroTarget = sDrivetrain.GetGyroAngle();
    }
  }

  /** Called every time the scheduler runs while the command is scheduled. */
  @Override
  public void execute() {
    CustomDriveCartesian(mYSpeed, 0, -(sDrivetrain.GetGyroAngle() - mGyroTarget) * mRotationMultiplier);
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) {
    sDrivetrain.StopDrivetrain();
  }

  /** Returns true when the command should end. */
  @Override
  public boolean isFinished() {
    return false;
  }


}
