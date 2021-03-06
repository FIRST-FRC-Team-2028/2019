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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ExtendCargoIntakeTestCommand extends Command {
  CargoIntake cargoIntake;
  Timer timer;

  public ExtendCargoIntakeTestCommand(Telepath r) {
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
      cargoIntake.deploy();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // return cargoIntake.isAtEnd();
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("extendCargoIntakeCommand end method");
    cargoIntake.stopExtendMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("extendCargoIntakeCommand interrupt method");
    cargoIntake.stopExtendMotor();
  }
}
