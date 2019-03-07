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

/**
 * Retracts the Hatch Handler
 */
public class HatchHandlerRetractCommand extends Command {
  private Handler handler;
  public HatchHandlerRetractCommand(Telepath r) {
    handler = r.getHandler();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(handler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    handler.setMode(AutoMode.AUTO);
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    handler.retractHatchHandler();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return handler.isHatchHandlerRetracted();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    handler.stopHatchHandler();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    handler.stopHatchHandler();
  }
}
