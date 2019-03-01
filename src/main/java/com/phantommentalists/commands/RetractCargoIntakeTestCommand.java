/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.CargoIntake;

import edu.wpi.first.wpilibj.command.Command;

public class RetractCargoIntakeTestCommand extends Command {
  CargoIntake cargoIntake;
  public RetractCargoIntakeTestCommand(Telepath r) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    if(Parameters.INTAKE_AVAILABLE){
      cargoIntake = r.getCargoIntake();
      requires(cargoIntake);
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
      cargoIntake.retract();
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
    cargoIntake.stopExtendMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    cargoIntake.stopExtendMotor();
  }
}
