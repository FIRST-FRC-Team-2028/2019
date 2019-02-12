/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

      private double ratio;
      private double maxCurrent=-1.;

  /** 
    * Default constructor
    */
  public Drive() {
      left = new DriveSide(true, Parameters.DRIVE_GEAR_BOX_TYPE);
      right = new DriveSide(false, Parameters.DRIVE_GEAR_BOX_TYPE);
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

  public void reflectiveAlignDrive(double x, double distance)
  {
    double diffl = 0.0;
    double diffr = 0.0;
      //pointed really far left
      if(x > (3*Parameters.CAM_WIDTH)/4 && distance > 200)
      {
        diffl = 0.3;
        diffr = -0.3;
      }
      //far away and somewhat pointed to the left
      else if(x < (3*Parameters.CAM_WIDTH)/4 && x > (Parameters.CAM_WIDTH/2) && distance > 200)
      {
        diffl = 0.20;
        diffr = -0.20;
      }
      //pointed really far right
      else if(x > Parameters.CAM_WIDTH/4 && x < Parameters.CAM_WIDTH && distance > 200)
      {
        diffl = -0.20;
        diffr = 0.20;
      }
      //far away and somewhat pointed to the right
      else if(x > 0 && x < Parameters.CAM_WIDTH/4 && distance > 200)
      {
        diffl = -0.3;
        diffr = 0.3;
      }
      //far away and 
      else if(x > 1)
      {

      }
      else if(x > 2)
      {

      }
      else
      {
        diffl = 0.0;
        diffr = 0.0;
      }


    left.setPercentOutput(0.2 + diffl);
    right.setPercentOutput(0.2 + diffr);
  }

  public void alignDrive(double leftangle, double rightangle, double leftx, double rightx)
  {

    if(leftangle > 90)
    {
        ratio = (leftangle-90)/550;
    }

    else if(leftangle <= 90)
    {
        ratio = (90-leftangle)/550;
    }

    if(leftx == 0)
    {
      left.setPercentOutput(-0.15);
      right.setPercentOutput(-0.15);
    }
    else if(leftx > Parameters.CAM_WIDTH/2)
    {
      if(leftx > (3*Parameters.CAM_WIDTH)/4)
      {
        ratio += 0.05;
      }
      left.setPercentOutput(-0.15 - ratio);
      right.setPercentOutput(-0.15 + (ratio/1.5));
    }
    else if(leftx <= Parameters.CAM_WIDTH/2)
    {
      if(leftx < Parameters.CAM_WIDTH/4)
      {
        ratio += 0.05;
      }
      left.setPercentOutput(-0.15  + (ratio/1.5));
      right.setPercentOutput(-0.15 - ratio);
    }
    // else if(leftx < Parameters.CAM_WIDTH/2)
    // {
    //   left.setPercentOutput(0.25);
    //   right.setPercentOutput(-0.25);
    // }
    // else if(leftangle < 90 && leftx >= Parameters.CAM_WIDTH/2)
    // {
    //   left.setPercentOutput(0.25);
    //   right.setPercentOutput(-0.3);
    // }

  }

  /* Use low gear when drive speed decreases to zero
   *  or when current draw (load) becomes high.
   * Use high gear when drive speed increases.
   */
  private void gearshift(double leftspeed, double rightspeed){
    double amps=0;
    int pdpnum=0;
    for (double load: Telepath.pdp.getDriveCurrent(Parameters.DRIVE_GEAR_BOX_TYPE)){
      amps+=load;
      System.err.println("pdp"+pdpnum+" "+load);
      pdpnum+=1;
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
    SmartDashboard.putNumber("amps", amps);
    maxCurrent=Math.max(maxCurrent, amps);
    SmartDashboard.putNumber("Maxamps", maxCurrent);
  }

  public void tankDrive(double lefta, double righta)
  {
    gearshift(lefta, righta);
    left.setPercentOutput(lefta);
    right.setPercentOutput(righta);
  }
}
