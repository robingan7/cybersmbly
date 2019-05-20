package frc.cybersmbly.lib.SM_Electronics;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.SpeedController;
import frc.cybersmbly.until.functions.MotorFunction;

public class Single_Motor_Talon extends com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX implements MotorFunction,SpeedController{
    private String motortype;
    private int channelnumber;
    public Single_Motor_Talon(String type, int channelnumber){
        super(channelnumber);
        motortype=type;
        this.channelnumber=channelnumber;
    }

    public Single_Motor_Talon(){
        super(-1);
    }

    public Single_Motor_Talon make_motor(String type, int channelnumber){
        return new Single_Motor_Talon(type,channelnumber);
    }
    public String getType(){
        return motortype;
    }
    public int getNumber(){
        return channelnumber;
    }
    public void movePercentage(double percent){
        super.set(ControlMode.PercentOutput, percent);
    }
    public void moveDistance(double distanceToDrive){
        super.set(ControlMode.MotionMagic, distanceToDrive);
    }
    public void setInverted(boolean b){
        super.setInverted(b);
    }
}
