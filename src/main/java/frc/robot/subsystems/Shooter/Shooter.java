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

  private static Zones SelectedZone = Zones.GREEN; // Green for nomral

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
    ZonesList[0] = new ZoneAnalogPosition(Zones.GREEN,  2.7,          .43); // 3.02 
    ZonesList[1] = new ZoneAnalogPosition(Zones.YELLOW, 3.115,          0.92);
    ZonesList[2] = new ZoneAnalogPosition(Zones.BLUE,   3.67,          0.8);
    ZonesList[3] = new ZoneAnalogPosition(Zones.RED,    3.74,          0.8); 
    ZonesList[4] = new ZoneAnalogPosition(Zones.PARTY,  3.80,          1.00);
    
    ZonesList[5] = new ZoneAnalogPosition(Zones.POWER_YELLOW, 3.10, 0.95);

  }

  private double outputvoltage = 0;

  public void periodic() {

    switch(SelectedZone) {
    
      case GREEN: 
          MoveFlapTo(ZonesList[0]);
          UpdateTargetVoltage(ZonesList[0]);
          break;
          
      case YELLOW:
          MoveFlapTo(ZonesList[1]);
          UpdateTargetVoltage(ZonesList[1]);
          break;

      case BLUE:
          MoveFlapTo(ZonesList[2]);
          UpdateTargetVoltage(ZonesList[2]);
          break;

      case RED:
          MoveFlapTo(ZonesList[3]);
          UpdateTargetVoltage(ZonesList[3]);
          break;

      case PARTY:
          MoveFlapTo(ZonesList[4]);
          UpdateTargetVoltage(ZonesList[4]);
          break;

      case POWER_YELLOW:
          MoveFlapTo(ZonesList[5]);
          UpdateTargetVoltage(ZonesList[5]);
          break;
      
      default:
          UpdateTargetVoltage(ZonesList[2]);
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

    if(CurrentVoltage >= 100) {
      outputvoltage = CurrentVoltage + convertToVolts(pController(getError(convertToTicks(CurrentVoltage)), 0.001));
    } else {
      outputvoltage = CurrentVoltage;
    }

    mShooterMotor.set(outputvoltage);
    
    // SmartDashboard.putNumber("Shooter Amps", getShooterAmps());
    SmartDashboard.putNumber("Shooter RPM", GetRPM());
    // SmartDashboard.putNumber("REEEE", convertToVolts(pController(0.000, getError(convertToTicks(CurrentVoltage)))));
    // SmartDashboard.putNumber("REEEE2", outputvoltage);
    // SmartDashboard.putString("State", SelectedZone.toString());
  }

  public void setTargetVoltage(double target) {
    TargetVoltage = target;
  }

  private double convertToTicks(double volts) {
    return (volts * 19640);
  }

  private double convertToVolts(double ticks) {
    return (ticks / 19640);
  }

  private double getError(double target) {
    return -(GetRPM() - convertToTicks(target));
  }

  private double pController(double kP, double error) {
    return kP * error;
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

  }

  /**
   * 
   * @param zone
   */
  private void UpdateTargetVoltage(ZoneAnalogPosition zone) {
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
