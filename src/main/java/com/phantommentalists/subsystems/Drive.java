/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.commands.DefaultDriveCommand;
import com.phantommentalists.DriveSide;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drive extends Subsystem {

      private DriveSide left;
      private DriveSide right;

      private DoubleSolenoid shifter;


  /** 
    * Default constructor
    */
  public Drive() {
      left = new DriveSide(true, Parameters.DriveGearbox.TWO_MOTOR_GEARBOX);
      right = new DriveSide(false, Parameters.DriveGearbox.TWO_MOTOR_GEARBOX);
      shifter = new DoubleSolenoid(Parameters.PneumaticChannel.DRIVE_SHIFT_HIGH.getChannel(),Parameters.PneumaticChannel.DRIVE_SHIFT_LOW.getChannel());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new DefaultCommand());
    setDefaultCommand(new DefaultDriveCommand());
  }

  public void spinDrive()
  {
    left.setPercentOutput(0.5);
    right.setPercentOutput(-0.5);
  }

  public boolean alignDrive(double leftangle, double rightangle, double leftx, double rightx)
  { 
    return false;
  }

  /* Use low gear when drive speed decreases to zero
   *  or when current draw (load) becomes high.
   * Use high gear when drive speed increases.
   */
  private void gearshift(double leftspeed, double rightspeed){
    double amps=0;
    for (double load: Telepath.pdp.getDriveCurrent()){
      amps+=load;
    }
    if (amps > Parameters.DRIVE_SHIFT_CURRENT){
      shifter.set(Parameters.DRIVE_LOW_GEAR);
    }
    else if ((Math.abs(leftspeed)+Math.abs(rightspeed))<Parameters.DRIVE_SHIFT_SPEED){
      shifter.set(Parameters.DRIVE_LOW_GEAR);
    }
    else {
      shifter.set(Parameters.DRIVE_HIGH_GEAR);
    }
  }

  public void tankDrive(double lefta, double righta)
  {
    gearshift(lefta, righta);
    left.setPercentOutput(lefta);
    right.setPercentOutput(righta);
  }
}
