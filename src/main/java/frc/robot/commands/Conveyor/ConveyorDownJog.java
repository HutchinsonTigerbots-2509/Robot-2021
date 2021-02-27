package frc.robot.commands.Conveyor;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Conveyor;

/**
 * Will run the conveyor down then back up using the ConveyorJog Command
 */
public class ConveyorDownJog extends SequentialCommandGroup {
  
  /**
   * Runs the command ConveyorJog twice, one after the other
   * in a sequential order.
   * 
   * @param pCon - Conveyor Subsystem
   */
  public ConveyorDownJog(Conveyor pCon) {
    addCommands(new ConveyorJog(pCon, true, 0.1), 
                new ConveyorJog(pCon, false, 0.3));
  }

}
