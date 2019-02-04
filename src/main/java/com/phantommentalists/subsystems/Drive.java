/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.phantommentalists.Parameters;
import com.phantommentalists.commands.DefaultDriveCommand;
import com.phantommentalists.DriveSide;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drive extends Subsystem {

      private DriveSide left;

      private DriveSide right;

      private double ratio;

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
   
    int angleint = (int)(leftangle/10);
    System.out.println(angleint);
    switch(angleint)
    {
      case 15:
        ratio = 0.4;
        break;
      case 14:
        ratio = 0.35;
        break;
      case 13:
        ratio = 0.3;
        break;
      case 12:
        ratio = 0.25;
        break;
      case 11:
        ratio = 0.2;
        break;
      case 10:
        ratio = 0.15;
        break;
      case 9:
        ratio = 0;
        break;
      case 8:
        ratio = 0.15;
        break;
      case 7:
        ratio = 0.2;
        break;
      case 6:
        ratio = 0.25;
        break;
      case 5:
        ratio = 0.3;
        break;
      case 4:
        ratio = 0.35;
        break;
      case 3:
        ratio = 0.40;
        break;
      default:
        ratio = 0;
        break;
    }

    if(leftangle > 90)
    {
      ratio = (leftangle-90)/700;
    }
    else if(leftangle <= 90)
    {
      ratio = (90-leftangle)/700;
    }

    if(leftx > Parameters.CAM_WIDTH/2)
    {
      left.setPercentOutput(-0.2 - ratio);
      right.setPercentOutput(-0.2 + (ratio/2));
    }
    else if(leftx <= Parameters.CAM_WIDTH/2)
    {
      left.setPercentOutput(-0.2 + (ratio/2));
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

  public void tankDrive(double lefta, double righta)
  {
    left.setPercentOutput(lefta);
    right.setPercentOutput(righta);
  }
}
