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



  public static final boolean COMPRESSOR_AVAILABLE = false;

  public static final boolean INTAKE_AVAILABLE = false;
  public static final double CARGO_INTAKE_ROLLER_SPEED = 1.0;
  public static final double CARGO_INTAKE_DEPLOY_SPEED = 0.5;
  public static final double CARGO_INTAKE_RETRACT_SPEED = -0.5;

  public static final double CAM_FILTER_LINES_ANGLE_LESSER = 1.0;
  public static final double CAM_FILTER_LINES_ANGLE_GREATER = 359.0;
  public static final double CAM_FILTER_LINES_MINIMUM_LENGTH = 1.0;

  public static final int CAM_WIDTH = 346;
  public static final int CAM_HEIGHT = 280;
  
  //using multiple controllers
  public enum MultiController {
    LOGITECH_EXTREME(20),
    PS_CONTROLLER(21),
    XBOX_CONTROLLER(1),
    CUBE_STEERING_WHEEL(24);  // GameCube Controller

    private int num;
    MultiController(int num){
      this.num=num;
    }

    public int getnum(){
      return this.num;
    }
  }
  public static MultiController multiContFromNum(int num)
  {
    for (MultiController m : MultiController.values())
    {
      if (m.getnum() == num)
      {
        return m;
      }
    }
    return null;
  }

  public static final int STICK_GET_LEFT_Y_AXIS = 1;
  public static final int STICK_GET_RIGHT_Y_AXIS = 2;
  public static final int LOGITECH_Y_AXIS = 1;
  public static final int LOGITECH_TWIST = 2;
  public static final int PS_LEFT_STICK = 1;
  public static final int PS_RIGHT_STICK = 5;
  public static final int CUBE_WHEEL_AXIS = 0;
  public static final int CUBE_RIGHT_PADDLE = 5;
  public static final int CUBE_RIGHT_PEDAL = 1;
  
  
  public static final boolean ELEVATOR_AVAILABLE = false;
  public static final double ELEVATOR_ZEROING_SPEED = -0.25;
  public enum ElevatorPosition {
    HATCH_LOW(900),
    HATCH_MIDDLE(15000),
    HATCH_HIGH(27000),
    CARGO_LOW(1500),
    CARGO_MIDDLE(17500),
    CARGO_HIGH(28500),
    HAB_ZONE_LEVEL_2(400),
    HAB_ZONE_LEVEL_3(5000);

    private int ticks;

    private ElevatorPosition(int t)
    {
      ticks = t;
    }

    public int getSetPoint()
    {
      return ticks;
    }
  }

  public enum AutoMode {
    AUTO,
    MANUAL,
    ZEROING;
  }


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
  
  /** Enum to hold all information about pneumatic solenoids */
  public enum PneumaticChannel {
    HANDLER_CREATE_VACUUM(1),
    HANDLER_RELEASE_VACUUM(2);

    private int channel;

    private PneumaticChannel(int ch) {
      channel = ch;
    }

    public int getChannel() {
      return channel;
    }
  }

  /**
   * Enum to hold all information about devices on the CAN bus
   */
  public enum CanId {
    LEFT_MASTER_CAN_ID(10, true),
    RIGHT_MASTER_CAN_ID(20, false),
    LEFT_2_FOLLOWER_CAN_ID(11, false), 
    LEFT_4_FOLLOWER_CAN_ID_1(11, false),
    LEFT_4_FOLLOWER_CAN_ID_2(12,false),
    LEFT_4_FOLLOWER_CAN_ID_3(13,false),
    RIGHT_2_FOLLOWER_CAN_ID(21, false), 
    RIGHT_4_FOLLOWER_CAN_ID_1(21, false),
    RIGHT_4_FOLLOWER_CAN_ID_2(22,false),
    RIGHT_4_FOLLOWER_CAN_ID_3(23,false),

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

  public static final boolean HANDLER_AVAILABLE = false;
}
