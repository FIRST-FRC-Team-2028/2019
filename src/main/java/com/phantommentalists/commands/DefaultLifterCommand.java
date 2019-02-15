/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.OI;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.Command;

public class DefaultLifterCommand extends Command {
  /**
  * Allows manual control of the lifter
  */
  private Lifter lifter;
  private OI oi;

  public DefaultLifterCommand(Telepath r) {
    lifter = r.getLifter();
    oi = r.getOI();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(lifter);
  }

  // Called just before this Command runs the first time
  //sets remaining gyro result to 0
  @Override
  protected void initialize() {
    lifter.pidWrite(0.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (oi.buttonLifterRetract.get()) {
      lifter.retract();
    }
    if (oi.buttonLifterDeploy.get()) {
      lifter.deploy();
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
