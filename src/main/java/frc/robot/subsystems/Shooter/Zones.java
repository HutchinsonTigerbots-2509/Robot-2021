package frc.robot.subsystems.Shooter;

/**
   * The color of zones for shooting during Autonomous
   * 
   * I think we can use a command to set this value, and have it update
   * the value based on joystick input. Then we can have these update
   * in 'periodic()', where it will bring the flap to the correct location.
   * - Ronnerino
   */
public enum Zones {      // Rows on field
    GREEN,               // 0 - 3 
    YELLOW,              // 3 - 5
    BLUE,                // 5 - 7
    RED,                 // 7 - 9
    REINTRODUCTION_ZONE  // 9 - End
}
