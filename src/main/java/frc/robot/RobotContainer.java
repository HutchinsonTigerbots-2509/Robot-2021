package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

/**
 * We declare a majority of our robot here. This includes
 * 
 * <p>
 *  <li> - Button Bindings for TeleOp
 *  <li> - Joysticks
 *  <li> - Subsystems
 *  <li> - Autonomous Commands, as well as selecting the preferred one
 * </p>
 * 
 * The RobotContainer class is a neat way to store all of the objects concerning your
 * robot. This is also where the magic of the code happens. It connects the code that
 * you wrote into how it actually functions with the robot, whether a through a button 
 * or an autonomous command.
 */
public class RobotContainer {
  
  // Subsystems
  public Drivetrain sDrivetrain = new Drivetrain();
  private Shooter sShooter = new Shooter();
  private Conveyor sConveyor = new Conveyor();
  private Intake sIntake = new Intake();

  // Joysticks
  public static Joystick OpStick = new Joystick(Constants.kOpStickID);
  public static Joystick CoOpStick = new Joystick(Constants.kCoOpStickID);


  // Joystick Buttons
  private JoystickButton SwitchMode;
  private JoystickButton RampUpShooter;
  private JoystickButton RampDownShooter;
  private JoystickButton ConveyorUp;
  private JoystickButton ConveyorDown;
  private JoystickButton IntakeIn;
  private JoystickButton IntakeOut;
  private JoystickButton AgitatorTrigger;
  private JoystickButton ExtendIntake;
  private JoystickButton RetractIntake;
  private JoystickButton AgitatorTrigger2;

  private JoystickButton SliderGreen;
  private JoystickButton SliderYellow;
  private JoystickButton SliderBlue;
  private JoystickButton SliderRed;
  
  /**
   * This constructor calls configureButtonBindings(). When that method runs, 
   * it will assign commands to buttons on the joystick. Create an instance
   * of RobotContainer in Robot.java to use. 
   */
  public RobotContainer() {
    configureButtonBindings();
  }

  /**
   * This method is where you connect commands to buttons on
   * the joysticks.
   * 
   * <p>
   * OpStick is generally reserved for driving the robot around, and other
   * rarely used buttons.
   * 
   * <p>
   * CoOpStick is used for all of the commands that are used a lot during the
   * round. 
   */
  private void configureButtonBindings() {
      /* OpStick Buttons */
      SwitchMode = new JoystickButton(OpStick, 9);
      SwitchMode.whenPressed(new InstantCommand(() -> sDrivetrain.SwitchMode()));

      IntakeIn = new JoystickButton(OpStick, Constants.kXboxRightBumper);
      IntakeIn.whileHeld(new RunCommand(() -> sIntake.IntakeIn()));
      IntakeIn.whenReleased(new InstantCommand(() -> sIntake.IntakeStop())); 
      
      IntakeOut = new JoystickButton(OpStick, Constants.kXboxLeftBumper);
      IntakeOut.whileHeld(new RunCommand(() -> sIntake.IntakeOut()));
      IntakeOut.whenReleased(new InstantCommand(() -> sIntake.IntakeStop()));

      ExtendIntake = new JoystickButton(OpStick, Constants.kXboxButtonA);
      ExtendIntake.whenPressed(new InstantCommand(() -> sIntake.Extend()));

      RetractIntake = new JoystickButton(OpStick, Constants.kXboxButtonX);
      RetractIntake.whenPressed(new InstantCommand(() -> sIntake.Retract()));

      AgitatorTrigger = new JoystickButton(OpStick, Constants.kXboxButtonY);
      AgitatorTrigger.whenPressed(new InstantCommand(() -> sConveyor.setAgitator(-0.5)));
      AgitatorTrigger.whenReleased(new InstantCommand(() -> sConveyor.setAgitator(0)));

      AgitatorTrigger2 = new JoystickButton(OpStick, Constants.kXboxButtonB);
      AgitatorTrigger2.whenPressed(new InstantCommand(() -> sConveyor.setAgitator(0.5)));
      AgitatorTrigger2.whenReleased(new InstantCommand(() -> sConveyor.setAgitator(0)));
      
      /* CoOpStick Buttons */
      // Shooter
      RampUpShooter = new JoystickButton(CoOpStick, Constants.kXboxRightBumper);
      RampUpShooter.whenPressed(new InstantCommand(() -> sShooter.setTargetVoltage(0.7)));

      RampDownShooter = new JoystickButton(CoOpStick, Constants.kXboxLeftBumper);
      RampDownShooter.whenPressed(new InstantCommand(() -> sShooter.setTargetVoltage(0)));
      

      // Slider
      // SliderBlue = new JoystickButton(CoOpStick, Constants.kXboxButtonX);
      // SliderBlue.whenPressed(new InstantCommand(() -> sShooter.setFlapToBlue()));

      SliderYellow = new JoystickButton(CoOpStick, Constants.kXboxButtonY);
      SliderYellow.whenPressed(new InstantCommand(() -> sShooter.setFlapToYellow()));

      SliderGreen = new JoystickButton(CoOpStick, Constants.kXboxButtonA);
      SliderGreen.whenPressed(new InstantCommand(() -> sShooter.setFlapToGreen()));

      // SliderRed = new JoystickButton(CoOpStick, Constants.kXboxButtonB);
      // SliderRed.whenPressed(new InstantCommand(() -> sShooter.setFlapToRed()));

      // JoystickButton partybutton = new JoystickButton(CoOpStick, Constants.kXboxButtonA);
      // partybutton.whenPressed(new InstantCommand(() -> sShooter.PARTYTIME()));

      // Conveyor
      ConveyorUp = new JoystickButton(CoOpStick, 10);
      ConveyorUp.whenPressed(new InstantCommand(() -> sConveyor.setConveyor(1)));
      ConveyorUp.whenReleased(new InstantCommand(() -> sConveyor.setConveyor(0)));

      ConveyorDown = new JoystickButton(CoOpStick, 9);
      ConveyorDown.whenPressed(new InstantCommand(() -> sConveyor.setConveyor(-1))); // .70
      ConveyorDown.whenReleased(new InstantCommand(() -> sConveyor.setConveyor(0)));
  }
}
