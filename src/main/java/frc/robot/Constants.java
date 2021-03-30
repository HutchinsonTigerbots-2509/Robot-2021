package frc.robot;

/**
 * To use this class, first import this class and then reference the public variables 
 * like this: 'Constants.kVariableName'
 * 
 * <p> 
 * All variables should be declared public final static, which means that they
 * have a global scope (can be accessed by everything), can't be changed, and there
 * is only one copy of the variable throughout the whole program.
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
    public final static int kSliderMotorID = 8;      // TalonSRX - CAN ID 

    // Intake
    public final static int kIntakeMotorID = 5;              // VictorSP - RIO PWM Port

    // Conveyor
    public final static int kConveyorMotorID = 6;            // VictorSP - RIO PWM Port
    public final static int kAgitatorID = 7;                 // TalonSRX - CAN ID 

    
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