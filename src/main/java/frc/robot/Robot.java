package frc.robot;


import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;

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
  private Command m_autonomousCommand;
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
    //mCompressor.stop();
    
    //Sets up the Drivetrain motors
    mRobotContainer.sDrivetrain.InitializeDrivetrain();
    new Thread(() -> {
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
      camera.setResolution(640, 480);
      

      CvSink cvSink = CameraServer.getInstance().getVideo();
      CvSource outputStream = CameraServer.getInstance().putVideo("CustomCrosshair", 640, 480);

      Mat source = new Mat();
      Mat output = new Mat();

      while(!Thread.interrupted()) {
        if (cvSink.grabFrame(source) == 0) {
          continue;
        }
        Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
        outputStream.putFrame(output);
      }
    }).start();
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

  /** This function is called once each time the robot enters Disabled mode. */
  public void disabledInit() {}

  /** This function is called periodically during Disabled mode. */
  public void disabledPeriodic() {}

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  public void autonomousInit() {
    m_autonomousCommand = mRobotContainer.getAutonomousCommand();
    // // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
    m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  public void autonomousPeriodic() {}

  /** This function is called during the beginning of operator control. */
  public void teleopInit() {
    // // This makes sure that the autonomous stops running when
    // // teleop starts running. If you want the autonomous to
    // // continue until interrupted by another command, remove
    // // this line or comment it out.
    if (m_autonomousCommand != null) {
    m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  public void teleopPeriodic() {}

  /** This function is called at the beginning of test mode */
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  public void testPeriodic() {}
}
