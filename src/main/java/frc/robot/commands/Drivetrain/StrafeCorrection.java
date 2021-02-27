package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class StrafeCorrection extends CommandBase {
  private Drivetrain sDrivetrain;
  private Vision sVision;
  private double CurrentTime;
  private double StartTime;
 
  private double TargVoltage;
  private double Voltage;

  private double rampUp_duration;
  private double total_duration;
  private double rampDown_duration;
  private double drive_duration;


  private int state;
  
  private boolean isFinished;
  
  /** Creates a new StrafeCorrection. */
  public StrafeCorrection(Drivetrain pDT, Vision pV, double pDuration, double pVoltage) {
    sDrivetrain = pDT;
    sVision = pV;

    total_duration = pDuration;

    if(total_duration < 0.5) {
      rampUp_duration = total_duration/2;
    } else {
      rampUp_duration = 0.25;
    }

    if(total_duration - rampUp_duration > (rampUp_duration / 2.0)){
      rampDown_duration = rampUp_duration / 2;
    } else {
      rampDown_duration = total_duration = rampUp_duration;
    }

    drive_duration = total_duration - rampUp_duration - rampDown_duration;

    TargVoltage = pVoltage;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sVision, sDrivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Voltage = 0;
    StartTime = Timer.getFPGATimestamp();
    CurrentTime = Timer.getFPGATimestamp();
    state = 0;
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //Runs when ramping up
    if(Voltage < TargVoltage && state == 0){
      Voltage = (TargVoltage / rampUp_duration) * (CurrentTime - StartTime);
      sDrivetrain.DriveWithStrafe(Voltage, 0, 0);
    } else if (state == 0) {
      state = 1;
      Voltage = TargVoltage;
      sDrivetrain.DriveWithStrafe(Voltage, 0, 0);
      StartTime = Timer.getFPGATimestamp();
    }

    //Runs when driving at a constant speed
    if(drive_duration > (CurrentTime - StartTime) && state == 1){
      sDrivetrain.DriveWithStrafe(Voltage, 0, 0);
    } else if (state == 1){
      state = 2;
      StartTime = Timer.getFPGATimestamp();
      sDrivetrain.DriveWithStrafe(Voltage, 0, 0);
    }

    //Runs when ramping down
    if(Voltage > 0 && state == 2){
      Voltage = -(TargVoltage / rampDown_duration) * (CurrentTime - StartTime) + TargVoltage;
      sDrivetrain.DriveWithStrafe(Voltage, 0, 0);
    } else if (state == 2){
      state = 3;
      isFinished = true;
      Voltage = 0;
      sDrivetrain.StopDrivetrain();
    }

    //Updates the current time
    CurrentTime = Timer.getFPGATimestamp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sDrivetrain.StopDrivetrain();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
