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
    LEFT_2_FOLLOWER_CAN_ID(11, false), 
    LEFT_4_FOLLOWER_CAN_ID_1(11, false),
    LEFT_4_FOLLOWER_CAN_ID_2(12,false),
    LEFT_4_FOLLOWER_CAN_ID_3(13,false),
    RIGHT_2_FOLLOWER_CAN_ID(21, false), 
    RIGHT_4_FOLLOWER_CAN_ID_1(21, false),
    RIGHT_4_FOLLOWER_CAN_ID_2(22,false),
    RIGHT_4_FOLLOWER_CAN_ID_3(23,false);

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
}
