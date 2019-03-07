/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Parameters is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Parameters {

/**
   * Flag that tells the code systems exist 
   */
  public static final boolean DRIVE_AVAILABLE = true;
  public static final boolean CAMERA_AVAILABLE = false;
  public static final boolean INTAKE_AVAILABLE = true;
  public static final boolean COMPRESSOR_AVAILABLE = true;
  public static final boolean ELEVATOR_AVAILABLE = true;
  public static final boolean HANDLER_AVAILABLE = true;
  public static final boolean HATCH_HANDLER_AVAILABLE = false;
  public static final boolean CARGO_HANDLER_AVAILABLE = true;
  public static final boolean LIFTER_AVAILABLE = false;
  public static final boolean GYRO_AVAILABLE = false;
  public static final boolean BUTTONBOX_AVAILABLE = true;
  


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
  //buttons on Dual Arcade 0
  // public static final int BUTTON_LIFTER_RETRACT = 10;
  // public static final int BUTTON_LIFTER_DEPLOY = 9;
  public static final int BUTTON_CARGO_HANDLER_INTAKE = 6;
  public static final int BUTTON_ELEVATOR_UP = 4; // 7 doesn't work, use 4 on left button box
  public static final int BUTTON_CARGO_INTAKE_EXTEND = 5;
  public static final int BUTTON_CARGO_INTAKE_RETRACT = 10;
  public static final int BUTTON_CARGO_RUN_BOTH_ROLLERS = 6;
  //public static final int BUTTON_CARGO_INTAKE_RETRACT = 6; 
  //buttons on Dual Arcade 1
  public static final int BUTTON_ELEVATOR_DOWN = 8;
  public static final int BUTTON_HATCH_1 = 5;
  public static final int BUTTON_CARGO_1 = 6;
  public static final int BUTTON_HATCH_2 = 3;
  public static final int BUTTON_CARGO_2 = 4;
  public static final int BUTTON_HATCH_3 = 1;
  public static final int BUTTON_CARGO_3 = 2;
  public static final int SWITCH_BLUE = 11;

  
  public static final int ELEVATOR_PID_CRUISE_VELOCITY_UP = 2000;
  public static final int ELEVATOR_PID_CRUISE_ACCEL_UP = 2000;
  public static final int ELEVATOR_PID_CRUISE_VELOCITY_DOWN = 100;
  public static final int ELEVATOR_PID_CRUISE_ACCEL_DOWN = 100;
  public static final double ELEVATOR_ZEROING_SPEED = -0.25;
  public static final int ELEVATOR_POSITION_ERROR = 1500;
  public static final double ELEVATOR_MANUAL_SPEED = 0.6;
  public static final double ELEVATOR_ZEROING_CURRENT_LIMIT = 3.5;
  public static final int ELEVATOR_ZEROING_ENCODER_LIMIT = 10;
  public enum ElevatorPosition {
    LOWER_LIMIT(200),
    HATCH_LOW(6105),
    HATCH_MIDDLE(16500),
    HATCH_HIGH(26700),
    CARGO_LOW(5300),
    CARGO_MIDDLE(16500),
    CARGO_HIGH(26500),
    HAB_ZONE_LEVEL_2(400),
    HAB_ZONE_LEVEL_3(5000),
    UPPER_LIMIT(27000);

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


  public static final double DRIVE_SHIFT_CURRENT = 60;
  public static final double DRIVE_SHIFT_SPEED = 0.35;
  public static final double DRIVE_LEFT_RIGHT_SPEED_DIFF = 0.4;
  public static final Value DRIVE_HIGH_GEAR = Value.kForward;
  public static final Value DRIVE_LOW_GEAR = Value.kReverse;
  public static final int DRIVE_CURRENT_LIMIT_PEAK = 30;
  public static final int DRIVE_CURRENT_LIMIT_CONTINUOUS = 20;
  public static final int DRIVE_CURRENT_LIMIT_PEAK_DURATION = 300;
  public static final boolean DRIVE_SHIFTER_ENABLE = true;
  public static final double DRIVE_SHIFT_TIME_INTERVAL = 1.0;
  public static final double DRIVE_SPEED_LIMITER_ELEVATOR_CG = 0.3;

  public enum DriveGearbox {
    /** We're deploying on practice robot with dual CIM motor gearbox */
    TWO_MOTOR_GEARBOX,

    /** We're deploying on competition robot with four 775 motor gearbox */
    FOUR_MOTOR_GEARBOX;
  }
  public static final DriveGearbox DRIVE_GEAR_BOX_TYPE = DriveGearbox.FOUR_MOTOR_GEARBOX;
  
  /** Enum to hold all information about pneumatic solenoids */
  public enum PneumaticChannel {
    // HANDLER_CREATE_VACUUM(0),
    // HANDLER_RELEASE_VACUUM(1),
    HANDLER_RETRACT(0), 
    HANDLER_DEPLOY(1),
    DRIVE_SHIFT_HIGH(3), 
    DRIVE_SHIFT_LOW(4), 
    HANDLER_CLIMBER_ARM(5),
    //CARGO_INTAKE_EXTENDER(6),
    //CARGO_INTAKE_RETRACT(7), 
    HANDLER_VENT_CLOSED(6), 
    HANDLER_VENT_OPEN(7);


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
    LEFT_MASTER_CAN_ID(20, false, 15),
    LEFT_2_FOLLOWER_CAN_ID(21, false, 14), 
    LEFT_4_FOLLOWER_CAN_ID_1(21, false, 1),
    LEFT_4_FOLLOWER_CAN_ID_2(22,false, 2),
    LEFT_4_FOLLOWER_CAN_ID_3(23,false, 3),
    RIGHT_MASTER_CAN_ID(10, true, 12),
    RIGHT_2_FOLLOWER_CAN_ID(11, true, 13), 
    RIGHT_4_FOLLOWER_CAN_ID_1(11, true, 14),
    RIGHT_4_FOLLOWER_CAN_ID_2(12,true, 13),
    RIGHT_4_FOLLOWER_CAN_ID_3(13,true, 12),

    ELEVATOR(40, true, 4),
    
    CARGO_INTAKE(52, true, 10),
    CARGO_INTAKE_EXT(30, true, 8),
    //CARGO_INTAKE_EXT(28, true, 9),
    CARGO_HANDLER(50, false, 6),

    HATCH_LEAD_SCREW_MOTOR(51, false, 5), 
    LIFTER_LIFT_MOTOR(30, false, 7), 
    LIFTER_DRIVE_MOTOR(31, false, 11);

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
    ELEVATOR(0.4, 0, 0, 0.011527), 
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

  public static final double HATCHHANDLER_MOTOR_SPEED = 0.0;
  public static final int HATCHHANDLER_ZERO_POSITION = 0;
  public static final DoubleSolenoid.Value HATCHHANDLER_DEPLOY_POSITION = Value.kForward;
  public static final double HATCHHANDLER_ZEROING_SPEED = -0.2;
  public static final DoubleSolenoid.Value HATCHHANDLER_RETRACTED_POSITION = Value.kReverse;
  public static final double HATCHHANDLER_ZEROING_CURRENT_LIMIT = 2.0;  //amps
  public static final double HATCHHANDLER_SET_POINT_CLOSE = 100;
  public static final double HATCHHANDLER_VACCUUM_DELAY = 1.0;  //seconds
  public static final double HATCH_RETURN_BEFORE_END_MATCH = 0.0;

  public static final double CARGO_HANDLER_INTAKE_SPEED = -1.0;
  public static final double CARGO_HANDLER_SHOOT_SPEED = 1.0;
  public static final int CARGO_HANDLER_SENSOR = 0;
  public static final int SHOOT_CARGO_TIME = 2;

  public static final double CARGO_INTAKE_ROLLER_SPEED = 0.7;
  //public static final Value CARGO_INTAKE_EXTEND = Value.kForward;
  //public static final Value CARGO_INTAKE_RETRACT = Value.kReverse;
  public static final double CARGO_INTAKE_EXTENDER_CURRENT_LIMIT = 300.0;
  public static final double CARGO_EXTENDER_RETRACT_SPEED = -0.5;
  public static final double CARGO_EXTENDER_SPEED = 0.5;

  //public static final double LIFTER_LIFT_MOTOR_DEPLOY_SPEED = 0.4;
  /**
   * 80/1 is the gear ratio of the elevator
   * 49/1 is the gear ratio of the lifter
   * To approximatley match the lifter speed to the elevator speed
   * use the ratio of gear ratios and the elevator speed
   */
  public static final double LIFTER_LIFT_MOTOR_DEPLOY_SPEED = ELEVATOR_MANUAL_SPEED * 80/49;
  public static final double LIFTER_LIFT_MOTOR_RETRACT_SPEED = -0.7;
  public static final double LIFT_DEPLOY_TIME = 3.0;
  public static final double LIFTER_LIFT_MOTOR_VARIATION = 0.2;

  public static final double LIFT_LEVELER_Kp = 0.2;
  public static final double LIFT_LEVELER_Ki = 0.0;
  public static final double LIFT_LEVELER_Kd = 0.0;
  public static final double LIFT_LEVELER_TOLERANCE = 3.0;
  public static final boolean ULTRASONIC_AVAILABLE = false;
  
public static final int BUTTON_HATCHHANDLER_SUCK = 1;
public static final int BUTTON_HATCHHANDLER_BLOW = 2;
}
