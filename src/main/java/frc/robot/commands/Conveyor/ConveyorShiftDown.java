package frc.robot.commands.Conveyor;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor.Conveyor;

/**
 * Shifts all balls in the conveyor down one, then will
 * run the command ConveyorDownJog
 */
public class ConveyorShiftDown extends CommandBase {
  
  private Conveyor sCon;
  
  private boolean isDone = false;

  private boolean currBottom;
  private boolean currMiddle;
  private boolean currTop;

  public ConveyorShiftDown(Conveyor pCon) {
    
    sCon = pCon;
    
    addRequirements(sCon);
  
  }

  /**
   * The program will immeadately set the conveyor moving the balls out. It will
   * also get the current state of the light sensors on the conveyor.
   */
  public void initialize() {

    sCon.MoveConveyor(-1);
    
    isDone = false;
    
    currBottom = sCon.BottomSensorValue;
    currMiddle = sCon.MiddleSensorValue;
    currTop = sCon.TopSensorValue;
  
  }

  /**
   * Will find out which of the two gaps are present, then will
   * run the conveyor down until the gap is gone.
   */
  public void execute() {

    //System.out.println(currentIndex);
    
    if (currMiddle && !currBottom) {
    
      if (sCon.BottomSensorValue && !sCon.MiddleSensorValue) {
        isDone = true;
      }
    
    } else if (currTop && !currMiddle) {
    
      if (sCon.MiddleSensorValue && !sCon.TopSensorValue) {
        isDone = true;
      }
    
    } else {
      isDone = true;
    }

  }

  /**
   * It will check if the command failed to fix the gap, and, if it did,
   * it will jog the conveyor then stop the conveyor. If command did fix it,
   * then it will just stop the conveyor.
   */
  public void end(boolean interrupted) {

    if (currTop && !currMiddle) {
    
      Command Jog = new ConveyorDownJog(sCon);
      Jog.schedule();
    
    }
    
    sCon.MoveConveyor(0);
  
  }

  /**
   * If isDone is set true in execute(), then the command
   * will be stopped.
   */
  public boolean isFinished() {
    return isDone;
  }
}
