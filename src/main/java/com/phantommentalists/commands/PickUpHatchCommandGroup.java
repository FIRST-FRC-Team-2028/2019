/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;
//import com.phantommentalists.Parameters.ElevatorPosition;
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

    //Move the elevator to the HATCH_LEVEL_1 position
    //addSequential(Telepath.elevator.setPosition(ElevatorPosition.HATCH_LOW));
    //FIXME make the elevator command^^
    //To pickup the hatch the command turns on Lead Screw Motor, 
    //Extends/deploys Lead Screw Motor, sensed by an encoder with 
    //limit switches or current failsafes
    //Turning on the Lead Screw Motor and deploying it runs in parallel
    //Drive to hatch (sensed somehow or viewed by camera)
    //Collapse vacuum cups by extending a pneumatic cylinder that pulls
    //on other pneumatic cylinders (only one solenoid operated valve is needed)
    //Placing hatch, the command breaks the vacuum by a valve releasing 
    //the vacuum (mechanism TBD) Similar to a solenoid operated valve, 
    //may use a servo
    //Stop/turn off Lead Screw Motor
    //DO ANY COMMANDS RUN IN PARALLEL?

  }
}
