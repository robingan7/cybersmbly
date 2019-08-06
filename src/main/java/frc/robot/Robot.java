package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.cybersmbly.lib.SM_Subsystem.*;
import frc.cybersmbly.lib.SM_Subsystem.SM_Robot.*;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {

    SM_Robot.add("right", Electronics.talon, 11);
    SM_Robot.add("right", Electronics.victor, 13);
    SM_Robot.add("right", Electronics.victor, 15);

    SM_Robot.add("left", Electronics.talon, 12);
    SM_Robot.add("left", Electronics.victor, 14);
    SM_Robot.add("left", Electronics.victor, 16);
    
    SM_Robot.set_West_Coast_Drive("right", "left");

    SM_Robot.configure_drivebase_deadband(0.3);
    //SM_Robot.configure_drivebase_speed(0);

    SM_Robot.add("arm",Electronics.talon,18);
    SM_Robot.add("arm",Electronics.victor,19);

    SM_Robot.setDeadband("arm", 0.15);
    SM_Robot.setSpeed("arm", 0.5);

  }
  @Override
  public void robotPeriodic() {}
  @Override
  public void autonomousInit() {}

  @Override
  public void teleopInit() {}
  @Override
  public void autonomousPeriodic() {
    SM_Robot.arcadeDrive(JoyStick.joystick1, Axis.axis1, Axis.axis2);
    SM_Robot.movePercent("arm", JoyStick.joystick2, Axis.axis1);
  }
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void disabledInit(){}
}
