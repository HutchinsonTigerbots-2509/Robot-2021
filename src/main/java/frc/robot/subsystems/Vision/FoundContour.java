package frc.robot.subsystems.Vision;


/**
 * Custom Class that keeps track of the features of the contours
 * found by the programs.
 */
public class FoundContour {
    public int CenterX;
    public int CenterY;

    public FoundContour(int pCenterX, int pCenterY) {
        CenterX = pCenterX;
        CenterY = pCenterY;
    } 
    
    public FoundContour() {
        CenterX = -1;
        CenterY = -1;
    }   

    public void setX(int x) {
        CenterX = x;
    }

    public void setY(int y) {
        CenterY = y;
    }

    public int getX() {
        return CenterX;
    }

    public int getY() {
        return CenterY;
    }
}