/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.phantommentalists.Parameters;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lifter lifts the back-end of the robot so that it can reach the Platform It
 * uses a motor to drive the rack and pinion to lift It uses a motor to turn the
 * wheels that pushes the robot forward
 * 
 * While lifting the elevator's lift bar will push down on the HAB ZONE LEVEL
 * 2/3 FLOOR raising the front of the robot. Simultanously this class will push
 * against the HAB ZONE LEVEL 1 FLOOR.
 * 
 * While climbing a gyro will measure the tilt of the robot and a PID controller
 * will speed up or slow down the lift motor in this class to keep the robot
 * level
 */
public class Lifter extends Subsystem implements PIDOutput {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX lift;
  TalonSRX drive;
  double tiltAdjustment;
  
  public Lifter() {
    lift = new TalonSRX(Parameters.CanId.LIFTER_LIFT_MOTOR.getCanId());
    lift.setInverted(Parameters.CanId.LIFTER_LIFT_MOTOR.isInverted());
    drive = new TalonSRX(Parameters.CanId.LIFTER_DRIVE_MOTOR.getCanId());
    drive.setInverted(Parameters.CanId.LIFTER_DRIVE_MOTOR.isInverted());
  }

  /**
   * It moves the lift mechanism up (which has the effect of lowering the robot
   * onto it's own wheels).  We always want to move the lifter up as fast as 
   * it can mechanically go.
   */
  public void retract() {
    lift.set(ControlMode.PercentOutput, Parameters.LIFTER_LIFT_MOTOR_RETRACT_SPEED);
  }

  /**
   * It moves the lift mechanism down (which has the effect of raising the robot
   * off it's own wheels). While the lifter is deploying, the robot must be kept
   * level. To keep it level we will speed up or slow down the lift motor.
   * 
   * This method uses a constant value for the deploy motor speed plus an adjustment
   * that comes from the Gyro (set by pidWrite()).  Since the lift motor is given
   * a negative percent output to deploy, a negative tilt adjustment will speed it up
   * and a positive tilt ajustment will slow it down.
   */
  public void deploy() {
    lift.set(ControlMode.PercentOutput, Parameters.LIFTER_LIFT_MOTOR_DEPLOY_SPEED + tiltAdjustment);
  }

  public void stopLifting() {
    lift.set(ControlMode.PercentOutput, 0.0);
    tiltAdjustment = 0.0;
  }
  
  /**
   * It tells if the lifter mechanism is all the way up (the robot's drive wheels are on the ground).
   * We know the lifter is all the way up when the limit switch is closed
   * @return boolean true if fully retracted, false otherwise
   */
  public boolean isRetracted() {
    SensorCollection sc = lift.getSensorCollection();
    if (sc != null)
    {
        if (sc.isRevLimitSwitchClosed())
        {
          return true;
        }
      }
    return false;
  }

  /**
   * It drives the robot forward/backwards
   * When the robot has finished climbing and needs to be fully supported
   * by the HAB ZONE LEVEL 2/3 
   * 
   * @param power Percentage in the range -1.0 (backwards) ... 0.0 ... 1.0 (forwards)
   */
  public void drive(double power) {
    drive.set(ControlMode.PercentOutput, power);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  /**
   * 
   * @param amountToSpeedUporSlowDownLiftMotorBasedOnRobotTilt
   */
  @Override
  public void pidWrite(double amountToSpeedUporSlowDownLiftMotorBasedOnRobotTilt) {
    tiltAdjustment = amountToSpeedUporSlowDownLiftMotorBasedOnRobotTilt;
  }
}
