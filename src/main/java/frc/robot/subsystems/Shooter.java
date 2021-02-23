// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Shooter Subsystem.
 * 
 * <p>
 * The Shooter subsystems runs the shooter.
 * 
 * @version February 9, 2021
 * @author Noah Sturges
 * @author Quinton MacMullan
 */
public class Shooter extends SubsystemBase {
  private WPI_TalonFX mShooterMotor = new WPI_TalonFX(Constants.kShooterMotorID);

  /** Creates a new Shooter. */
  public Shooter() {
    mShooterMotor.setNeutralMode(NeutralMode.Coast);
  }

  /** periodic method. */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter RPM", GetRPM());
    SmartDashboard.putNumber("Shooter volts", GetMotorOutputPercent());
  }

  /**
   * Gets the RPM of the shooter motor.
   * 
   * @return RPM
   */
  public double GetRPM() {
    return Math.abs((600 * mShooterMotor.getSelectedSensorVelocity()) / Constants.kShooterTicksPerRevolution);
  }

  
  public double GetMotorOutputPercent() {
    return mShooterMotor.getMotorOutputPercent();
  }

  /**
   * Sets shooter motor speed.
   * 
   * @param pSpeed speed of the motor
   */
  public void RunShooter(double pSpeed) {
    mShooterMotor.set(pSpeed);
  }
}
