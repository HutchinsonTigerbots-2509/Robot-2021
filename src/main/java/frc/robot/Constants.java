// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * Constants class.
 * 
 * <p>
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 * 
 * <p>
 * When declaring constants, they should start with k. (i.e. kLeftMotorID).
 * 
 * @version February 9, 2021
 * @author First Generated
 * @author Quinton MacMullan
 * @author Noah Sturges
 * @author Teagan Young
 * @author Cole Gartner
 */
public final class Constants {
    // ***** Subsystems ***** //

    // Drivetrain
    public static int kFrontLeftID = 2;
    public static int kFrontRightID = 1;
    public static int kRearLeftID = 3;
    public static int kRearRightID = 0;

    public static double kDrivetrainMinVoltage = 0.4;

    // Shooter
    public static int kShooterMotorID = 4;

    public static double kShooterTicksPerRevolution = 2048;

    // Conveyor
    public static int kConveyorMotorID;

    // Intake
    public static int kIntakeMotorID;

    // ***** Vision Constants ***** //

    // Network
    public static String kLimelightIP = "10.25.9.11";
    public static String kLimelightNetworkID = "limelight";

    // Settings
    public static int kLimelightLED = 0; // Sets LED. 0 = Set by Pipline, 1 = Force off, 2 = Force blink, 3 = Force on
    public static int kLimelightMode = 0; // Sets camera mode. 0 = Vision processor, 1 = Driver Camera
    public static int kLimelightStream = 0; // Sets streaming mode. 0 = Side-by-Side, 1 = PiP main, 2 = PiP secondary
    public static int kLimelightStartingPipeline = 1; // The default pipeline to stream

    // Table IDs (for getting values from the Network Table)
    public static String kLimelightLatencyID = "tl"; // Pipeline latency in milliseconds
    public static String kLimelightTargetID = "tv"; // Whether or not a valid target is found (0 or 1)
    public static String kLimelightTargetXID = "tx"; // Horizontal offset from crosshair to target (+/- 27 degrees)
    public static String kLimelightTargetYID = "ty"; // Vertical offset from crosshair to target (+/- 20.5 degrees)
    public static String kLimelightTargetAreaID = "ta"; // Target area (0-100 % of image)
    public static String kLimelightTargetSkewID = "ts"; // Target skew/rotation (-90 to 0 degrees)
    public static String kLimelightTargetVertID = "tvert"; // Vertical sidelength of bounding box (0-320 pixels)
    public static String kLimelightTargetHorID = "thor"; // Horizontal sidelength of bounding box (0-320 pixels)

    // ***** Joysticks ***** //

    public final static int kOpStickID = 0;
    public final static int kCoOpStickID = 1;

    // Xbox Controller Variables
    public final static int kXboxLeftJoystickX = 0;
    public final static int kXboxLeftJoystickY = 1;
    public final static int kXboxLeftTrigger = 2;
    public final static int kXboxRightTrigger = 3;
    public final static int kXboxRightJoystickX = 4;
    public final static int kXboxRightJoystickY = 5;
    public final static int kXboxButtonA = 1;
    public final static int kXboxButtonB = 2;
    public final static int kXboxButtonX = 3;
    public final static int kXboxButtonY = 4;
    public final static int kXboxLeftBumper = 5;
    public final static int kXboxRightBumper = 6;
    public final static int kXboxButtonBack = 7;
    public final static int kXboxButtonStart = 8;
    public final static int kXboxLeftJoystickButton = 9;
    public final static int kXboxRightJoystickButton = 10;
}
