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
   * It has to place the hatch on the specified location by
   * Driving to the hatch
   * Elevate the Handler to the desired level
   * Driving and elevating runs in parallel
   * Release the hatch
   */
  public PlaceHatchCommandGroup(ElevatorPosition whichHatch) {
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

    requires(Telepath.drive);
    requires(Telepath.elevator);
    requires(Telepath.handler);

    //Driving to the hatch
    addParallel(new DriveToHatchCommand());
    //Elevate the Handler to the desired level
    addSequential(new GoToElevatorPositionCommand(whichHatch));
    // Driving and elevating runs in parallel
    // Release the hatch
    addSequential(new ReleaseHatchCommand());
  }
}
