package frc.cybersmbly.until.functions;

/*
*Method for motorgroup
*{@link Multi_Motor}
*/
public interface SolenoidFunction{
    String getType();
    void turnOn();
    void turnOff();
}