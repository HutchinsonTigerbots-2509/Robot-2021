// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class RotateCorrection extends CommandBase {
  Drivetrain sDriveTrain;
  Vision sVision;

  //Y Target
  private double mYTarget;
  //X Target
  private double mXTarget;
  //Boolean
  private boolean islessthan0;

  /** Creates a new RotateCorrection. */
  public RotateCorrection(Drivetrain pDriveTrain, Vision pVision) {
    sDriveTrain = pDriveTrain;
    sVision = pVision;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sVision, sDriveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mXTarget = sVision.getTargetX();
    if(mXTarget > 0){
      islessthan0 = false;
    }else{
      islessthan0 = true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(islessthan0) {
      sDriveTrain.DriveWithStrafe(0, 0, -0.3);
    }else if(!islessthan0) {
      sDriveTrain.DriveWithStrafe(0, 0, 0.3);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sDriveTrain.DriveWithStrafe(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(islessthan0) {
      if(sVision.getTargetX() > 0) {
        return true;
      }
    }else if(!islessthan0) {
      if(sVision.getTargetX() < 0) {
        return true;
      }
    }
    return false;
  }
}
