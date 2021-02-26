// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Conveyor Class for the conveyor mechanism on the robot.
 * <p> Constructor: {@link #Conveyor()}
 */
public class Conveyor extends SubsystemBase {

  //#region Variable Declaration
  private VictorSP ConveyorMotor = new VictorSP(Constants.kConveyorMotorID);
  private WPI_TalonSRX Agitator = new WPI_TalonSRX(Constants.kAgitatorID);

  private AnalogInput topLightSensor = new AnalogInput(Constants.kTopLightSensorID);
  private AnalogInput middleLightSensor = new AnalogInput(Constants.kMiddleLightSensorID);
  private AnalogInput bottomLightSensor = new AnalogInput(Constants.kBottomLightSensorID);

  private boolean canSensorMove = true;

  private int BallsIn = 0;
  
  private boolean previousTopSensorValue = false;
  private boolean previousMiddleSensorValue = false;
  private boolean previousBottomSensorValue = false;

  private AnalogInput[] listOfSensors = new AnalogInput[3];
  //#endregion
  
  /**
   * Creates a new conveyor object.
   */
  public Conveyor() {
    listOfSensors[0] = bottomLightSensor;
    listOfSensors[1] = middleLightSensor;
    listOfSensors[2] = topLightSensor;
    Agitator.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Sensor Move", canSensorMove);
    FindNumberOfBalls();
    //System.out.println(BallsIn);
    GetTopSensorValue();
    GetMiddleSensorValue();
    GetBottomSensorValue();
  }

  /**
   * Gets the bottom sensor value and places it on the smart
   * dashboard and returns that value.
   * @return true/false
   */
  public boolean GetBottomSensorValue() {
    if (bottomLightSensor.getVoltage() < 0.15) {
      SmartDashboard.putBoolean("Bottom Sensor", true);
      return true;
    } else {
      SmartDashboard.putBoolean("Bottom Sensor", false);
      return false;
    }
  }

  /**
   * Gets the top sensor value and places it on the smart
   * dashboard and returns that value.
   * @return true/false
   */
  public boolean GetTopSensorValue() {
    if (topLightSensor.getVoltage() > 0.6) {
      SmartDashboard.putBoolean("Top Sensor", true);
      return true;
    } else {
      SmartDashboard.putBoolean("Top Sensor", false);
      return false;
    }
  }

  public boolean GetMiddleSensorValue(){
    if(middleLightSensor.getVoltage() < 0.15) {
      SmartDashboard.putBoolean("Middle Sensor", true);
      return true;
    }else {
      SmartDashboard.putBoolean("Middle Sensor", false);
      return false;
    }
  }

  public void FindNumberOfBalls(){
    if(GetMiddleSensorValue() != previousMiddleSensorValue){
      if(GetMiddleSensorValue()){
        BallsIn += 1;
      }else {
        BallsIn -= 1;
      }
      previousMiddleSensorValue = GetMiddleSensorValue();
    }
    if(GetTopSensorValue() != previousTopSensorValue){
      if(GetTopSensorValue()) {
        BallsIn += 1;
      }else {
        BallsIn -= 1;
      }
      previousTopSensorValue = GetTopSensorValue();
    }
    if(GetBottomSensorValue() != previousBottomSensorValue){
      if(GetBottomSensorValue()){
        BallsIn += 1;
      }else {
        BallsIn -= 1;
      }
      previousBottomSensorValue = GetBottomSensorValue();
    }
  }

  /**
   * Moves only the bottom conveyor portion at a designated speed.
   * @param speed
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

  /**
   * Stops the conveyor motors
   */
  public void StopMotors() {
    ConveyorMotor.set(0);
  }

  /**
   * Toggles the value of canSensorMove
   */
  public void ToggleSensor() {
    canSensorMove = !canSensorMove;
  }

  public int getTopIndex() {
    if(GetTopSensorValue()) {
      return 2;
    } else if (GetMiddleSensorValue()) {
      return 1;
    } else if (GetBottomSensorValue()) {
      return 0;
    } else {
      return -1;
    }
  }

  public int getBallsIn() {
    return BallsIn;
  }

  public boolean isGap() {
    if (GetMiddleSensorValue() && !GetBottomSensorValue()) {
      return true;
    } else if (GetTopSensorValue() && !GetMiddleSensorValue()) {
      return true;
    }
    return false;
  }

  public void setAgitator(double Speed) {
    Agitator.set(Speed);
  }
}
