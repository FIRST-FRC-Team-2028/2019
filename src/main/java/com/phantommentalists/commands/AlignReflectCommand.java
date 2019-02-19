/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.CameraThread;
import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Drive;
import edu.wpi.first.wpilibj.command.Command;

public class AlignReflectCommand extends Command {

  com.phantommentalists.TapePipeline.Line leftLine;

  com.phantommentalists.TapePipeline.Line rightLine;

  double ultrasonic;
  CameraThread cam;

  Drive drive;
  Telepath robot;

  public AlignReflectCommand(Telepath robot) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.robot = robot;
    cam = robot.getCameraThread();
    this.drive = robot.getDrive();
    requires(drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    CameraThread cam = robot.getCameraThread();
    cam.start();
    leftLine = cam.getLeft();
    rightLine = cam.getRight();
    // FIXME MrG says DownShift
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Parameters.ULTRASONIC_AVAILABLE){
      ultrasonic = ((robot.getUltrasonic().getValue()-59.55)/7.727)+6;
      drive.reflectiveAlignDrive(leftLine.x1, ultrasonic);
    }
    drive.reflectiveAlignDrive(leftLine.x1);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    cam.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    cam.stop();
  }
}
