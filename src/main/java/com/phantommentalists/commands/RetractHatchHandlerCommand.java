/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;

import edu.wpi.first.wpilibj.command.Command;

public class RetractHatchHandlerCommand extends Command {
  /**
   * It releases the hatch panel onto the hatch
   * By either retracting the hatch handler or moving the elevator down then retracting the Hatch Handler
   */
  private Telepath robot;
  
  public RetractHatchHandlerCommand(Telepath r) {
    robot = r;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(robot.getHandler());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    robot.getHandler().retractHatchHandler();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return robot.getHandler().isHatchHandlerRetracted();
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
