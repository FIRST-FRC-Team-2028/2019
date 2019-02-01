/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.phantommentalists.Parameters;

/**
 * Control the distribution of power
 * Monitor the current going to motors.
 */
public class PDP extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private PowerDistributionPanel pdp;

  public PDP() {
    pdp = new PowerDistributionPanel();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  /**
   * Gets the drive current.
   * @return double of the drive current
   */
  public double getDriveCurrent() {
    double leftMasterCurrent = pdp.getCurrent(Parameters.PDP_LEFT_MOTOR_MASTER);
    double leftFollowerCurrent = pdp.getCurrent(Parameters.PDP_LEFT_MOTOR_FOLLOWER);
    double rightMasterCurrent = pdp.getCurrent(Parameters.PDP_RIGHT_MOTOR_MASTER);
    double rightFollowerCurrent = pdp.getCurrent(Parameters.PDP_RIGHT_MOTOR_FOLLOWER);
    
    return leftMasterCurrent + leftFollowerCurrent + rightMasterCurrent + rightFollowerCurrent;
  }
}
