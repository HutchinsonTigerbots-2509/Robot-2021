package frc.robot.subsystems.Conveyor;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;

public class Conveyor extends SubsystemBase {

  private VictorSP ConveyorMotor = new VictorSP(Constants.kConveyorMotorID);
  private WPI_TalonSRX Agitator = new WPI_TalonSRX(Constants.kAgitatorID);
  
  public boolean TopSensorValue = false;
  public boolean BottomSensorValue = false;
  
  public Conveyor() {
    Agitator.setNeutralMode(NeutralMode.Brake);
  }

  public void periodic() {}

  public void setAgitator(double Speed) {
    Agitator.set(Speed);
  }

  public void setConveyor(double Speed) {
    ConveyorMotor.set(Speed);
  }
}
