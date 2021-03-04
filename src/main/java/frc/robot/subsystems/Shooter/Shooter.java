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

  private Zones SelectedZone = Zones.REINTRODUCTION_ZONE;

  private ZoneAnalogPosition[] ZonesList = new ZoneAnalogPosition[4];

  public Shooter() {
    
    mShooterMotor.setNeutralMode(NeutralMode.Coast);

    //                                    Zone          Analog Value
    ZonesList[0] = new ZoneAnalogPosition(Zones.GREEN,  2.85);
    ZonesList[1] = new ZoneAnalogPosition(Zones.YELLOW, 3.00);
    ZonesList[2] = new ZoneAnalogPosition(Zones.BLUE,   3.60);
    ZonesList[3] = new ZoneAnalogPosition(Zones.RED,    3.70);

  }

  public void periodic() {

    switch(SelectedZone) {
    
      case GREEN:  MoveFlapTo(ZonesList[0]);

      case YELLOW: MoveFlapTo(ZonesList[1]);
      
      case BLUE:   MoveFlapTo(ZonesList[2]);
      
      case RED:    MoveFlapTo(ZonesList[3]);
      
      default:     Slider.set(0);
    
    }
    
    // SmartDashboard.putNumber("Shooter RPM", GetRPM());
    SmartDashboard.putNumber("Potent", Potentiometer.getVoltage());
  }

  /**
   * Gets the raw quadrature encoder units from the TalonFX integrated sensor. It will 
   * convert the returned value to RPM.
   * 
   * @return  RPM
   */
  public double GetRPM() {
    return Math.abs((600 * mShooterMotor.getSelectedSensorVelocity()) / Constants.kShooterTicksPerRevolution);
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

  /**
   * 
   * @param zone
   */
  private void MoveFlapTo(ZoneAnalogPosition zone) {
    // If Flap is too far forward,                        then go backward
    if(Potentiometer.getVoltage() > zone.AnalogTarget) {  Slider.set(0.5);  } 

    //   If Flap is too far backward                           then go forward
    else if(Potentiometer.getVoltage() < zone.AnalogTarget) {  Slider.set(-0.5);  }

    // Otherwise Stop
    else {  Slider.set(0);  }
  }

  public void setFlapToGreen()    {   SelectedZone = Zones.GREEN;  }

  public void setFlapToYellow()   {   SelectedZone = Zones.YELLOW; }

  public void setFlapToBlue()     {   SelectedZone = Zones.BLUE;   }

  public void setFlapToRed()      {   SelectedZone = Zones.RED;    }
}
