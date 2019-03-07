/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Drive;
import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class GrabHatchCommand extends Command {
  /**
   * Retrives the hatch from either the Cargo ship or the Loading Station
   * 
   */
  private Elevator elevator;
  private Drive drive;
  
  public GrabHatchCommand(Telepath r) {
    elevator = r.getElevator();
    drive = r.getDrive();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(elevator);
    requires(drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // return handler.isHatchLoaded();
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
