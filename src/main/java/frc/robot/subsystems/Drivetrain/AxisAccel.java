package frc.robot.subsystems.Drivetrain;

/**
 * Generic Axis with voltage output class. Uses a Proportional gain (passed
 * as a constructor parameter) and if statements to accelerate the robot.
 */
public class AxisAccel {

    private double curr = 0;
    
    private double positvemulti;
    private double negativemulti;
    
    private double min;
    private double max;
    
    private double error;

    /** 
     * @param pmulti - proportional constant in the postive direction
     * @param nmulti - proportional constant in the negative direction
     * @param pmin - minimum allowed voltage for the motor, range -1 to 1
     * @param pmax - maximum allowed voltage for the motor, range -1 to 1
     */
    public AxisAccel (double pmulti, double nmulti, double pmin, double pmax) {
        positvemulti = pmulti;
        negativemulti = nmulti;
        min = pmin;
        max = pmax;
    }

    /**
     * Defaults your max voltage to 0.9 and min voltage to 0.1
     * 
     * @param pmulti - proportional constant in the postive direction
     * @param nmulti - proportional constant in the negative direction
     */
    public AxisAccel (double pmulti, double nmulti) {
        positvemulti = pmulti;
        negativemulti = nmulti;
        min = 0.1;
        max = 0.9;
    }

    /**
     * Call this to run continously. It will take a target in and spit out
     * the voltage the motor should go at. 
     * 
     * @param targ Range: -1 to 1
     * @return motor voltage, range -1 to 1
     */
    public double periodic(double targ) {
        
        error = targ - curr;
        
        // Will check if voltage is below threshold. If the setpoint on the 
        // joystick is too small, the output will be set to zero. If it is
        // within 0.2 of the passed min, the program will set the target to
        // the minimum + 0.2.
        if (Math.abs(targ) < min + 0.1) {
            if (Math.abs(targ) < min) {
                curr = 0;
            } else if (targ < curr) {
                curr = -min - 0.1;
            } else if (targ > curr) {
                curr = min + 0.1;
            }
        } else if (targ < curr) {
            curr += negativemulti*(error); // porportional gain
        } else if (targ > curr) {
            curr += positvemulti*(error); // proportional gain
        } else {
            // if the joystick and output are the same then,
            curr = targ;
        }

        // Makes sure we don't go beyond our maxes (the program shouldn't, but
        // just in case)
        if(curr > max) {
            curr = max;
        } else if (curr < -max) {
            curr = -max;
        }

        return curr;
    }
    
}