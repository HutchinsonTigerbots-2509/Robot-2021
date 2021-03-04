// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor.Conveyor;

public class ConveyorUp extends CommandBase {
  private Conveyor sCon;
  /** Creates a new ConveyorUp. */
  public ConveyorUp(Conveyor pCon) {
    sCon = pCon;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sCon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sCon.setConveyor(0.8);
    sCon.setAgitator(-0.8);
  }

  // Called every time the scheduler runs while the command is scheduled.
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sCon.setConveyor(0);
    sCon.setAgitator(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return sCon.BottomSensorValue;
  }
}
