package frc.robot.subsystems.Vision;


/**
 * Custom Class that keeps track of the features of the contours
 * found by the programs.
 */
public class FoundContour {
    public double CenterX;
    public double CenterY;
    public double Area;
    public boolean IsTargeted;

    public FoundContour(double pCenterX, double pCenterY, double pArea, boolean pIsTargeted) {
        CenterX = pCenterX;
        CenterY = pCenterY;
        Area = pArea;
        IsTargeted = pIsTargeted;
    }   
}