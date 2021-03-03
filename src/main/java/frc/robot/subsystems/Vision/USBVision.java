package frc.robot.subsystems.Vision;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class USBVision extends SubsystemBase {
  
  // Actual Camera Stream - Web Address of http://roboRIO-2509-FRC.local/1181/?action=stream
  public static UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("BALL", 0);

  // 'Sinks' the camera stream into a processable image
  public static CvSink cvSink = CameraServer.getInstance().getVideo("BALL");

  // An output stream that sends our processed image out onto
  // the Shuffleboard
  public static CvSource outputStream = CameraServer.getInstance().putVideo("BALL2", 160, 120); // 160, 120


  // Pipeline Declarlations
  ProofPipeline proof_pipeline = new ProofPipeline();

  /* Matrixes for Values of Images */
  Mat source = new Mat(); // Our Source Image
  Mat output = new Mat(); // The processed Image
  
  /**
   * Creates a new USBVision.
   */
  public USBVision() {
    camera.setResolution(640, 480); // 640, 480
    // camera.setFPS(10);

    cvSink.setSource(camera);
  }

  public FoundContour[] getContours() {
    FoundContour[] Contours = new FoundContour[5];
    // for (FoundContour Contour : Contours) {
    //   Contour = new FoundContour(-1, -1 , -1, false);
    // }
    Contours[0] = new FoundContour(-1, -1 , -1, false);
    Contours[1] = new FoundContour(-1, -1 , -1, false);
    Contours[2] = new FoundContour(-1, -1 , -1, false);
    Contours[3] = new FoundContour(-1, -1 , -1, false);
    Contours[4] = new FoundContour(-1, -1 , -1, false);

    // Empty Object for Logic
    Object imgLock = new Object();;

    // Ends process if there is no image (FPS slower) than
    // the code refresh rate
    if (cvSink.grabFrame(source) == 0) {
      return Contours;
    }

    // Will show the processed image up to the
    // blur step
    proof_pipeline.process(source);
    outputStream.putFrame(proof_pipeline.blurOutput());

    cvSink.grabFrame(source);
    if (!proof_pipeline.filterContoursOutput().isEmpty()) {
      for (int i = 0; i < 5; i++) {
        try {
          SmartDashboard.putNumber("i", i);
          Rect r = Imgproc.boundingRect(proof_pipeline.filterContoursOutput().get(i));
          synchronized (imgLock) {
            Contours[i].CenterX = (r.x + (r.width / 2));
            Contours[i].CenterY = (r.y + (r.height / 2));
            Contours[i].Area = (r.area());
          }
        } catch (Exception e) {
          // System.out.println("CAN'T HAVE EMPTY CONTOURS");
          System.out.println(e);
          return Contours;
        }
      }
    }

    // DEBUGGING
    // if (!pipeline.filterContoursOutput().isEmpty()) {
    //     SmartDashboard.putNumber("i", i);
    //     Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
    //     synchronized (imgLock) {
    //       centerX = r.x + (r.width / 2);
    //     }
    // }

    // DEBUGGING
    // SmartDashboard.putNumber("Center 1", centers[0]);
    // SmartDashboard.putNumber("Center 2", centers[1]);
    // SmartDashboard.putNumber("Center 3", centers[2]);
    // SmartDashboard.putNumber("CenterX", centerX);

    return Contours;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}