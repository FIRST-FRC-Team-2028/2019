/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.CameraThread;
import com.phantommentalists.Telepath;
import com.phantommentalists.CameraAlignment.Line;

import edu.wpi.first.wpilibj.command.Command;

public class AlignCommand extends Command {

  Line left;
  Line right;
  static boolean isdone;
  public AlignCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Telepath.drive);
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    CameraThread cam = Telepath.cameraThread;
    left = cam.getLeft();
    right = cam.getRight();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    isdone = Telepath.drive.alignDrive(left.angle(), right.angle(), left.x2, right.x2);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isdone;
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
