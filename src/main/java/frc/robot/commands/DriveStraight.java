// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * Drive Straight Command
 * 
 * <p>This command uses the Drivetrain subsystem to Drive forward or backward, while correcting for rotation
 * using the gyro.
 * 
 * @version February 6, 2021
 * @author Cece
 */
public class DriveStraight extends CommandBase {
  //Subsystems
  private Drivetrain sDrivetrain;
  //Speed variables
  private double XSpeed;
  private double ZMultiplier = 0.04;
  //Gyro
  private Double gyroTarget;
  
   /**
   * Drive Straight Constructor.
   * 
   * <p>Drives the robot straight, using the current gyro angle to correct for rotation
   */
  public DriveStraight(Drivetrain pDrivetrain, double pXSpeed) {
    sDrivetrain = pDrivetrain;
    XSpeed = pXSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
  }

  /**
   * Drive Straight Constructor.
   * 
   * <p>Drives the robot straight, using a specified gyro angle to correct for rotation
   */
  public DriveStraight(Drivetrain pDrivetrain, double pXSpeed, double pGyroTarget){
    sDrivetrain = pDrivetrain;
    XSpeed = pXSpeed;
    gyroTarget = pGyroTarget;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
  }

  @Override
  public void initialize() {
    //If the gyro target wasn't specified, use the current angle instead.
    if(gyroTarget == null){
      gyroTarget = sDrivetrain.GetGyroAngle();
    }
    SmartDashboard.putNumber("Gyro Target", gyroTarget);
  }

  @Override
  public void execute() {
    //Sends the voltage to the Drivetrain motors
    sDrivetrain.DriveWithoutStrafe(XSpeed, -(sDrivetrain.GetGyroAngle() - gyroTarget) * ZMultiplier);
  }

  @Override
  public void end(boolean interrupted) {
    //Stops the robot
    sDrivetrain.StopDrivetrain();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
