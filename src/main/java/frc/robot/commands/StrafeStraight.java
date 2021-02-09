// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * Strafe Straight Command
 * 
 * <p>This commmand uses the Drivetrain and Gyro to strafe while maintaining rotation
 * 
 * @version February 6, 2021
 * @author Cece
 */
public class StrafeStraight extends CommandBase {
  private Drivetrain sDrivetrain;
  private double YSpeed;
  private double rotationMultiplier = 0.06;
  private double gyroTarget;
  /** Creates a new StrafeStraight. */
  public StrafeStraight(Drivetrain pDrivetrain, double pYSpeed) {
    sDrivetrain = pDrivetrain;
    YSpeed = pYSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    gyroTarget = sDrivetrain.GetGyroAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    sDrivetrain.DriveWithStrafe(YSpeed, 0, -(sDrivetrain.GetGyroAngle() - gyroTarget) * rotationMultiplier);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sDrivetrain.StopDrivetrain();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
