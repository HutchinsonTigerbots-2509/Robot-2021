package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Shooter extends SubsystemBase {
  
  private WPI_TalonFX mShooterMotor = new WPI_TalonFX(Constants.kShooterMotorID);


  /**
   * The color of zones for shooting during Autonomous
   */
  enum Zone {
                         // ROWS
    GREEN,               // 0 - 3 
    YELLOW,              // 3 - 5
    BLUE,                // 5 - 7
    RED,                 // 7 - 9
    REINTRODUCTION_ZONE  // 9 - END
  } 

  private Zone Current = Zone.REINTRODUCTION_ZONE;

  public Shooter() {
    mShooterMotor.setNeutralMode(NeutralMode.Coast);
  }

  public void periodic() {
    // This method will be called once per scheduler run





    // PICK ONE
    SmartDashboard.putNumber("Shooter RPM", GetRPM());
    SmartDashboard.putString("Shooter volts", Current.toString());
    //PICK ONE





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
  
  public double GetMotorOutputPercent() {
    return mShooterMotor.getMotorOutputPercent();
  }

  /**
   * Sets the voltage output of the motor from the passed parameter
   * 
   * @param Speed range from -1 to 1
   */
  public void RunShooter(double Speed) {
    mShooterMotor.set(Speed);
  }
}
