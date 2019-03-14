/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Drive;
import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabHatchCommandGroup extends CommandGroup {
  /**
   * Retrives the hatch from either the Cargo ship or the Loading Station
   * Lift the elevator and back up to get the hatch out
   */
  private Elevator elevator;
  private Drive drive;

  public GrabHatchCommandGroup(Telepath r) {
    elevator = r.getElevator();
    drive = r.getDrive();
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
    requires(elevator);
    requires(drive);

    //lift the elevator
    addSequential(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_OUT, r));
    //back up to get the hatch out
    addSequential(new DriveForwardToHatchCommand(drive, true));
  }
}
