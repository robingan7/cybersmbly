package frc.cybersmbly.lib.SM_Electronics;

import frc.cybersmbly.until.functions.SolenoidFunction;
import edu.wpi.first.wpilibj.Solenoid;

public class SM_Solenoid extends Solenoid implements SolenoidFunction{
    private String type="solenoid";
    public SM_Solenoid(int channel){
        super(channel);
    }
    public void turnOff(){
        super.set(false);
    }
    public void turnOn(){
        super.set(true);
    }
    public String getType(){
        return type;
    }
}