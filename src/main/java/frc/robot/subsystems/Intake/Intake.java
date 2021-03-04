package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  
  private VictorSP IntakeMotor = new VictorSP(Constants.kIntakeMotorID);
  private DoubleSolenoid mExtender = new DoubleSolenoid(1, 0);

  public Intake(){}

  public void periodic() {}

  public void IntakeIn() {
    IntakeMotor.set(1);
  }

  public void IntakeOut() {
    IntakeMotor.set(-1);
  }

  public void IntakeStop() {
    IntakeMotor.set(0);
  }

  public void Extend(){
    mExtender.set(Value.kForward);
  }

  public void Retract(){
    mExtender.set(Value.kReverse);
  }
}
