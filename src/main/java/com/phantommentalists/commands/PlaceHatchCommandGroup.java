/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.ElevatorPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceHatchCommandGroup extends CommandGroup {
  /**
   * Places the hatch on the rocket at a specified level
   * Steps to accomplish this are:
   *  Bring Elevator up to specified level
   *  Run the ReleaseHatchCommandGroup
   */
  Telepath r;
  public PlaceHatchCommandGroup(ElevatorPosition position, Telepath r) {
    this.r = r;
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
    requires(r.getDrive());
    requires(r.getHandler());

    //Bring Elevator up to specified level
    addSequential(new GoToElevatorPositionCommand(position, r));
    addSequential(new ReleaseHatchCommandGroup(r));
  }
}
