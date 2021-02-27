package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  
  private VictorSP IntakeMotor = new VictorSP(Constants.kIntakeMotorID);

  public void periodic() {}


  public void IntakeIn() {
    IntakeMotor.set(-0.6);
  }

  public void IntakeOut() {
    IntakeMotor.set(0.6);
  }

  public void IntakeStop() {
    IntakeMotor.set(0);
  }
}
