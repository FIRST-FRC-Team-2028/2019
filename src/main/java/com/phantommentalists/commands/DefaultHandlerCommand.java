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
import com.phantommentalists.subsystems.Handler;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Default Command to be run by the Handler subsystem
 * 
 */
public class DefaultHandlerCommand extends Command {
  private Handler handler;
  private OI oi;
  private Timer timer;
  Joystick buttons;
  public DefaultHandlerCommand(Telepath r) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    handler=r.getHandler();
    oi=r.getOI();
    requires(handler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    buttons = oi.getButtonBoxleft();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // handler.setLeadScrewPower(oi.getSlider());
    // System.out.println("pizza time");
    //FIXME switch to a button or such for competition
    //make sure that the hatch handler is retracted 
    //before the end of the match
    double matchTime = timer.getMatchTime();
    if (matchTime < Parameters.HATCH_RETURN_BEFORE_END_MATCH) {
      handler.retractHatch();
      if (handler.isHatchRetracted() == true) {
        handler.stopHatchHandler();
      }
    }
    if(buttons.getRawButton(6))
    {
      handler.loadCargo();
    }
    else
    {
      handler.stopCargoHandler();
    }
    // handler.setCargoSpeed(oi.getSlider());
    SmartDashboard.putNumber("Cargo Handler Percent", handler.getPercentage());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    handler.stopHatchHandler();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    handler.stopHatchHandler();
  }
}
