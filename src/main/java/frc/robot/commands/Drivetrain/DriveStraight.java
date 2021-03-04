package frc.robot.commands.Drivetrain;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain.AxisAccel;
import frc.robot.subsystems.Drivetrain.Drivetrain;

/**
 * Drive Straight Command
 * 
 * <p>This command uses the Drivetrain subsystem to Drive forward or backward, while correcting for rotation
 * using the gyro.
 * 
 * @version February 6, 2021
 * @author Cece
 */
public class DriveStraight extends CommandBase {
  //Subsystems
  private Drivetrain sDrivetrain;
  //Speed variables
  private double mXSpeed;
  private double mZMultiplier = 0.04;
  private AxisAccel mDriveAxis = new AxisAccel(0.04, 0.04);
  private boolean mAccel;
  //Gyro
  private Double mGyroTarget;
  
   /**
   * Drive Straight Constructor.
   * 
   * <p>Drives the robot straight, using the current gyro angle to correct for rotation
   * @param pDrivetrain Drivetrain subsystem
   * @param pXSpeed Forward Speed
   * @param pAccel Whether or not to accelerate into the move
   */
  public DriveStraight(Drivetrain pDrivetrain, double pXSpeed, boolean pAccel) {
    sDrivetrain = pDrivetrain;
    mXSpeed = pXSpeed;
    mAccel = pAccel;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
  }

  /**
   * Drive Straight Constructor.
   * 
   * <p>Drives the robot straight, using a specified gyro angle to correct for rotation
   * @param pDrivetrain Drivetrain Subsystem
   * @param pXSpeed Forward Speed
   * @param pGyroTarget The gyro angle to Target
   * @param pAccel Whether or not to accelerate into the move
   */
  public DriveStraight(Drivetrain pDrivetrain, double pXSpeed, double pGyroTarget, boolean pAccel){
    sDrivetrain = pDrivetrain;
    mXSpeed = pXSpeed;
    mGyroTarget = pGyroTarget;
    mAccel = pAccel;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
  }

  /** Called when the command is initially scheduled. */
  @Override
  public void initialize() {
    //If the gyro target wasn't specified, use the current angle instead.
    if(mGyroTarget == null){
      mGyroTarget = sDrivetrain.GetGyroAngle();
    }
  }

  /** Called every time the scheduler runs while the command is scheduled. */
  @Override
  public void execute() {
    //Sends the voltage to the Drivetrain motors
    if(mAccel){
      sDrivetrain.DriveWithoutStrafe(mDriveAxis.periodic(mXSpeed), -(sDrivetrain.GetGyroAngle() - mGyroTarget) * mZMultiplier);
    } else {
      sDrivetrain.DriveWithoutStrafe(mXSpeed, -(sDrivetrain.GetGyroAngle() - mGyroTarget) * mZMultiplier);
    }
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) {
    //Stops the robot
    sDrivetrain.StopDrivetrain();
  }

  /** Returns true when the command should end. */
  @Override
  public boolean isFinished() {
    return false;
  }

  
}
