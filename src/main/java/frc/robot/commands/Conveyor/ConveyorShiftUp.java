package frc.robot.commands.Conveyor;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor.Conveyor;

public class ConveyorShiftUp extends CommandBase {

  private Conveyor sCon;

  private int currentTopIndex;

  private boolean isDone = false;

  private Command IfFailedDoThis;

  /** Creates a new ConveyorShiftUp. */
  public ConveyorShiftUp(Conveyor pCon) {

    sCon = pCon;
    
    IfFailedDoThis = new ConveyorShiftDown(sCon);
    
    addRequirements(sCon);
  
  }

  // Called when the command is initially scheduled.
  public void initialize() {

    currentTopIndex = sCon.getTopIndex();
  
    sCon.MoveConveyor(1);
  
    isDone = false;
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  public void execute() {

    System.out.println(currentTopIndex);
    
    if (currentTopIndex == 0) {
    
      if (sCon.MiddleSensorValue) {
        isDone = true;
      }
    
    } else if (currentTopIndex == 1) {
    
      if (sCon.TopSensorValue) {
        isDone = true;
      }
    
    } 
    
    // TODO
    //else if (currentTopIndex == 2) {
    //
    //}
    
    else {

      if (sCon.BottomSensorValue) {
        isDone = true;
      }

    }

  }

  // Called once the command ends or is interrupted.
  public void end(boolean interrupted) {
    
    if(sCon.isThereAGap()) {
      IfFailedDoThis.schedule();
    }
    
    sCon.MoveConveyor(0);

  }

  // Returns true when the command should end.
  public boolean isFinished() {
    return isDone;
  }
}
