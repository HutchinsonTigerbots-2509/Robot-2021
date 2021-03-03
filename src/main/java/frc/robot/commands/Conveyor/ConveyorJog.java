package frc.robot.commands.Conveyor;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor.Conveyor;

/**
 * This command will power the conveyor for a set duration.
 */
public class ConveyorJog extends CommandBase {


  private Conveyor sCon;

  private boolean isGoingDown;

  // Variables for the Timer
  private double endtime = 0;
  private double duration = 0;

  /**
   * @param pCon          - Conveyor Subsystem Object
   * @param pisGoingDown  - Set 'true' for balls going down, set 'false' for balls going up 
   * @param pDuration     - Time in seconds for run (reccomended less than 0.5)
   */
  public ConveyorJog(Conveyor pCon, boolean pisGoingDown, double pDuration) {
    
    sCon = pCon;
    
    isGoingDown = pisGoingDown;
    
    duration = pDuration;
    
    addRequirements(sCon);
  
  }


  /**
   * Sets up our timer for keeping track of our duration and starts
   * conveyor.
   */
  public void initialize() {

    if(isGoingDown) {
      sCon.MoveConveyor(-1);
    } else {
      sCon.MoveConveyor(1);
    }

    endtime = Timer.getFPGATimestamp() + duration;
   
  }

  /**
   * Continuously checks if the duration has passed. If it has,
   * the conveyor will stop, and the program will exit the command.
   * If not, the conveyor will continue to run.
   */
  public boolean isFinished() {

    if(Timer.getFPGATimestamp() > endtime) {
  
      sCon.MoveConveyor(0);
      return true;
  
    }

    return false;
  
  }


}
