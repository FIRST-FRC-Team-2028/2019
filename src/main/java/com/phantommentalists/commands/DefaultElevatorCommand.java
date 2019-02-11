/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DefaultElevatorCommand extends Command {
  /**
   * Manually controls the elevator
   */
  public DefaultElevatorCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Telepath.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  //Use buttons to move up or down if held
  @Override
  protected void execute() {
    if (Telepath.oi.getElevatorUp().get()) {
      Telepath.elevator.setPower(Parameters.ELEVATOR_MANUAL_SPEED);
      SmartDashboard.putString("Hello", "Goodbye");
    }
    else if(Telepath.oi.getElevatorDown().get()) {
      Telepath.elevator.setPower(-Parameters.ELEVATOR_MANUAL_SPEED);
      SmartDashboard.putString("Hi", "Bye");
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
