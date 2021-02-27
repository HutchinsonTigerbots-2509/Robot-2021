package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;

public class Conveyor extends SubsystemBase {

  private VictorSP ConveyorMotor = new VictorSP(Constants.kConveyorMotorID);
  private WPI_TalonSRX Agitator = new WPI_TalonSRX(Constants.kAgitatorID);

  private AnalogInput topLightSensor = new AnalogInput(Constants.kTopLightSensorID);
  private AnalogInput middleLightSensor = new AnalogInput(Constants.kMiddleLightSensorID);
  private AnalogInput bottomLightSensor = new AnalogInput(Constants.kBottomLightSensorID);

  private int BallsIn = 0;
  
  public boolean TopSensorValue = false;
  public boolean MiddleSensorValue = false;
  public boolean BottomSensorValue = false;
  
  public Conveyor() {
    Agitator.setNeutralMode(NeutralMode.Coast);
  }

  /**
   * Cotinually updates the class variables storing sensor values and
   * the number of balls in the conveyor.
   */
  public void periodic() {

    FindNumberOfBalls();
    
    UpdateTopSensorValue();
    UpdateMiddleSensorValue();
    UpdateBottomSensorValue();

  }

  public void UpdateTopSensorValue() {
  
    if (topLightSensor.getVoltage() > 0.6) {
      TopSensorValue = true;
    } else {
      TopSensorValue = false;
    }
  
  }

  public void UpdateMiddleSensorValue(){
  
    if(middleLightSensor.getVoltage() < 0.15) {
      MiddleSensorValue = true;
    } else {
      MiddleSensorValue = false;
    }
  
  }

  public void UpdateBottomSensorValue() {
  
    if (bottomLightSensor.getVoltage() < 0.15) {
      BottomSensorValue = true;
    } else {
      BottomSensorValue = false;
    }
  
  }

  boolean previousTopSensorValue = false;
  boolean previousMiddleSensorValue = false;
  boolean previousBottomSensorValue = false;

  public void FindNumberOfBalls(){
    
    if(MiddleSensorValue != previousMiddleSensorValue) {
      
      if(MiddleSensorValue) {
        BallsIn += 1;
      }
      else {
        BallsIn -= 1;
      }

      previousMiddleSensorValue = MiddleSensorValue;

    }


    if(TopSensorValue != previousTopSensorValue) {
      
      if(TopSensorValue) {
        BallsIn += 1;
      }else {
        BallsIn -= 1;
      }

      previousTopSensorValue = TopSensorValue;

    }

    if(BottomSensorValue != previousBottomSensorValue) {
      
      if(BottomSensorValue) {
        BallsIn += 1;
      }else {
        BallsIn -= 1;
      }

      previousBottomSensorValue = BottomSensorValue;
    
    }
  }

  /**
   * Sets the voltage output of conveyor from the passed parameter
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


  public int getTopIndex() {

    if(TopSensorValue) {
      return 2;
    } else if (MiddleSensorValue) {
      return 1;
    } else if (BottomSensorValue) {
      return 0;
    } else {
      return -1;
    }
  
  }

  public int getBallsIn() {
    return BallsIn;
  }

  public boolean isThereAGap() {

    if (MiddleSensorValue && !BottomSensorValue) {
      return true;
    } else if (TopSensorValue && !MiddleSensorValue) {
      return true;
    }
    return false;
  
  }

  public void setAgitator(double Speed) {
    Agitator.set(Speed);
  }

}
