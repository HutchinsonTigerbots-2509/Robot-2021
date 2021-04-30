package frc.robot.subsystems.Vision;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class USBVision extends SubsystemBase {
  
  // Actual Camera Stream sent back from the robot
  public static UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("BALL", 1);

  // 'Sinks' the camera stream into a processable image
  public static CvSink cvSink = CameraServer.getInstance().getVideo("BALL");

  // Generated Pipeline from Grip application (Ctrl+G in Grip, select Java)
  private GripPipeline pipeline = new GripPipeline();

  // Matrixes for Values of Images
  private Mat source = new Mat();

  private int width = 640;
  private int height = 480;


  public USBVision() {
    camera.setResolution(width, height);
    cvSink.setSource(camera);
  }

  FoundContour Ball = new FoundContour();

  private void updateContours() {
    
    Ball.setX(-1);
    Ball.setY(-1);

    Object imgLock = new Object();;

    if (cvSink.grabFrame(source) != 0) {

      cvSink.grabFrame(source);
      pipeline.process(source);

      if (!pipeline.filterContoursOutput().isEmpty()) {

        for (int i = 0; i < 2; i++) {
      
          try {
            
            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(i));
            
            synchronized (imgLock) {
              Ball.setX((r.x + (r.width / 2))); 
              Ball.setY((r.y + (r.height / 2)));
            }
          } 
          catch (Exception e) {
            System.out.println(e);
          }
        }
      }
    }
  }

  public BallState FindBallsForIntake() {
    
    updateContours();
    
    if (Ball.getX() == -1) {
      return BallState.NONE;
    }

    else if (Ball.getX() > width/2 + 20) {
      return BallState.RIGHT;
    }

    else if (Ball.getX() < width/2 - 20) {
      return BallState.LEFT;
    }
    
    else {
      return BallState.CENTER;
    }

  }

  @Override
  public void periodic() {}
}