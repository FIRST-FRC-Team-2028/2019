/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Handler;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoHandlerLoadCommand extends CommandGroup {
  /**
   * FIXME Comment
   */
  private Handler handler;

  public CargoHandlerLoadCommand(Telepath r) {
    handler = r.getHandler();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(handler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    handler.stopCargoHandler();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Cargo Handler turns on rollers
    handler.loadCargo();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // return handler.isCargoHeld();
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    handler.stopCargoHandler();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    handler.stopCargoHandler();
  }
}
