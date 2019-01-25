/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

/**
 * Parameters is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Parameters {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;


  public static final boolean ELEVATOR_AVAILABLE = false;

  public static final boolean COMPRESSOR_AVAILABLE = false;

  public static final boolean GRIPPER_AVAILABLE = false;
  public static final double GRIPPER_INFEED_SPEED = 100;
  public static final double GRIPPER_LAUNCH_SPEED = -300;

  /**
   * Flag that tells the code if there is a drive system 
   */
  public static final boolean DRIVE_AVAILABLE = true;

  public enum DriveGearbox {
    /** We're deploying on practice robot with dual CIM motor gearbox */
    TWO_MOTOR_GEARBOX,

    /** We're deploying on competition robot with four 775 motor gearbox */
    FOUR_MOTOR_GEARBOX;
  }
  
  /**
   * Enum to hold all information about devices on the CAN bus
   */
  public enum CanId {
    LEFT_MASTER_CAN_ID(10, true),
    RIGHT_MASTER_CAN_ID(20, false),
        
    ELEVATOR(30, false),
    
    CARGO_INTAKE(40, false),
    CARGO_HANDLER(50, false);

    private int canId;

    private boolean inverted; 

    CanId(int canId, boolean inverted) {
      this.inverted = inverted;
      this.canId = canId;
    }

    public int getCanId() {
      return canId;
    }

    public boolean isInverted() {
      return inverted;
    }
  }

  public enum Pid {
    ELEVATOR(0, 0, 0, 0);

    private double p;
    private double i;
    private double d;
    private double f;

    private Pid(double p, double i, double d, double f) {
      this.p = p;
      this.i = i;
      this.d = d;
      this.f = f;
    }

    public double getP()
    {
      return p;
    }

    public double getI()
    {
      return i;
    }

    public double getD()
    {
      return d;
    }

    public double getF()
    {
      return f;
    }
  }
}
