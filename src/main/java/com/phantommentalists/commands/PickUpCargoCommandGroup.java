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

public PickUpCargoCommandGroup() {
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

    requires(Telepath.elevator);
    requires(Telepath.cargoIntake);
    requires(Telepath.handler);
    //Move the elevator to the zero position
    addSequential(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.LOWER_LIMIT));
    //Deploy and runs the Cargo Intake
    //While the Cargo Intake is being deployed, run the Cargo Handler
    addParallel(new DeployCargoIntakeCommand());
    //Run Cargo Handler until we have a ball
    addSequential(new CargoHandlerLoadCommand());
    //retract Cargo Intake
    addSequential(new RetractCargoIntakeCommand());
  }
}
