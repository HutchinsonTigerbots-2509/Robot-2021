package frc.robot.commands.Drivetrain;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
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
  //Gyro
  private Double mGyroTarget;
  
   /**
   * Drive Straight Constructor.
   * 
   * <p>Drives the robot straight, using the current gyro angle to correct for rotation
   * @param pDrivetrain Drivetrain subsystem
   * @param pXSpeed Forward Speed
   */
  public DriveStraight(Drivetrain pDrivetrain, double pXSpeed) {
    sDrivetrain = pDrivetrain;
    mXSpeed = pXSpeed;
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
   */
  public DriveStraight(Drivetrain pDrivetrain, double pXSpeed, double pGyroTarget){
    sDrivetrain = pDrivetrain;
    mXSpeed = pXSpeed;
    mGyroTarget = pGyroTarget;
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
    sDrivetrain.DriveWithoutStrafe(mXSpeed, -(sDrivetrain.GetGyroAngle() - mGyroTarget) * mZMultiplier);
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
