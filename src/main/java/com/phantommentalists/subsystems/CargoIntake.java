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
      extender = new TalonSRX(CanId.CARGO_INTAKE_EXT.getCanId());

      roller.set(ControlMode.PercentOutput, 0.0);
      roller.setNeutralMode(NeutralMode.Brake);
      roller.setInverted(Parameters.CanId.CARGO_INTAKE.isInverted());

      extender.set(ControlMode.PercentOutput, 0.0);
      extender.setNeutralMode(NeutralMode.Brake);
      extender.setInverted(Parameters.CanId.CARGO_INTAKE.isInverted());
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
      if(getCurrent() < 12){
      extender.set(ControlMode.PercentOutput, Parameters.CARGO_EXTENDER_SPEED);
      }
      else
      {
        stopExtendMotor();
      }
    }
  }

  /**
   * run extender motor to retract
   * Run to zero encoder position
   */
  public void retract() {
    if (Parameters.INTAKE_AVAILABLE) {
      //extender.set(Parameters.CARGO_INTAKE_RETRACT);
      if(getCurrent() < 12){
      extender.set(ControlMode.PercentOutput, Parameters.CARGO_EXTENDER_RETRACT_SPEED);
      }
      else
      {
        stopExtendMotor();
      }
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
   * Use slider to vary extender speed during testing
   * 
   * @return
   */
  public void setPowerExtender(double motorPower) {
    if (Parameters.INTAKE_AVAILABLE) {
      if(getCurrent() < 12){
      extender.set(ControlMode.PercentOutput, motorPower); 
      //extender.set(ControlMode.PercentOutput, 0.1); 
      SmartDashboard.putNumber("Intakepercent", motorPower);
      }
      else
      {
        stopExtendMotor();
      }
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
    }
    return false;
  }

  public boolean isAtEnd() {
    if (Parameters.INTAKE_AVAILABLE) {
      if (getCurrent() > Parameters.CARGO_INTAKE_EXTENDER_CURRENT_LIMIT) {
        return true;
      }
    }
    return false;
  }

  public double getCurrent() 
  {
      return extender.getOutputCurrent();
  }

  public void stopExtendMotor() {
    extender.set(ControlMode.PercentOutput, 0);
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
      SmartDashboard.putNumber("Cargo Intake extender: Voltage", extender.getMotorOutputVoltage());
      SmartDashboard.putNumber("Cargo Intake extender: Current", extender.getOutputCurrent());
      SmartDashboard.putNumber("Cargo Intake extender: Percent", extender.getMotorOutputPercent());
    }
  }
}