/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToHatchCommand extends Command {
  /**
   * Uses the align method to line up with the tape
   * This is because it is able to place and pick up hatches accurately
   */

  public DriveToHatchCommand(Telepath r) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(r.getDrive());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //TODO use Ricky's Align method
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
