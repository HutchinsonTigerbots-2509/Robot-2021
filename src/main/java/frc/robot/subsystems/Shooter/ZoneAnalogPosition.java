package frc.robot.subsystems.Shooter;

public class ZoneAnalogPosition {
    
    public Zones zone;
    public double AnalogTarget;
    
    public ZoneAnalogPosition(Zones p_zone, double p_AnalogTarget) {
        zone = p_zone;
        AnalogTarget = p_AnalogTarget;
    }

    public boolean isEqualTo(Zones p_zone) {
        return (zone == p_zone);
    }

    public String toString() {
        return "" + zone + " : " + AnalogTarget; 
    }
}
