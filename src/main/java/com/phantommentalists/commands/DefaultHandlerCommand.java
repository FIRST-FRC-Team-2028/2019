/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.subsystems.Handler;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Default Command to be run by the Handler subsystem
 * Retracts the Hatch Handler before the end of the match
 */
public class DefaultHandlerCommand extends Command {
  private Handler handler;
  private Timer timer;
  Joystick buttons;
  public DefaultHandlerCommand(Telepath r) {
    buttons = r.getOI().getButtonBoxleft();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    handler=r.getHandler();
    requires(handler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //buttons = oi.getButtonBoxleft();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //make sure that the hatch handler is retracted 
    //before the end of the match
    double matchTime = timer.getMatchTime();
    if (matchTime < Parameters.HATCH_RETURN_BEFORE_END_MATCH) {
      handler.retractHatchHandler();
    }
    if(buttons.getRawButton(Parameters.BUTTON_CARGO_RUN_BOTH_ROLLERS))
    {
      handler.loadCargo();
    }
    else
    {
      handler.stopCargoHandler();
    }
    // handler.setCargoSpeed(oi.getSlider());
    //SmartDashboard.putNumber("Cargo Handler Percent", handler.getPercentage());
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
