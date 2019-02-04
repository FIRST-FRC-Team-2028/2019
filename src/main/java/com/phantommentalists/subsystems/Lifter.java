/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.phantommentalists.Parameters;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lifter lifts the back-end of the robot so that it can reach the Platform
 * It uses a motor to drive the rack and pinion to lift
 * It uses a motor to turn the wheels that pushes the robot forward
 */
public class Lifter extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX lift;
  TalonSRX drive;
  
  public Lifter() {
    lift = new TalonSRX(Parameters.CanId.LIFTER_LIFT_MOTOR.getCanId());
    drive = new TalonSRX(Parameters.CanId.LIFTER_DRIVE_MOTOR.getCanId());
  }

  public void retract(){

  }

  public void level1(){

  }

  public void level2(){

  }

  public void drive(){
    
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
