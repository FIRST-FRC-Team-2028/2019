/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbHABCommandGroup extends CommandGroup {
  /**
   * Extend Climbing Arm
   * Deploy Lifter
   * Lift the robot to the height of the HAB
   * Drive Lifter until the center of gravity and two wheels are over the HAB
   * Retract Lifter and raise the elevator
   * Drive until the robot is completley over HAB
   */
  public ClimbHABCommandGroup(Telepath r) {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.

    requires(r.getElevator());
    requires(r.getLifter());
    requires(r.getHandler());

    //Raise the elevator to HAB height
    addSequential(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HAB_ZONE_LEVEL_3, r));
    //Extend Climbing Arm
    addSequential(new ExtendClimbingArmCommand(r));
    //Deploy Lifter
    addSequential(new DeployLifterCommand(r));
    //Lift the robot to the height of the HAB
    addSequential(new LiftRobotCommand(r));
    //Drive Lifter until the center of gravity and two wheels are over the HAB
    //Retract Lifter and raise the elevator
    //Drive until the robot is completley over HAB
  }
}
