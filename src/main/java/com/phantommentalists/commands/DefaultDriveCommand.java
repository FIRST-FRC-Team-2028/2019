/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.phantommentalists.OI;
import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;

public class DefaultDriveCommand extends Command {
  /**
   * An example command.  You can replace me with your own command.
   */
  private OI oi;
  private Telepath robot;

  public DefaultDriveCommand(Telepath r) {
    oi = r.getOI();
    robot = r;
    // Use requires() here to declare subsystem dependencies
    requires(robot.getDrive());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  // Limit the drive speed when the elevator is high to avoid tipping
  @Override
  protected void execute() {
    double multiplier = Parameters.DRIVE_SPEED_LIMITER_ELEVATOR_CG
     + (1.0 - Parameters.DRIVE_SPEED_LIMITER_ELEVATOR_CG)*(1.0 - robot.getElevator().getCGHeight());
    robot.getDrive().tankDrive(Parameters.DRIVE_LIMITER_MULTIPLIER*multiplier*oi.getLeftStick(), Parameters.DRIVE_LIMITER_MULTIPLIER*multiplier*oi.getRightStick());
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
