/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.phantommentalists.OI;
import com.phantommentalists.Telepath;


/**
 * An example command.  You can replace me with your own command.
 */
public class DefaultDriveCommand extends Command {

  public OI oi = Telepath.oi;
  public DefaultDriveCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(Telepath.drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Telepath.drive.tankDrive(oi.getLeftStick(), oi.getRightStick());
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
