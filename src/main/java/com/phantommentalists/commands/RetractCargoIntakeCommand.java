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

public class RetractCargoIntakeCommand extends Command {
  /**
   * It brings the cargo intake back to it's starting position
   */
  private Telepath robot;
  
  public RetractCargoIntakeCommand(Telepath r) {
    robot = r;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    if(Parameters.INTAKE_AVAILABLE){
      requires(robot.getCargoIntake());
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Parameters.INTAKE_AVAILABLE){
      robot.getCargoIntake().turnOffRollers();
      robot.getCargoIntake().retract();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Parameters.INTAKE_AVAILABLE){
      return robot.getCargoIntake().isRetracted();
    }
    else return true;
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
