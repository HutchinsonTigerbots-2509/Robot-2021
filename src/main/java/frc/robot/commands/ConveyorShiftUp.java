// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorShiftUp extends CommandBase {
  private Conveyor sCon;
  private int currentTopIndex;
  private boolean isDone = false;
  private Command IfFailedDoThis;

  /** Creates a new ConveyorShiftUp. */
  public ConveyorShiftUp(Conveyor pCon) {
    sCon = pCon;
    IfFailedDoThis = new ConveyorShiftDown(sCon);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sCon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    currentTopIndex = sCon.getTopIndex();
    sCon.MoveConveyor(1);
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(currentTopIndex);
    if (currentTopIndex == 0) {
      if (sCon.GetMiddleSensorValue()) {
        isDone = true;
      }
    } else if (currentTopIndex == 1) {
      if (sCon.GetTopSensorValue()) {
        isDone = true;
      }
    } 
    // TODO
    //else if (currentTopIndex == 2) {
    //
    //}
    else {
      if (sCon.GetBottomSensorValue()) {
        isDone = true;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(sCon.isGap()) {
      IfFailedDoThis.schedule();
    }
    sCon.MoveConveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
