package frc.robot.commands.Shooter;

public class LinearRampProfile {
    
    private double beginning_volts;
    private double beginning_time; 
    private double end_volt; 
    private double end_time;

    private double slope;


    public LinearRampProfile(double p_beginning_volts, double p_beginning_time, 
                             double p_end_volt, double p_end_time) {
        
        beginning_volts = p_beginning_volts;
        beginning_time = p_beginning_time;
        end_volt = p_end_volt;
        end_time = p_end_time;

        slope = (end_volt - beginning_volts) / (end_time - beginning_time);
    
    }

    public double getVolt(double current_time) {
        return slope*(current_time) + beginning_volts;
    }
}
