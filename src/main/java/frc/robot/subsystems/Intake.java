// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private VictorSP IntakeMotor = new VictorSP(Constants.kIntakeMotorID);

  /** Creates a new Intake. */
  public Intake() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void RunIntakeIn(double Speed){
    IntakeMotor.set(Speed);
  }

  public void RunIntakeOut(double Speed){
    IntakeMotor.set(-Speed);
  }

  public void StopIntake(){
    IntakeMotor.set(0);
  }
}
