/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Allows manual control of the lifter
 */
public class DefaultLifterCommand extends Command {
  public DefaultLifterCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Telepath.lifter);
  }

  // Called just before this Command runs the first time
  //sets remaining gyro result to 0
  @Override
  protected void initialize() {
    Telepath.lifter.pidWrite(0.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Telepath.oi.buttonLifterRetract.get()) {
      Telepath.lifter.retract();
    }
    if (Telepath.oi.buttonLifterDeploy.get()) {
      Telepath.lifter.deploy();
    }
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
