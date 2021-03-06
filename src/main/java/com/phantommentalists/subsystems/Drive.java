/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
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

      private Timer timer2;

      private DriveSide left;
      private DriveSide right;

      private DoubleSolenoid shifter;
      private DoubleSolenoid.Value currentGear = Parameters.DRIVE_LOW_GEAR;
      private double shiftTime = 0.0;

      private double ratio;
      private double maxCurrent = -1.;

      private Telepath robot;

      Timer timer;
  /** 
    * Default constructor
    */
  public Drive(Telepath r) {
      robot = r;
      left = new DriveSide(true, Parameters.DRIVE_GEAR_BOX_TYPE, r);
      right = new DriveSide(false, Parameters.DRIVE_GEAR_BOX_TYPE, r);
      shifter = new DoubleSolenoid(Parameters.PneumaticChannel.DRIVE_SHIFT_HIGH.getChannel(),Parameters.PneumaticChannel.DRIVE_SHIFT_LOW.getChannel());
      timer = new Timer();
      timer2= new Timer();
      timer.start();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new DefaultCommand());
    setDefaultCommand(new DefaultDriveCommand(robot));
  }
  /**
   * spins the robot...
   */
  public void spinDrive()
  {
    left.setPercentOutput(0.5);
    right.setPercentOutput(-0.5);
  }

  public void goStraight(double speed) {
    left.setPercentOutput(speed);
    right.setPercentOutput(speed);
  }

  public void reflectiveAlignDrive(double x)
  {
    double diffl = 0.0;
    double diffr = 0.0;

    if(x < Parameters.CAM_WIDTH/4)
    {
      diffl = 0.20;
      diffr = -0.20;
    }
    else if(x < (Parameters.CAM_WIDTH/2)-(Parameters.CAM_WIDTH/7))
    {
      diffl = -0.10;
      diffr = 0.10;
    }
    else if(x >= (Parameters.CAM_WIDTH/2)+(Parameters.CAM_WIDTH/7))
    {
      diffl = 0.10;
      diffr = -0.10;
    }
    else if(x >= (3*Parameters.CAM_WIDTH)/4)
    {
      diffr = 0.20;
      diffl = -0.20;
    }
    
    left.setPercentOutput(0.1 + diffl);
    right.setPercentOutput(0.1 + diffr);
  }
  /**
   * takes camera parameters to drive towards reflective tape.
   * @param x
   * @param distance
   */
  public void reflectiveAlignDrive(double x, double distance)
  {
    double diffl = 0.0;
    double diffr = 0.0;
    

    if(x < Parameters.CAM_WIDTH/4)
    {
      diffl = 0.2;
      diffr = -0.2;
    }
    else if(x < Parameters.CAM_WIDTH/2)
    {
      diffl = -0.1;
      diffr = 0.1;
    }
    else if(x >= Parameters.CAM_WIDTH/2)
    {
      diffl = 0.1;
      diffr = -0.1;
    }
    else if(x >= (3*Parameters.CAM_WIDTH)/4)
    {
      diffr = 0.2;
      diffl = -0.2;
    }


    if(distance > 100)
    {
      diffl += 0.05;
      diffr += -0.05;
    }
    else if (distance > 80 )
    {
      diffl += 0.07;
      diffl += -0.07;
    }
    else if (distance > 60)
    {
      diffl += 0.1;
      diffr += -0.1;
    }
    else if (distance > 40)
    {
      diffl += 0.15;
      diffr += -0.15;
    }
    else if (distance > 20)
    {
      diffl += 0.17;
      diffr += -0.17;
    }
    else if (distance < 20)
    {
      diffl += 0.2;
      diffr += -0.2;
    }
    
    if (distance > 100 && diffl < 0)
    {
      diffl *= -1;
      diffr *= -1;
    }


    left.setPercentOutput(0.2 + diffl);
    right.setPercentOutput(0.2 + diffr);
  }


  /**
   * takes in angles and x coordinates to drive with a camera facing the ground.
   * @param leftangle
   * @param rightangle
   * @param leftx
   * @param rightx
   */
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


  public void testMotors()
  {
    // timer2.start();
    // if(timer2.get() < 21)
    // {
    //   left.testMotors();
    // }
    // else if (timer2.get() < 42)
    // {
    //   right.testMotors();
    // }

  }

  /* Use low gear when drive speed decreases to zero
   *  or when current draw (load) becomes high
   *  or left and right are opposite side
   *  or widely disparate
   * Use high gear when magnitude of drive speed increases.
   */
  private void gearshift(double leftspeed, double rightspeed){
    double amps=0;
    DoubleSolenoid.Value newGear = Parameters.DRIVE_HIGH_GEAR;
    double currentTime = timer.get();
    for (double load: robot.getPDP().getDriveCurrent(Parameters.DRIVE_GEAR_BOX_TYPE)){
      amps+=load;
    }
    if (amps > Parameters.DRIVE_SHIFT_CURRENT) {
      newGear=Parameters.DRIVE_LOW_GEAR;
    }
    else if ( (Math.abs(leftspeed)+Math.abs(rightspeed)) < Parameters.DRIVE_SHIFT_SPEED) {
      newGear=Parameters.DRIVE_LOW_GEAR;
    }
    else if (leftspeed*rightspeed < 0) {
      newGear=Parameters.DRIVE_LOW_GEAR;
    }
    else if ( Math.abs(leftspeed-rightspeed) > Parameters.DRIVE_LEFT_RIGHT_SPEED_DIFF) {
      newGear=Parameters.DRIVE_LOW_GEAR;
    }
    if (Parameters.DRIVE_SHIFTER_ENABLE) {
      if (currentTime - shiftTime > Parameters.DRIVE_SHIFT_TIME_INTERVAL) {
        currentGear=newGear;
        shiftTime = currentTime;
        shifter.set(currentGear);
      }
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

  public void process() {
    left.process();
    right.process();
    SmartDashboard.putNumber("Drive: Total Current", getAllMotorCurrent());
  }

  public double getAllMotorCurrent() {
    return left.getMotorCurrentOutput() + right.getMotorCurrentOutput();
  }
}
