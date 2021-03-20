package frc.robot.subsystems.Shooter;

public class ZoneAnalogPosition {
    
    public Zones zone;
    public double AnalogTarget;
    public double voltage;
    
    public ZoneAnalogPosition(Zones p_zone, double p_AnalogTarget, double p_voltage) {
        zone = p_zone;
        AnalogTarget = p_AnalogTarget;
        voltage = p_voltage;
    }

    public boolean isEqualTo(Zones p_zone) {
        return (zone == p_zone);
    }

    public String toString() {
        return "" + zone + " : " + AnalogTarget + " : " + voltage; 
    }
}
