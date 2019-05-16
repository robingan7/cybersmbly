package frc.cybersmbly.lib.SM_Electronics;

import frc.cybersmbly.until.functions.SolenoidFunction;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SM_DoubleSolenoid extends DoubleSolenoid implements SolenoidFunction{
    private String type="doublesolenoid";
    public SM_DoubleSolenoid(int channel,int channel2){
        super(channel,channel2);
    }
    public void turnOff(){
        super.set(Value.kReverse);
    }
    public void turnOn(){
        super.set(Value.kForward);
    }

    public String getType(){
        return type;
    }
}