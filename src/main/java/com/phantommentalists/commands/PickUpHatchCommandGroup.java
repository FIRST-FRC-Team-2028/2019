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

public class PickUpHatchCommandGroup extends CommandGroup {
  /**
   * Moves the elevator to the HATCH_LEVEL_1 position
   * To pickup the hatch the command extend/deploys Hatch Handler
   * Deploying it runs in parallel with the elevator
   * Drive to hatch (sensed somehow or viewed by camera)
   * Collapse vacuum cups by extending a pneumatic cylinder that pulls
   * on other pneumatic cylinders (only one solenoid operated valve is needed)
   */
  public PickUpHatchCommandGroup(Telepath r) {
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

    requires(r.getHandler());
    requires(r.getElevator());
    requires(r.getDrive());

    //Move the elevator to the HATCH_LEVEL_1 position
    addSequential(new GoToElevatorPositionCommand(ElevatorPosition.HATCH_LOW, r));
    
    addParallel(new ElevatorHoldPositionTimeCommand(r, 4));
    //To pickup the hatch the command extends/deploys Hatch Handler
    addSequential(new DeployHatchHandlerCommand(r));
    //Deploying it runs in parallel with the elevator
    //Drive to hatch (sensed somehow or viewed by camera)
    addSequential(new DriveToHatchCommand(r));
    //FIXME ensure DriveToHatchCommand exists
    //Collapse vacuum cups by extending a pneumatic cylinder that pulls
    //on other pneumatic cylinders (only one solenoid operated valve is needed)
    addSequential(new GrabHatchCommand(r));
    //TODO Do we need to drive backwards just a bit to check if grabbed?
  }
}
