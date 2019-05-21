package frc.cybersmbly.lib.SM_Electronics;
import java.util.ArrayList;

import frc.cybersmbly.until.functions.*;

public class Multi_Solenoid implements SolenoidFunction{
private final ArrayList<SolenoidFunction> m_solenoid;
private static int instances;
private String name;
public Multi_Solenoid(String n, SolenoidFunction solenoid,SolenoidFunction... solenoids) {
    m_solenoid = new ArrayList<SolenoidFunction>();
  m_solenoid.add(solenoid);
  for (int i = 0; i < solenoids.length; i++) {
    m_solenoid.add(solenoids[i]);
    instances++;
  }
  instances++; 
  name=n;
}
public Multi_Solenoid(String n){
    name=n;
    m_solenoid = new ArrayList<SolenoidFunction>();
}
public void addSolenoid(SolenoidFunction addMotor){
    m_solenoid.add(addMotor);
}
public ArrayList<SolenoidFunction> getSolenoidList(){
    return m_solenoid;
}
public int numberOfSolenoid(){
    return instances;
}
public String getType(){
    return name;
}
public void turnOn(){
    for(SolenoidFunction sol: m_solenoid){
        sol.turnOn();
    }
}

public void turnOff(){
    for(SolenoidFunction sol: m_solenoid){
        sol.turnOff();
    }
}
}