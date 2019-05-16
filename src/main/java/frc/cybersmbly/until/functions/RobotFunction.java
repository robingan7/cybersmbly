/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.cybersmbly.until.functions;

import frc.cybersmbly.lib.SM_Subsystem.SM_Robot.Electronics;

/**
 * Add your docs here.
 */
public interface RobotFunction {
  void addStuff(String name, final Electronics e, int ch);
  void setInverse(String name,boolean isInverted);
  void addStuff(String name, final Electronics e, int ch,int ch2);
}
