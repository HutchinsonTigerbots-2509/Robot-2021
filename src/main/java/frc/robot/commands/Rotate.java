// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

/**
 * Rotate Command
 * 
 * <p>This command uses the Drivetrain and Vision subsystems to rotate the robot around a vision target.
 * 
 * @version February 6, 2021
 * @author Cece
 */
public class Rotate extends CommandBase {
  //Subsystems
  private Drivetrain sDrivetrain;
  private Vision sVision;
  // Speed variables
  private double strafeSpeed;
  private double forwardSpeed;
  private double maxForwardSpeed = 0.6;
  //Multipliers
  private double forwardMultiplier = 0.05; // 0.06
  private double rotationMultiplier = 0.05; //0.06
  //Y Target
  private double YTarget;
  
  public Rotate(Drivetrain pDrivetrain, Vision pVision, double pStrafeSpeed, double pYTarget) {
    sDrivetrain = pDrivetrain;
    sVision = pVision;
    strafeSpeed = pStrafeSpeed;
    YTarget = pYTarget;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
    addRequirements(sVision);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    //Sets the forward speed based on the Y position of the target
    if(YTarget < sVision.getTargetY()){
      forwardSpeed = (Math.abs(YTarget - sVision.getTargetY())) * forwardMultiplier;
    }else if(YTarget > sVision.getTargetY()){
      forwardSpeed = -(Math.abs(YTarget - sVision.getTargetY())) * forwardMultiplier;
    }else{
      forwardSpeed = 0;
    }
    //Checks to make sure the speed is less than the max speed
    if(forwardSpeed > maxForwardSpeed){
      forwardSpeed = maxForwardSpeed;
    } else if (forwardSpeed < -maxForwardSpeed){
      forwardSpeed = -maxForwardSpeed;
    }

    //Updates the speed of the Drivetrain motors
    sDrivetrain.DriveWithStrafe(strafeSpeed, forwardSpeed, sVision.getTargetX() * rotationMultiplier);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
