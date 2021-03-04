package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain.AxisAccel;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Drivetrain.AxisAccel;


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
  private double mRotationMultiplier = 0.02; //0.06
  private Double mGyroTarget;
  private AxisAccel strafeAxis = new AxisAccel(0.06, 0.06);
  private boolean mAccel;

  /** 
   * Creates a new StrafeStraight.
   * <p> Strafes while correcting for rotation using the current gyro angle
   * @param pDrivetrain Drivetrain subsystem
   * @param pYSpeed strafe speed
   * @param pAccel Whether or not to accelerate into the move
   */
  public StrafeStraight(Drivetrain pDrivetrain, double pYSpeed, boolean pAccel) {
    sDrivetrain = pDrivetrain;
    mYSpeed = pYSpeed;
    mAccel = pAccel;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
  }

  /** 
   * Creates a new StrafeStraight.
   * <p> Strafes while correcting for rotation using the current gyro angle
   * @param pDrivetrain Drivetrain subsystem
   * @param pYSpeed strafe speed
   * @param pGyroTarget the gyro angle to target
   * @param pAccel Whether or not to accelerate into the move
   */
  public StrafeStraight(Drivetrain pDrivetrain, double pYSpeed, double pGyroTarget, boolean pAccel){
    sDrivetrain = pDrivetrain;
    mYSpeed = pYSpeed;
    mGyroTarget = pGyroTarget;
    mAccel = pAccel;
    // Use addRequirements() here to delcare subsystem dependencies
    addRequirements(sDrivetrain);
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
    if(mAccel){
      sDrivetrain.DriveWithStrafe(strafeAxis.periodic(mYSpeed), 0, (mGyroTarget - sDrivetrain.GetGyroAngle()) * mRotationMultiplier);
    } else {
      sDrivetrain.DriveWithStrafe(mYSpeed, 0, (mGyroTarget - sDrivetrain.GetGyroAngle()) * mRotationMultiplier);
    }
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
