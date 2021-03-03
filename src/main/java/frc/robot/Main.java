package frc.robot;


import edu.wpi.first.wpilibj.RobotBase;

/**
 * Do not change this file.
 * 
 * This initializes the robot (starts the program).
 */
public final class Main {
  private Main() {}

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
