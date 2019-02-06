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

      private double ratio;

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

  public void alignDrive(double leftangle, double rightangle, double leftx, double rightx)
  {
   
    // int angleint = (int)(leftangle/10);
    // System.out.println(angleint);
    // switch(angleint)
    // {
    //   case 15:
    //     ratio = 0.3;
    //     break;
    //   case 14:
    //     ratio = 0.25;
    //     break;
    //   case 13:
    //     ratio = 0.2;
    //     break;
    //   case 12:
    //     ratio = 0.15;
    //     break;
    //   case 11:
    //     ratio = 0.10;
    //     break;
    //   case 10:
    //     ratio = 0.05;
    //     break;
    //   case 9:
    //     ratio = 0;
    //     break;
    //   case 8:
    //     ratio = 0.05;
    //     break;
    //   case 7:
    //     ratio = 0.08;
    //     break;
    //   case 6:
    //     ratio = 0.11;
    //     break;
    //   case 5:
    //     ratio = 0.15;
    //     break;
    //   case 4:
    //     ratio = 0.20;
    //     break;
    //   case 3:
    //     ratio = 0.25;
    //     break;
    //   default:
    //     ratio = 0;
    //     break;
    // }

    if(leftangle > 90)
    {
        ratio = (leftangle-90)/500;
    }

    else if(leftangle <= 90)
    {
        ratio = (90-leftangle)/500;
    }

    if(leftx > Parameters.CAM_WIDTH/2)
    {
      left.setPercentOutput(-0.2 - ratio);
      right.setPercentOutput(-0.2 + (ratio/1.5));
    }
    else if(leftx <= Parameters.CAM_WIDTH/2)
    {
      left.setPercentOutput(-0.2  + (ratio/1.5));
      right.setPercentOutput(-0.2 - ratio);
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
    else
    {
      left.setPercentOutput(-0.25);
      right.setPercentOutput(-0.25);
    }

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
