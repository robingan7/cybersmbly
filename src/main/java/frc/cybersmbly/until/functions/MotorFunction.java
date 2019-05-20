package frc.cybersmbly.until.functions;
import edu.wpi.first.wpilibj.SpeedController;

/*
*Method for motorgroup
*{@link Multi_Motor}
*/
public interface MotorFunction extends SpeedController {
    String getType();
    void movePercentage(double d);
    void moveDistance(double d);
    void setInverted(boolean b);
    int getNumber();
    MotorFunction make_motor(String name, int channelnumber);
}