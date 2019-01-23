/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.phantommentalists.Parameters;
import com.phantommentalists.DriveSide;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drive extends Subsystem {

      private DriveSide left;

      private DriveSide right;

  /** 
    * Default constructor
    */
  public Drive() {
      left = new DriveSide(true, Parameters.DriveGearbox.TWO_MOTOR_GEARBOX);
      right = new DriveSide(false, Parameters.DriveGearbox.TWO_MOTOR_GEARBOX);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
