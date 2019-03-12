/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.subsystems.Handler;

import edu.wpi.first.wpilibj.command.Command;

public class DeployHatchHandlerCommand extends Command {
  /**
   * Extends the Hatch Handler
   */
  private Handler handler;

  public DeployHatchHandlerCommand(Telepath r) {
    handler = r.getHandler();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(handler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    handler.deployHatchHandler();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !handler.isHatchHandlerRetracted();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}