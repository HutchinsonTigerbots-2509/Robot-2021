package frc.robot.subsystems.Conveyor;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;

public class Conveyor extends SubsystemBase {

  private VictorSP ConveyorMotor = new VictorSP(Constants.kConveyorMotorID);
  private WPI_TalonSRX Agitator = new WPI_TalonSRX(Constants.kAgitatorID);

  private AnalogInput topLightSensor = new AnalogInput(Constants.kTopLightSensorID);
  private AnalogInput bottomLightSensor = new AnalogInput(Constants.kBottomLightSensorID);
  
  public boolean TopSensorValue = false;
  public boolean BottomSensorValue = false;
  
  public Conveyor() {
    Agitator.setNeutralMode(NeutralMode.Brake);
  }

  /**
   * Cotinually updates the class variables storing sensor values and
   * the number of balls in the conveyor.
   */
  public void periodic() {
    UpdateTopSensorValue();
    UpdateBottomSensorValue();
    SmartDashboard.putNumber("Top Sensor", topLightSensor.getVoltage());
    SmartDashboard.putNumber("Bottom Sensor", bottomLightSensor.getVoltage());

  }

  public void UpdateTopSensorValue() {
  
    if (topLightSensor.getVoltage() < 0.15) {
      TopSensorValue = true;
    } else {
      TopSensorValue = false;
    }
  
  }

  public void UpdateBottomSensorValue() {
  
    if (bottomLightSensor.getVoltage() > 0.6) {
      BottomSensorValue = true;
    } else {
      BottomSensorValue = false;
    }
  
  }

  /**
   * Sets the voltage output of conveyor and agitator from the passed parameter
   * 
   * @param Speed range -1 to 1
   */
  public void MoveConveyor (double Speed) {

    ConveyorMotor.set(Speed);

    if(Speed > 0) {
      Agitator.set(-0.5);
    } else if(Speed < 0) {
      Agitator.set(0.5);
    } else {
      Agitator.set(0);
    }

  }

  public void setAgitator(double Speed) {
    Agitator.set(Speed);
  }

  public void setConveyor(double Speed) {
    ConveyorMotor.set(Speed);
  }
}
