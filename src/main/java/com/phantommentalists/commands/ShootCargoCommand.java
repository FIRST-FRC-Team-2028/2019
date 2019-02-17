/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Handler;

import edu.wpi.first.wpilibj.command.Command;

import java.time.*;

public class ShootCargoCommand extends Command {
  /**
   * It shoots the cargo into the port
   */
  private Handler handler;

  private LocalTime startTime;

  public ShootCargoCommand(Telepath r) {
    handler = r.getHandler();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(handler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startTime = LocalTime.now();
    handler.stopCargoHandler();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    handler.shootCargo();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // LocalTime shootTime = LocalTime.now();
    // if (shootTime.getSecond() - startTime.getSecond() > Parameters.SHOOT_CARGO_TIME) {
    //   return true;
    // }
    // return false;
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    handler.stopCargoHandler();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    handler.stopCargoHandler();
  }
}
