// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Vision;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class USBVision2 extends SubsystemBase {
  // UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
  // 'Sinks' the camera stream into a processable image
  // public static CvSink cvSink = CameraServer.getInstance().getVideo();

  // An output stream that sends our processed image out onto
  // the Shuffleboard
  // public static CvSource outputStream = CameraServer.getInstance().putVideo("CrossHairs-Processed", 160, 120); // 160, 120
  
  // public static Mat source = new Mat();
  
  /** Creates a new USBVision2. */
  public USBVision2() {
    // cvSink.setSource(camera);
  }

  @Override
  public void periodic() {
    // cvSink.grabFrame(source);
    // outputStream.putFrame(source);
    // This method will be called once per scheduler run
  }
}
