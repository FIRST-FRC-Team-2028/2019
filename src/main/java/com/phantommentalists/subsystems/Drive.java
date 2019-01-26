/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.commands.DefaultCommand;
import com.phantommentalists.DriveSide;
import com.phantommentalists.OI;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drive extends Subsystem {

      private DriveSide left;

      private DriveSide right;

      private OI oi;

  /** 
    * Default constructor
    */
  public Drive() {
      oi = Telepath.oi;
      left = new DriveSide(true, Parameters.DriveGearbox.TWO_MOTOR_GEARBOX);
      right = new DriveSide(false, Parameters.DriveGearbox.TWO_MOTOR_GEARBOX);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new DefaultCommand());
    setDefaultCommand(new DefaultCommand());
  }

  public void tankDrive()
  {
    System.out.println(oi.getLeftStick());
    left.setPercentOutput(oi.getLeftStick());
    right.setPercentOutput(oi.getRightStick());
  }
}
