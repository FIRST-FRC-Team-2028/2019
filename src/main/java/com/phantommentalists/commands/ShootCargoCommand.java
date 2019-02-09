/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;

import edu.wpi.first.wpilibj.command.Command;

public class ShootCargoCommand extends Command {
  /**
   * It shoots the cargo into the port
   */
  public ShootCargoCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Telepath.handler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Telepath.handler.shootCargo();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !Telepath.handler.isCargoHeld();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Telepath.handler.stopCargoHandler();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
