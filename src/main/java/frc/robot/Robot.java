package frc.robot;


import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * Robot class.
 * 
 * <p>
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation.
 * 
 * <p>
 * ===== Authors of Robot-2021 =====
 * @author Cece
 * @author Gart
 * @author Grace Swaja
 * @author Noah Sturges
 * @author Quinton MacMullan
 * @author Ron
 * @author Tegean Young
 */
public class Robot extends TimedRobot {

  private static RobotContainer mRobotContainer;
  private Compressor mCompressor = new Compressor();


  /**
   * Initialization.
   * 
   * <p>
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our autonomous chooser on the dashboard.
    mRobotContainer = new RobotContainer();
    
    
    mRobotContainer.sDrivetrain.InitializeDrivetrain();
    
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled commands, running already-scheduled commands, removing 
    // finished or interrupted commands, and running subsystem periodic() 
    // methods. This must be called from the robot's periodic block in order 
    // for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  public void disabledInit() {}
  public void disabledPeriodic() {}

  public void autonomousInit() {}
  public void autonomousPeriodic() {}

  public void teleopInit() {}
  public void teleopPeriodic() {}

  /** This function is called at the beginning of test mode */
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  public void testPeriodic() {}
}
