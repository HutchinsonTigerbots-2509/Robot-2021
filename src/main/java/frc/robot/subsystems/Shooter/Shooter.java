package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;


public class Shooter extends SubsystemBase {
  
  private WPI_TalonFX mShooterMotor = new WPI_TalonFX(Constants.kShooterMotorID);
  private WPI_TalonSRX Slider = new WPI_TalonSRX(8);

  private AnalogInput Potentiometer = new AnalogInput(3);

  private static Zones SelectedZone = Zones.POWER_YELLOW; // Green for nomral

  private static double TargetVoltage = 0;
  private static double CurrentVoltage = 0;
  private static double CurrentProfileTime = 0;
  private static double IdealTime = 5;
  private static double MaxSpeed = 1;
  private static double ProfileSlope = MaxSpeed / IdealTime;

  private ZoneAnalogPosition[] ZonesList = new ZoneAnalogPosition[6];

  public Shooter() {
    
    mShooterMotor.setNeutralMode(NeutralMode.Coast);

    // What the values stand for ->       Zone          Analog Value   Motor Volts
    ZonesList[0] = new ZoneAnalogPosition(Zones.GREEN,  3.02,          0.50); // 1
    ZonesList[1] = new ZoneAnalogPosition(Zones.YELLOW, 3.10,          0.92);
    ZonesList[2] = new ZoneAnalogPosition(Zones.BLUE,   3.67,          0.80);
    ZonesList[3] = new ZoneAnalogPosition(Zones.RED,    3.74,          0.68);
    ZonesList[4] = new ZoneAnalogPosition(Zones.PARTY,  3.80,          1.00);
    ZonesList[5] = new ZoneAnalogPosition(Zones.POWER_YELLOW, 3.10, 0.95);

  }

  public void periodic() {

    switch(SelectedZone) {
    
      case GREEN: 
          MoveFlapTo(ZonesList[0]);
          break;
          
      case YELLOW:
          MoveFlapTo(ZonesList[1]);
          break;

      case BLUE:
          MoveFlapTo(ZonesList[2]);
          break;

      case RED:
          MoveFlapTo(ZonesList[3]);
          break;

      case PARTY:
          MoveFlapTo(ZonesList[4]);
          break;

      case POWER_YELLOW:
          MoveFlapTo(ZonesList[5]);
          break;
      default:
          MoveFlapTo(ZonesList[2]);
    }

    if (TargetVoltage == CurrentVoltage) {
      CurrentVoltage = TargetVoltage;
    } else if (TargetVoltage < CurrentVoltage) {
      CurrentProfileTime = CurrentVoltage / ProfileSlope;
      CurrentVoltage = ProfileSlope * (CurrentProfileTime - 0.02);
    } else if (TargetVoltage > CurrentVoltage) {
      CurrentProfileTime = CurrentVoltage / ProfileSlope;
      CurrentVoltage = ProfileSlope * (CurrentProfileTime + 0.02);
    }

    mShooterMotor.set(CurrentVoltage);
    
    SmartDashboard.putNumber("Shooter Amps", getShooterAmps());
    SmartDashboard.putNumber("Shooter RPM", GetRPM());
  }

  public void setTargetVoltage(double target) {
    TargetVoltage = target;
  }

  /**
   * Gets the raw quadrature encoder units from the TalonFX integrated sensor. It will 
   * convert the returned value to RPM.
   * 
   * @return  RPM
   */
  public double GetRPM() {
    //               600
    return Math.abs((1 * mShooterMotor.getSelectedSensorVelocity()) / 1);//Constants.kShooterTicksPerRevolution);
  }

  /**
   * Sets the voltage output of the motor from the passed parameter
   * 
   * @param Speed range from -1 to 1
   */
  public void RunShooter(double Speed) {
    mShooterMotor.set(Speed);
  }

  /**
   * 
   * @return
   */
  public double GetMotorOutputPercent() {
    return mShooterMotor.getMotorOutputVoltage();
  }

  public double getShooterAmps() {
    return mShooterMotor.getSupplyCurrent();
  }

  /**
   * 
   * @param zone
   */
  private void MoveFlapTo(ZoneAnalogPosition zone) {
    // If Flap is too far forward,                        then go backward
    if(Potentiometer.getVoltage() > zone.AnalogTarget+0.01) {  Slider.set(0.5);  } 

    //   If Flap is too far backward                           then go forward
    else if(Potentiometer.getVoltage() < zone.AnalogTarget-0.01) {  Slider.set(-0.5);  }

    // Otherwise Step
    else {  Slider.set(0);  }

    if (TargetVoltage != 0) {
      TargetVoltage = zone.voltage;
    }
  }

  public void setFlapToGreen()    {   SelectedZone = Zones.GREEN;  }

  public void setFlapToYellow()   {   SelectedZone = Zones.YELLOW; }

  public void setFlapToBlue()     {   SelectedZone = Zones.BLUE;   }

  public void setFlapToRed()      {   SelectedZone = Zones.RED;    }

  public void PARTYTIME()         {   SelectedZone = Zones.PARTY;  }

  public void setToPowerYellow()   {   SelectedZone = Zones.POWER_YELLOW;   }
}
