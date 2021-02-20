// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorJog extends CommandBase {
  private Conveyor sCon;
  private double starttime = 0;
  private double endtime = 0;
  private double duration = 0;
  private boolean down;
  private boolean isDone;

  /** Creates a new ConveyorJog. */
  public ConveyorJog(Conveyor pCon, boolean isDown, double pDuration) {
    sCon = pCon;
    down = isDown;
    duration = pDuration;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sCon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    starttime = Timer.getFPGATimestamp();
    endtime = Timer.getFPGATimestamp() + duration;
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(endtime + " : " + starttime + " : " + Timer.getFPGATimestamp());
    if(Timer.getFPGATimestamp() < endtime) {
      if(down) {
        sCon.MoveConveyor(-1);
      } else {
        sCon.MoveConveyor(1);
      }
    } else {
      isDone = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sCon.MoveConveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
