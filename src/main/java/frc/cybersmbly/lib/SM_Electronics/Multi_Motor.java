package frc.cybersmbly.lib.SM_Electronics;
import java.util.ArrayList;
import frc.cybersmbly.until.functions.MotorFunction;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.SendableBase;

public class Multi_Motor extends SendableBase implements SpeedController {
private final ArrayList<MotorFunction> m_speedControllers;
private static int instances;
private String name;
private boolean m_isInverted=false;
public Multi_Motor(String n,MotorFunction speedController,MotorFunction... speedControllers) {
  m_speedControllers = new ArrayList<MotorFunction>();
  m_speedControllers.add((MotorFunction)speedController);
  for (int i = 0; i < speedControllers.length; i++) {
    m_speedControllers.add(speedControllers[i]);
    instances++;
  }
  instances++; 
  name=n;
}
public Multi_Motor(String n){
    name=n;
    m_speedControllers = new ArrayList<MotorFunction>();
}
public void addMotor(MotorFunction addMotor){
    m_speedControllers.add(addMotor);
}
public ArrayList<MotorFunction> getMotorList(){
    return m_speedControllers;
}
public int numberOfMotors(){
    return m_speedControllers.size();
}
public String getType(){
    return name;
}

public String all_Motor_name(){
  String result=getName();
  for (MotorFunction speedController : m_speedControllers) {
    result+=" "+speedController.getNumber();
  }
  return result;
}

@Override
public void set(double speed) {
  for (SpeedController speedController : m_speedControllers) {
    speedController.set(speed);
  }
}

@Override
public double get() {
  if (m_speedControllers.size() > 0) {
    return m_speedControllers.get(0).get() * (m_isInverted ? -1 : 1);
  }
  return 0.0;
}

@Override
public void setInverted(boolean isInverted) {
  m_isInverted = isInverted;
}

@Override
public boolean getInverted() {
  return m_isInverted;
}

@Override
public void disable() {
  for (SpeedController speedController : m_speedControllers) {
    speedController.disable();
  }
}

@Override
public void stopMotor() {
  for (SpeedController speedController : m_speedControllers) {
    speedController.stopMotor();
  }
}

@Override
public void pidWrite(double output) {
  set(output);
}

@Override
public void initSendable(SendableBuilder builder) {
  builder.setSmartDashboardType("Speed Controller");
  builder.setActuator(true);
  builder.setSafeState(this::stopMotor);
  builder.addDoubleProperty("Value", this::get, this::set);
}
}