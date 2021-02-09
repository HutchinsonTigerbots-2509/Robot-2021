// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Vision Subsystem.
 * 
 * <p>The Vision subsystems accesses the Limelight Network Table and contains methods to retrieve those values.
 * It is also responsible for any pipeline changes and anything else regarding the Limelight camera.
 * 
 * @version 1.0.0 February 6, 2021
 * @author Cece
 */
public class Vision extends SubsystemBase {
  // The Network Table contains all values relevant to working with vision
  private NetworkTable mLimelightTable = NetworkTableInstance.getDefault().getTable(Constants.kLimelightNetworkID);

  // Network Table entry variables to hold data in the form of a NetworkTableEntry
  private NetworkTableEntry mTableX = mLimelightTable.getEntry(Constants.kLimelightTargetXID);
  private NetworkTableEntry mTableY = mLimelightTable.getEntry(Constants.kLimelightTargetYID);
  private NetworkTableEntry mTableTargetFound = mLimelightTable.getEntry(Constants.kLimelightTargetID);
  //Unused Network Table Values
  // private NetworkTableEntry mTableSkew = mLimelightTable.getEntry(Constants.kLimelightTargetSkewID);
  // private NetworkTableEntry mTableArea = mLimelightTable.getEntry(Constants.kLimelightTargetAreaID);
  // private NetworkTableEntry mTableVert = mLimelightTable.getEntry(Constants.kLimelightTargetVertID);
  // private NetworkTableEntry mTableHor = mLimelightTable.getEntry(Constants.kLimelightTargetHorID);
  //private NetworkTableEntry mTableCorners = mLimelightTable.getEntry("tcornx");

  // Variables to hold Network Table values in the form of Doubles
  private double mTargetX = 0;
  private double mTargetY = 0;
  private double mTargetFound = 0;
  //Unused Values
  // private double mTargetSkew = 0;
  // private double mTargetArea = 0;
  // private double mTargetVert = 0;
  // private double mTargetHor = 0;


  /**
   * Vision Constructor.
   * 
   * <p>Contains all information for the Limelight Networktable and it's values
   */
  public Vision() {
    //Sets default values (check Constants variable descriptions for more info)
    mLimelightTable.getEntry("ledMode").setNumber(Constants.kLimelightLED);
    mLimelightTable.getEntry("camMode").setNumber(Constants.kLimelightMode);
    mLimelightTable.getEntry("stream").setNumber(Constants.kLimelightStream);
    mLimelightTable.getEntry("pipeline").setNumber(Constants.kLimelightStartingPipeline);
  }

  @Override
  public void periodic() {
    //Prints Vision values to the SmartDashboard
    SmartDashboard.putNumber("Target X", getTargetX());
    SmartDashboard.putNumber("Target Y", getTargetY());

    //Unused Values
    // SmartDashboard.putNumber("Target Area", getTargetArea());
    // SmartDashboard.putNumber("Target Skew", getTargetSkew());
    // SmartDashboard.putNumber("Target Vert", getTargetVert());
    // SmartDashboard.putNumber("Target Hor", getTargetHor());
  }

  // ***** VISION VALUE METHODS ***** //

  // Returns the horizontal offset from crosshair to target (+/- 27 degrees)
  public double getTargetX() {
    mTableX = mLimelightTable.getEntry(Constants.kLimelightTargetXID); 
    mTargetX = mTableX.getDouble(0.0); 
    return mTargetX; 
  }

  // Returns the vertical offset from crosshair to target (+/- 20.5 degrees)
  public double getTargetY() {
    mTableY = mLimelightTable.getEntry(Constants.kLimelightTargetYID);
    mTargetY = mTableY.getDouble(0.0);
    return mTargetY;
  }

  public boolean GetTargetFound(){
    mTableTargetFound = mLimelightTable.getEntry(Constants.kLimelightTargetID);
    mTargetFound = mTableTargetFound.getDouble(0.0);
    if(mTargetFound == 1){
      return true;
    }else{
      return false;
    }
  }

  // ***** UNUSED VISION VALUE METHODS ***** //

  // Returns target area (0-100 % of image)
  // public double getTargetArea() {
  //   mTableArea = mLimelightTable.getEntry(Constants.kLimelightTargetAreaID);
  //   mTargetArea = mTableArea.getDouble(0.0);
  //   return mTargetArea;
  // }

  // // Returns the target skew/rotation (-90 to 0 degrees)
  // public double getTargetSkew() {
  //   mTableSkew = mLimelightTable.getEntry(Constants.kLimelightTargetSkewID);
  //   mTargetSkew = mTableSkew.getDouble(0.0);
  //   return mTargetSkew;
  // }

  // // Returns the vertical sidelength of the bounding box (0-320 pixels)
  // public double getTargetVert() {
  //   mTableVert = mLimelightTable.getEntry(Constants.kLimelightTargetVertID);
  //   mTargetVert = mTableVert.getDouble(0.0);
  //   return mTargetVert;
  // }

  // // Returns the horizontal sidelength of the bounding box (0-320 pixels)
  // public double getTargetHor() {
  //   mTableHor = mLimelightTable.getEntry(Constants.kLimelightTargetHorID);
  //   mTargetHor = mTableHor.getDouble(0.0);
  //   return mTargetHor;
  // }

  // ***** VISION PIPELINE METHODS ***** //

  // Changes the pipeline to a specified pipeline
  public void SwitchPipeline(int pipeline){
    mLimelightTable.getEntry("pipeline").setNumber(pipeline);
  }
}