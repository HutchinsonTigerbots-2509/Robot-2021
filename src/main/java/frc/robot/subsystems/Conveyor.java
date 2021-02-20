// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Conveyor Class for the conveyor mechanism on the robot.
 * <p> Constructor: {@link #Conveyor()}
 */
public class Conveyor extends SubsystemBase {

  //#region Variable Declaration
  private VictorSP ConveyorMotor = new VictorSP(Constants.kConveyorMotorID);

  private AnalogInput topLightSensor = new AnalogInput(Constants.kTopLightSensorID);
  private AnalogInput middleLightSensor = new AnalogInput(Constants.kMiddleLightSensorID);
  private AnalogInput bottomLightSensor = new AnalogInput(Constants.kBottomLightSensorID);

  private boolean canSensorMove = true;

  private int BallsIn = 0;
  private boolean previousTopSensorValue = false;
  private boolean previousMiddleSensorValue = false;
  private boolean previousBottomSensorValue = false;

  //#endregion
  
  /**
   * Creates a new conveyor object.
   */
  public Conveyor() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Sensor Move", canSensorMove);
    //SmartDashboard.putNumber("Balls seen", BallsIn);
    // System.out.println(topLightSensor.getVoltage());
    System.out.println(BallsIn);
    GetTopSensorValue();
    GetMiddleSensorValue();
    GetBottomSensorValue();

    // if (canSensorMove) {
    //   if (!GetTopSensorValue() && GetBottomSensorValue()) {
    //     ConveyorForward(0.8);
    //   } else if (GetBottomSensorValue()) {
    //     ConveyorForward(0.8);
    //   } else {
    //     StopMotors();
    //   }
    // }
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
    }
    if(GetTopSensorValue() != previousTopSensorValue){
      if(GetTopSensorValue()) {
        BallsIn += 1;
      }else {
        BallsIn -= 1;
      }
    }
    if(GetBottomSensorValue() != previousBottomSensorValue){
      if(GetBottomSensorValue()){
        BallsIn += 1;
      }else {
        BallsIn -= 1;
      }
    }
  }

  /**
   * Moves only the bottom conveyor portion at a designated speed.
   * @param speed
   */
  public void MoveConveyor (double Speed) {
    ConveyorMotor.set(Speed);
  }

  /**
   * Reverses the conveyor motors at full speed
   */


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
}
