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

public class LiftRobotCommand extends Command {
/**
 * FIXME add comment
 */
private Elevator elevator;

  public LiftRobotCommand(Telepath r) {
    elevator = r.getElevator();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Telepath.liftLeveler.setAbsoluteTolerance(Parameters.LIFT_LEVELER_TOLERANCE);
    Telepath.liftLeveler.setInputRange(-Parameters.LIFT_LEVELER_TOLERANCE, Parameters.LIFT_LEVELER_TOLERANCE);
    Telepath.liftLeveler.setOutputRange(-Parameters.LIFTER_LIFT_MOTOR_VARIATION, Parameters.LIFTER_LIFT_MOTOR_VARIATION);
    Telepath.liftLeveler.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    elevator.setPosition(ElevatorPosition.HAB_ZONE_LEVEL_3);
    //FIXME set as a different ElevatorPosition
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (elevator.getPosition() - ElevatorPosition.HAB_ZONE_LEVEL_3.getSetPoint() <= Parameters.ELEVATOR_POSITION_ERROR) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    elevator.stopMotor();
    Telepath.liftLeveler.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
