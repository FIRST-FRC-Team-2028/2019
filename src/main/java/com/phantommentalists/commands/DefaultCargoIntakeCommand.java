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
import com.phantommentalists.subsystems.CargoIntake;

import edu.wpi.first.wpilibj.command.Command;

public class DefaultCargoIntakeCommand extends Command {
  /**
   * Turns on the rollers of the Cargo Intake manually with the slider
   * 
   */
  private CargoIntake cargoIntake;
  private OI oi;

  public DefaultCargoIntakeCommand(Telepath r) {
    if(Parameters.INTAKE_AVAILABLE){
      cargoIntake = r.getCargoIntake();
      // Use requires() here to declare subsystem dependencies
      // eg. requires(chassis);
      requires(cargoIntake);
    }
    oi = r.getOI();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Parameters.INTAKE_AVAILABLE){
      cargoIntake.turnOffRollers();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // slider input = -1 to 1, motor out = 0 to 1
    // for testing: cargoIntake.setPower((oi.getSlider()+1)/2);
    if(Parameters.INTAKE_AVAILABLE){
      cargoIntake.setPower(0.0);
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
