/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorHoldPositionTimeCommand extends Command {

  Telepath robot;
  Elevator elevator;
  double setpoint;
  double timeout;
  public ElevatorHoldPositionTimeCommand(Telepath r, double timeout_) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    robot = r;
    elevator = robot.getElevator();
    timeout = timeout_;
    requires(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setpoint = elevator.getPosition();
    this.setTimeout(timeout);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    elevator.holdPosition(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    elevator.setPower(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    elevator.setPower(0);
  }
}
