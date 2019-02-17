/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.CargoIntake;

import edu.wpi.first.wpilibj.command.Command;

public class DeployCargoIntakeCommand extends Command {
  /**
   * FIXME Comment
   */
  private CargoIntake cargoIntake;

  public DeployCargoIntakeCommand(Telepath r) {
    cargoIntake = r.getCargoIntake();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(cargoIntake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    cargoIntake.deploy();
    // FIXME cargoIntake.turnOnRollers();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return cargoIntake.isDeployed();
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
