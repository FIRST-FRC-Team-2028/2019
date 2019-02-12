/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.ElevatorPosition;
//import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PickUpHatchCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public PickUpHatchCommandGroup() {
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

    requires(Telepath.handler);
    requires(Telepath.elevator);
    requires(Telepath.drive);

    //Move the elevator to the HATCH_LEVEL_1 position
    addParallel(new GoToElevatorPositionCommand(ElevatorPosition.HATCH_LOW));
    //To pickup the hatch the command extends/deploys Hatch Handler
    addSequential(new DeployHatchHandlerCommand());
    //Deploying it runs in parallel with the elevator
    //Drive to hatch (sensed somehow or viewed by camera)
    addSequential(new DriveToHatchCommand());
    //FIXME ensure DriveToHatchCommand exists
    //Collapse vacuum cups by extending a pneumatic cylinder that pulls
    //on other pneumatic cylinders (only one solenoid operated valve is needed)
    addSequential(new GrabHatchCommand());
  }
}
