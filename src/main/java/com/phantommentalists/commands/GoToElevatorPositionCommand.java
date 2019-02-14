/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.ElevatorPosition;
import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class GoToElevatorPositionCommand extends Command {
  /**
   * It goes to the specified Elevator postition 
   */
  private ElevatorPosition position;
  private Elevator elevator;

  public GoToElevatorPositionCommand(ElevatorPosition position, Telepath r) {
    elevator = r.getElevator();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.position = position;
    requires(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    elevator.setPosition(position);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (elevator.getPosition() - position.getSetPoint() < Parameters.ELEVATOR_POSITION_ERROR) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    elevator.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
