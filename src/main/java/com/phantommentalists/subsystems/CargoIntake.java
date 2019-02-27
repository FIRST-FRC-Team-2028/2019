/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.CanId;
import com.phantommentalists.commands.DefaultCargoIntakeCommand;

/*
 * CargoIntake is the mechanism that pulls the cargo off the floor into the CargoHandler.
 * It will extend and retract.
 * It will roll the ball up to engage the CargoHandler
 */
public class CargoIntake extends Subsystem {
  TalonSRX roller;
  TalonSRX extender;
  private Telepath robot;

  /**
   * Default constructor
   */
  public CargoIntake(Telepath r) {
    if (Parameters.INTAKE_AVAILABLE) {
      robot = r;
      roller = new TalonSRX(CanId.CARGO_INTAKE.getCanId());
      //FIXME
      //extender = new DoubleSolenoid(Parameters.PneumaticChannel.CARGO_INTAKE_EXTENDER.getChannel(), Parameters.PneumaticChannel.CARGO_INTAKE_RETRACT.getChannel());

      roller.set(ControlMode.PercentOutput, 0.0);
      roller.setNeutralMode(NeutralMode.Brake);
      roller.setInverted(Parameters.CanId.CARGO_INTAKE.isInverted());

      //extender.set(Parameters.CARGO_INTAKE_RETRACT);
    }
  }

  public void turnOnRollers() {
    if (Parameters.INTAKE_AVAILABLE) {
      roller.set(ControlMode.PercentOutput, Parameters.CARGO_INTAKE_ROLLER_SPEED);
    }
  }

  public void turnOffRollers() {
    if (Parameters.INTAKE_AVAILABLE) {
      roller.set(ControlMode.PercentOutput, 0.0);
    }
  }

  /**
   * run the extender motor
   * Run to encoder deployed position 
   */
  public void deploy() {
    if (Parameters.INTAKE_AVAILABLE) {
      //extender.set(Parameters.CARGO_INTAKE_EXTEND);
    }
  }

  /**
   * run extender motor to retract
   * Run to zero encoder position
   */
  public void retract() {
    if (Parameters.INTAKE_AVAILABLE) {
      //extender.set(Parameters.CARGO_INTAKE_RETRACT);
    }
  }

  /**
   * Use slider to vary roller speed during testing
   * 
   * @return
   */
  public void setPower(double motorPower) {
    if (Parameters.INTAKE_AVAILABLE) {
      roller.set(ControlMode.PercentOutput, motorPower); 
    }
  }

  /**
   * Tells if the cargo intake is deployed by reading the 
   * state of the double solenoid.
   * Side Effects: isDeployed may return true despite cargo 
   * intake not being fully deployed. Actual deploy time
   * may be off by 0.5 to 1 seconds.
   * 
   * @return true if extender is extended. False otherwise
   */
  public boolean isDeployed() {
    if (Parameters.INTAKE_AVAILABLE) {
      Value ds = Value.kForward; //FIXME extender.get();
      if (ds == Value.kForward)
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Tells if the cargo intake is retracted by reading the 
   * state of the double solenoid.
   * Side Effects: isRetracted may return true despite cargo 
   * intake not being fully retracted. Actual deploy time
   * may be off by 0.5 to 1 seconds.
   * 
   * @return true if extender is retracted. False otherwise
   */
  public boolean isRetracted() {
    if (Parameters.INTAKE_AVAILABLE) {
      Value ds = Value.kReverse; //FIXME extender.get();
      if (ds == Value.kReverse)
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DefaultCargoIntakeCommand(robot));
  }

  public void process() {
    if(Parameters.INTAKE_AVAILABLE) {
      SmartDashboard.putNumber("Cargo Intake: Voltage", roller.getMotorOutputVoltage());
      SmartDashboard.putNumber("Cargo Intake: Current", roller.getOutputCurrent());
      SmartDashboard.putNumber("Cargo Intake: Percent", roller.getMotorOutputPercent());
    }
  }
}