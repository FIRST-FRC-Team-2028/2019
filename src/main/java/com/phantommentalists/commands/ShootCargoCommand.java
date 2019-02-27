/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Handler;

import edu.wpi.first.wpilibj.command.Command;

public class ShootCargoCommand extends Command {
  /**
   * It shoots the cargo into the port
   */
  private Handler handler;

  public ShootCargoCommand(Telepath r) {
    if(Parameters.HANDLER_AVAILABLE){
    handler = r.getHandler();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(handler);
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.setTimeout(1);
    if(Parameters.HANDLER_AVAILABLE){
    handler.stopCargoHandler();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Parameters.HANDLER_AVAILABLE){
    handler.shootCargo();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // LocalTime shootTime = LocalTime.now();
    // if (shootTime.getSecond() - startTime.getSecond() > Parameters.SHOOT_CARGO_TIME) {
    //   return true;
    //  }
    // return false;
    return this.isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(Parameters.HANDLER_AVAILABLE){
    handler.stopCargoHandler();
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    if(Parameters.HANDLER_AVAILABLE){
      handler.stopCargoHandler();
    }
  }
}