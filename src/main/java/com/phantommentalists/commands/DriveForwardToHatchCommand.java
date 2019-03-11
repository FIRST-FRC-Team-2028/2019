/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardToHatchCommand extends Command {
  /**
   * Drives forward to place hatch
   * Runs by a timer at a set speed
   */
  Drive d;
  public DriveForwardToHatchCommand(Drive d) {
    this.d = d;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(d);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(Parameters.DRIVE_TO_HATCH_TIMER);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    d.goStraight(Parameters.DRIVE_TO_HATCH_SPEED);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    d.goStraight(0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    d.goStraight(0.0);
  }
}
