package frc.robot.subsystems.Vision;


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