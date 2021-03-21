// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter.Shooter;

public class RampDownShooter2 extends CommandBase {
  private Shooter sShooter;
 
  /** Creates a new RampDownShooter2. */
  public RampDownShooter2() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sShooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sShooter.RunShooter(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    sShooter.RunShooter(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
