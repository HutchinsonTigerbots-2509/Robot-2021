package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Vision.FoundContour;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Vision.USBVision;

public class ProcessAndGrabBalls extends CommandBase {
  private USBVision sUsbVision;
  private Intake sIntake;
  private Drivetrain sDrivetrain;

  private FoundContour[] contours;

  // For selecting which ball to track
  private double leastY = 480;
  private double leastX = 640;

  public ProcessAndGrabBalls(USBVision pUsbVision, Intake pIntake, Drivetrain pDrivetrain) {
    sUsbVision = pUsbVision;
    sIntake = pIntake;
    sDrivetrain = pDrivetrain;

    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(sIntake);
  }

  public void execute() {

    // Updates contours list and runs pipeline
    contours = sUsbVision.getContours();

    // Selects the ball that we are going to track: the furthest away and
    // the leftmost ball
    for (FoundContour Contour : contours) {
      if (Contour.CenterY < leastY && Contour.CenterY > 0) {
        leastY = Contour.CenterY;
        if (Contour.CenterX < leastX && Contour.CenterX  > 0) {
          leastX = Contour.CenterX;
        }
      }
    }

    // Finds the ball that we are targeting and changes it to
    // being targeted
    for (FoundContour Contour : contours) {
      if (Contour.CenterX == leastX) {
        Contour.IsTargeted = true;
      } else {
        Contour.IsTargeted = false;
      }
    }


    // Turns on intake and drives towards targeted ball. Looks at center
    // of ball and turns left or right (so the targeted ball moves toward
    // the center)
    // TODO: See if we can combine the last two for loops
    sIntake.IntakeIn();

    for (FoundContour contour : contours) {
      if (contour.IsTargeted) {
        
        if (contour.CenterX < (640 / 2) - 20) {
          sDrivetrain.mDrive.driveCartesian(0.4, 0, -0.2);
        } 
        
        else if (contour.CenterX > (640 / 2) + 20) {
          sDrivetrain.mDrive.driveCartesian(0.4, 0, 0.2);
        } 
        
        else {
          sDrivetrain.mDrive.driveCartesian(0.5, 0, 0);
        }

      }
    }


    // DEBUGGING - Displays all values of Contours list
    // SmartDashboard.putNumber("Contours 0 X", contours[0].CenterX);
    // SmartDashboard.putNumber("Contours 0 Y", contours[0].CenterY);
    // SmartDashboard.putBoolean("Contours 0 IsTargeted", contours[0].IsTargeted);

    // SmartDashboard.putNumber("Contours 1 X", contours[1].CenterX);
    // SmartDashboard.putNumber("Contours 1 Y", contours[1].CenterY);
    // SmartDashboard.putBoolean("Contours 1 IsTargeted", contours[1].IsTargeted);

    // SmartDashboard.putNumber("Contours 2 X", contours[2].CenterX);
    // SmartDashboard.putNumber("Contours 2 Y", contours[2].CenterY);
    // SmartDashboard.putBoolean("Contours 2 IsTargeted", contours[2].IsTargeted);

    // SmartDashboard.putNumber("Contours 3 X", contours[3].CenterX);
    // SmartDashboard.putNumber("Contours 3 Y", contours[3].CenterY);
    // SmartDashboard.putBoolean("Contours 3 IsTargeted", contours[3].IsTargeted);

    // SmartDashboard.putNumber("Contours 4 X", contours[4].CenterX);
    // SmartDashboard.putNumber("Contours 4 Y", contours[4].CenterY);
    // SmartDashboard.putBoolean("Contours 4 IsTargeted", contours[4].IsTargeted);
  }

  // Not Needed
  public void initialize() {}
  public void end(boolean interrupted) {}
  public boolean isFinished() { return false; }
}