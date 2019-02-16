/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.OI;
import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DefaultElevatorCommand extends Command {
  /**
   * Manually controls the elevator
   */
  private Elevator elevator;
  private OI oi;

  public DefaultElevatorCommand(Telepath r) {
    elevator = r.getElevator();
    oi = r.getOI();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  //Use buttons to move up or down if held
  @Override
  protected void execute() {
    if(elevator.getMode() == AutoMode.ZEROING) return;
    if (oi.getElevatorUp().get()) {
      elevator.setPower(Parameters.ELEVATOR_MANUAL_SPEED);
 
    }
    else if(oi.getElevatorDown().get()) {
      elevator.setPower(-Parameters.ELEVATOR_MANUAL_SPEED);

    }else
    {
      elevator.stopMotor();
    }
    SmartDashboard.putNumber("Elevator Position", elevator.getPosition());
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
