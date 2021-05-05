package frc.robot;

/**
 * To use this class, first import this class and then reference the public variables 
 * like this: 'Constants.kVariableName'
 * 
 * <p> 
 * All variables should be declared public final static, which means that they
 * have a global scope (can be accessed by everything), can't be changed, and there
 * is only one copy of the variable throughout the whole program.
 * 
 * <p>
 * Variables will start with a 'k' for naming convention.
 */
public class Constants { 

    // ***** Motor IDs ***** //
    
    // Drivetrain
    public final static int kFrontLeftID = 2;                // TalonFX - CAN ID
    public final static int kFrontRightID = 1;               // TalonFX - CAN ID
    public final static int kRearLeftID = 3;                 // TalonFX - CAN ID
    public final static int kRearRightID = 0;                // TalonFX - CAN ID

    // Shooter
    public final static int kShooterMotorID = 4;             // TalonFX - CAN ID
    public final static int kFlapperMotorID = 10378913;      // TalonSRX - CAN ID 

    // Intake
    public final static int kIntakeMotorID = 5;              // VictorSP - RIO PWM Port

    // Conveyor
    public final static int kConveyorMotorID = 6;            // VictorSP - RIO PWM Port
    public final static int kAgitatorID = 7;                 // TalonSRX - CAN ID 


    
    // ***** Subsystem Costants ***** //

    // Drivetrain
    public final static double kDrivetrainMinVoltage = 0.4;
    public final static int kDrivetrainTicksPerRev = 2048;
    public final static double kDrivetrainWheelDiameter = 6.0;

    // Shooter
    public final static double kShooterTicksPerRevolution = 2048;
      

    // Conveyor
    public final static int kTopLightSensorID = 1;           // Analog Input - RIO Analog Port
    public final static int kBottomLightSensorID = 0;        // Analog Input - RIO Analog Port
    


    // ***** Vision Constants ***** //

    // Network
    public static String kLimelightIP = "10.25.9.11";        // IP Address of Camera
    public static String kLimelightNetworkID = "limelight";  // Name of Camera on Network

    // Settings
    public static int kLimelightLED = 0;                     // Sets LED. 0 = Set by Pipline, 1 = Force off, 2 = Force blink, 3 = Force on
    public static int kLimelightMode = 1;                    // Sets camera mode. 0 = Vision processor, 1 = Driver Camera
    public static int kLimelightStream = 2;                  // Sets streaming mode. 0 = Side-by-Side, 1 = PiP main, 2 = PiP secondary
    public static int kLimelightStartingPipeline = 1;        // The default pipeline to stream

    // Table IDs (for getting values from the Network Table)
    public static String kLimelightLatencyID = "tl";         // Pipeline latency in milliseconds
    public static String kLimelightTargetID = "tv";          // Whether or not a valid target is found (0 or 1)
    public static String kLimelightTargetXID = "tx";         // Horizontal offset from crosshair to target (+/- 27 degrees)
    public static String kLimelightTargetYID = "ty";         // Vertical offset from crosshair to target (+/- 20.5 degrees)
    public static String kLimelightTargetAreaID = "ta";      // Target area (0-100 % of image)
    public static String kLimelightTargetSkewID = "ts";      // Target skew/rotation (-90 to 0 degrees)
    public static String kLimelightTargetVertID = "tvert";   // Vertical sidelength of bounding box (0-320 pixels)
    public static String kLimelightTargetHorID = "thor";     // Horizontal sidelength of bounding box (0-320 pixels)



    // ***** Joysticks ***** //

    // Joystick IDs - set with Driverstation
    public final static int kOpStickID = 0;
    public final static int kCoOpStickID = 1;

    // Xbox Controller Button IDs
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