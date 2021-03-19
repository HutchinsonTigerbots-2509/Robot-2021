// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Drivetrain.AxisAccel;

public class DriveBackwardAutonomous extends CommandBase {
  private Drivetrain sDrivetrain;
  private double mXSpeed;
  private AxisAccel mDriveAxis = new AxisAccel(0.04, 0.04);
  /** Creates a new DriveBackwardAutonomous. */
  public DriveBackwardAutonomous(Drivetrain pDrivetrain, double pXSpeed) {
    sDrivetrain = pDrivetrain;
    mXSpeed = pXSpeed;
    

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    sDrivetrain.FieldOrientedDrive(0, mDriveAxis.periodic(mXSpeed), 0, sDrivetrain.GetGyroAngle());
    // sDrivetrain.DriveWithoutStrafe(mDriveAxis.periodic(mXSpeed), 0);
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
