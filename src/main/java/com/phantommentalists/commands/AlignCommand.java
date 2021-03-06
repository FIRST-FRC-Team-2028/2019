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
  private Telepath robot;

  public AlignCommand(Telepath r) {
    robot = r;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(r.getDrive());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    CameraThread cam = robot.getCameraThread();
    left = cam.getLeftline();
    right = cam.getRightline();
    // FIXME MrG says DownShift
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    robot.getDrive().alignDrive(left.angle(), right.angle(), left.x2, right.x2);
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
