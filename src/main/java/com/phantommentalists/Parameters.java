/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

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

  public static final boolean CAMERA_AVAILABLE = false;
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
  public static final int ELEVATOR_POSITION_ERROR = 10;
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
  public static final double DRIVE_SHIFT_CURRENT = 60;
  public static final double DRIVE_SHIFT_SPEED = 0.3;
  public static final Value DRIVE_HIGH_GEAR = Value.kForward;
  public static final Value DRIVE_LOW_GEAR = Value.kReverse;
  public static final int DRIVE_CURRENT_LIMIT_PEAK = 30;
  public static final int DRIVE_CURRENT_LIMIT_CONTINUOUS = 20;
  public static final int DRIVE_CURRENT_LIMIT_PEAK_DURATION = 300;

  public enum DriveGearbox {
    /** We're deploying on practice robot with dual CIM motor gearbox */
    TWO_MOTOR_GEARBOX,

    /** We're deploying on competition robot with four 775 motor gearbox */
    FOUR_MOTOR_GEARBOX;
  }
  public static final DriveGearbox DRIVE_GEAR_BOX_TYPE = DriveGearbox.FOUR_MOTOR_GEARBOX;
  
  /** Enum to hold all information about pneumatic solenoids */
  public enum PneumaticChannel {
    HANDLER_CREATE_VACUUM(1),
    HANDLER_RELEASE_VACUUM(2),
    DRIVE_SHIFT_HIGH(3), 
    DRIVE_SHIFT_LOW(4), 
    HANDLER_CLIMBER_ARM(5);

    private int channel;

    private PneumaticChannel(int ch) {
      channel = ch;
    }

    public int getChannel() {
      return channel;
    }
  }

  /**
   * PDP Channels on the practice robot.
   */
  // PDP_LEFT_MOTOR_MASTER      12;
  // PDP_LEFT_MOTOR_FOLLOWER    13;
  // PDP_RIGHT_MOTOR_MASTER     15;
  // PDP_RIGHT_MOTOR_FOLLOWER = 14;
  /**
   * Enum to hold all information about devices on the CAN bus
   */
  public enum CanId {
    LEFT_MASTER_CAN_ID(20, false, 0),
    LEFT_2_FOLLOWER_CAN_ID(21, false, 13), 
    LEFT_4_FOLLOWER_CAN_ID_1(21, false, 1),
    LEFT_4_FOLLOWER_CAN_ID_2(22,false, 2),
    LEFT_4_FOLLOWER_CAN_ID_3(23,false, 3),
    RIGHT_MASTER_CAN_ID(10, true, 15),
    RIGHT_2_FOLLOWER_CAN_ID(11, true, 14), 
    RIGHT_4_FOLLOWER_CAN_ID_1(11, true, 14),
    RIGHT_4_FOLLOWER_CAN_ID_2(12,true, 13),
    RIGHT_4_FOLLOWER_CAN_ID_3(13,true, 12),

    ELEVATOR(40, false, 6),
    
    CARGO_INTAKE(52, false, 7),
    CARGO_HANDLER(50, false, 8),

    HATCH_LEAD_SCREW_MOTOR(51, false, 5), 
    LIFTER_LIFT_MOTOR(30, false, 4), 
    LIFTER_DRIVE_MOTOR(31, false, 11),
    
    HANDLER_VACUUM_EXTEND_RETRACT(60, false, 9);

    private int canId;
    private int pdpChannel;
    private boolean inverted; 

    CanId(int canId, boolean inverted, int channel) {
      this.inverted = inverted;
      this.canId = canId;
      this.pdpChannel = channel;
    }

    public int getCanId() {
      return canId;
    }

    public boolean isInverted() {
      return inverted;
    }

    public int getChannel(){
      return pdpChannel;
    }
  }

  public enum Pid {
    ELEVATOR(0, 0, 0, 0), 
    HATCH_LEADSCREW(0, 0, 0, 0);

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
  public static final double HATCHHANDLER_MOTOR_SPEED = 0;
  public static final int HATCHHANDLER_ZERO_POSITION = 0;
  public static final double HATCHHANDLER_DEPLOY_POSITION = 10;
  public static final double HATCHHANDLER_ZEROING_SPEED = 0;

  public static final boolean CARGO_HANDLER_AVAILABLE = false;
  public static final double CARGO_HANDLER_INTAKE_SPEED = 1;
  public static final double CARGO_HANDLER_SHOOT_SPEED = -1;
  public static final int CARGO_HANDLER_SENSOR = 0;

  public static final double LIFTER_LIFT_MOTOR_DEPLOY_SPEED = 0.4;
  public static final double LIFTER_LIFT_MOTOR_RETRACT_SPEED = -0.7;


}
