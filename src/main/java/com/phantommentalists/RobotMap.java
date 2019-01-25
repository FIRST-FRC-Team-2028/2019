/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  public static Joystick joystick;
  public static Joystick buttonBoxLeft;
  public static Joystick buttonBoxRight;

  public static TalonSRX left_drive_master;
  public static TalonSRX left_drive_slave;
  public static TalonSRX right_drive_master;
  public static TalonSRX right_drive_slave;

  public static TalonSRX elevator_motor;

  public static TalonSRX cargo_motor_handler;
  //public static TalonSRX cargo_motor_bottom;
  public static TalonSRX cargo_motor_intake;

  public static Compressor compressor;

  public static void init() {
    joystick = new Joystick(0);
    buttonBoxLeft = new Joystick(1);
    buttonBoxRight = new Joystick(2);

    // Drive motors defined in DriveSide

    if(Parameters.GRIPPER_AVAILABLE)
    {
      cargo_motor_intake = new TalonSRX(Parameters.CanId.CARGO_INTAKE.getCanId());
      cargo_motor_handler = new TalonSRX(Parameters.CanId.CARGO_HANDLER.getCanId());
    }

    if(Parameters.ELEVATOR_AVAILABLE){
      elevator_motor= new TalonSRX(Parameters.CanId.ELEVATOR.getCanId());
    }

    if(Parameters.COMPRESSOR_AVAILABLE){
      compressor = new Compressor(0);
    }
  }

}
