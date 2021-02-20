// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorShiftDown extends CommandBase {
  private Conveyor sCon;
  private boolean isDone = false;
  private boolean currBottom;
  private boolean currMiddle;
  private boolean currTop;

  /** Creates a new ConveyorShiftUp. */
  public ConveyorShiftDown(Conveyor pCon) {
    sCon = pCon;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sCon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sCon.MoveConveyor(-1);
    isDone = false;
    currBottom = sCon.GetBottomSensorValue();
    currMiddle = sCon.GetMiddleSensorValue();
    currTop = sCon.GetTopSensorValue();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println(currentIndex);
    if (currMiddle && !currBottom) {
      if (sCon.GetBottomSensorValue() && !sCon.GetMiddleSensorValue()) {
        isDone = true;
      }
    } else if (currTop && !currMiddle) {
      if (sCon.GetMiddleSensorValue() && !sCon.GetTopSensorValue()) {
        isDone = true;
      }
    } else {
      isDone = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (currTop && !currMiddle) {
      Command x = new ConveyorDownJog(sCon);
      x.schedule();
    }
    sCon.MoveConveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
