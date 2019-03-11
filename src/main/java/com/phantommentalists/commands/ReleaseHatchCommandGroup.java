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
import com.phantommentalists.subsystems.Handler;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ReleaseHatchCommandGroup extends CommandGroup {
  /**
   *  Places the hatch on the velcro
   * Steps include:
   *  Drives forward to place the hatch
   *  Bring down elevator and in parallel retract the Hatch Handler
   */

  public ReleaseHatchCommandGroup(Telepath r) {
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

    requires(r.getDrive());
    requires(r.getElevator());
    requires(r.getHandler());

   //Drives forward to place the hatch
   addSequential(new DriveForwardToHatchCommand(r.getDrive()));
   //Bring down elevator
   addParallel(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_SAFE, r));
   // and in parallel retract the Hatch Handler
   addSequential(new RetractHatchHandlerCommand(r));
  }
}
