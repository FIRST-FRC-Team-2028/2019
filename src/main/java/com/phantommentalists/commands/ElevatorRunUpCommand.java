/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.commands;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorRunUpCommand extends Command {
  /**
   * Run the elevator up open loop
   */
  private Elevator elevator;
  double power;

  public ElevatorRunUpCommand(Telepath r) {
    elevator = r.getElevator();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(elevator);
    System.out.println("UpElevatorRun construct");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    elevator.setMode(AutoMode.MANUAL);
    System.out.println("ElevatorRunUp init");
    power = Parameters.ELEVATOR_MANUAL_SPEED/2.0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    power = power + Parameters.ELEVATOR_MANUAL_SPEED/20.0;
    power = Math.min(power, Parameters.ELEVATOR_MANUAL_SPEED);
    elevator.setPower(power);
    System.out.println("UpElevatorRun exec");
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
    System.out.println("UpElevatorRun end");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("UpElevatorRun interrupted");
  }
}
