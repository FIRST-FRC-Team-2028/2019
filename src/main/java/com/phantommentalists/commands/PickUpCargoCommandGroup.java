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

public class PickUpCargoCommandGroup extends CommandGroup {
/**
*Move the elevator to the zero position
*Deploy and runs the Cargo Intake
*While the Cargo Intake is being deployed, run the Cargo Handler
*Run Cargo Handler until we have a ball
*Retract Cargo Intake
*/
public PickUpCargoCommandGroup(Telepath r) {
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

    // requires(r.getElevator());
    if (Parameters.INTAKE_AVAILABLE){
      requires(r.getCargoIntake());
    }
    requires(r.getHandler());

    //Move the elevator to the zero position
    // addSequential(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.LOWER_LIMIT, r, 0));
    //Deploy and runs the Cargo Intake
    // TODO Test interrupt mechanisms of CommandGroups
    //While the Cargo Intake is being deployed, run the Cargo Handler
    if (Parameters.INTAKE_AVAILABLE){
      // addParallel(new DeployCargoIntakeCommand(r));
    }
    //Run Cargo Handler until we have a ball
    if (Parameters.CARGO_HANDLER_AVAILABLE){
      addParallel(new CargoHandlerLoadCommand(r));
      if(Parameters.INTAKE_AVAILABLE){
        addSequential(new CargoIntakeLoadCommand(r));
      }
    }
    //retract Cargo Intake
    if (Parameters.INTAKE_AVAILABLE){
      // addSequential(new RetractCargoIntakeCommand(r));
    }
  }
}
