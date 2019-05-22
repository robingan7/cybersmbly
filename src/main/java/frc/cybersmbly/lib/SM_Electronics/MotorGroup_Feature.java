package frc.cybersmbly.lib.SM_Electronics;
public class MotorGroup_Feature{
    private double deadband;
    private double speed;
    private int direction;

    public MotorGroup_Feature(){
        deadband=0d;
        speed=1d;
        direction=1;
    }

    public void setDirection(int val){
        direction=val;
    } 

    public void setSpeed(double val){
        speed=val;
    } 

    public void setDeadband(double val){
        deadband=val;
    } 
    public int getDirection(){
        return direction;
    }
    public double getDeadband(){
        return deadband;
    }
    public double getSpeed(){
        return speed;
    }
}