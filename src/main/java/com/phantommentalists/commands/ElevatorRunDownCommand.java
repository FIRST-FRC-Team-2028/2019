/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorRunDownCommand extends Command {
  /**
   * Run the elevator up open loop
   */
  private Elevator elevator;

  public ElevatorRunDownCommand(Telepath r) {
    elevator = r.getElevator();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    elevator.setMode(AutoMode.MANUAL);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    elevator.setPower(-1.0*Parameters.ELEVATOR_MANUAL_SPEED);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
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
