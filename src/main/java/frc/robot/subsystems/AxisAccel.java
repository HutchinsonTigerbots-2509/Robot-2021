package frc.robot.subsystems;

public class AxisAccel {
    private double curr = 0;
    private double positvemulti;
    private double negativemulti;
    private double min;
    private double max;
    private double error;

    public AxisAccel (double pmulti, double nmulti, double pmin, double pmax) {
        positvemulti = pmulti;
        negativemulti = nmulti;
        min = pmin;
        max = pmax;
    }

    public AxisAccel (double pmulti, double nmulti) {
        positvemulti = pmulti;
        negativemulti = nmulti;
        min = 0.1;
        max = 0.9;
    }

    public double periodic(double targ) {
        error = targ - curr;
        if (Math.abs(targ) < min + 0.2) {
            if (Math.abs(targ) < min) {
                curr = 0;
            } else if (targ < curr) {
                curr = -min;
            } else if (targ > curr) {
                curr = min;
            }
        } else if (targ < curr) {
            curr += negativemulti*(error);
        } else if (targ > curr) {
            curr += positvemulti*(error);
        } else {
            curr = targ;
        }

        if(curr > max) {
            curr = max;
        } else if (curr < -max) {
            curr = -max;
        }

        return curr;
    }
    
}