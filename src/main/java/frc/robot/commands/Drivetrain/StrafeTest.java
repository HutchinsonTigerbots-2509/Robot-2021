package frc.robot.commands.Drivetrain;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

/**
 * Test Command For Testing Strafe.
 */
public class StrafeTest extends SequentialCommandGroup {

  public StrafeTest(Drivetrain pDrivetrain) {

    addCommands(new StrafeStraight(pDrivetrain, -0.9).withTimeout(3), 
                new StrafeStraight(pDrivetrain, 0.9).withTimeout(3), 
                new StrafeStraight(pDrivetrain, -0.9).withTimeout(3), 
                new StrafeStraight(pDrivetrain, 0.9).withTimeout(3));
  
  }

}
