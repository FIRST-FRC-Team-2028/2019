/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.phantommentalists.Parameters;
import com.phantommentalists.Parameters.CanId;

public class CargoIntake extends Subsystem {
  TalonSRX roller;
  TalonSRX extender;

  /**
   * Default constructor
   */
  public CargoIntake() {
    if (Parameters.INTAKE_AVAILABLE) {
      roller = new TalonSRX(CanId.CARGO_INTAKE.getCanId());
      extender = new TalonSRX(CanId.CARGO_HANDLER.getCanId());

      roller.set(ControlMode.PercentOutput, 0.0);
      roller.setNeutralMode(NeutralMode.Brake);
      roller.setInverted(Parameters.CanId.CARGO_INTAKE.isInverted());

      extender.set(ControlMode.PercentOutput, 0.0);
      extender.setNeutralMode(NeutralMode.Brake);
      extender.setInverted(Parameters.CanId.CARGO_HANDLER.isInverted());
      extender.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
      extender.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
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

  public void deploy() {
    if (Parameters.INTAKE_AVAILABLE) {
      extender.set(ControlMode.PercentOutput, Parameters.CARGO_INTAKE_DEPLOY_SPEED);
    }
  }

  public void retract() {
    if (Parameters.INTAKE_AVAILABLE) {
      extender.set(ControlMode.PercentOutput, Parameters.CARGO_INTAKE_RETRACT_SPEED);
    }
  }

  public boolean isDeployed() {
    if (Parameters.INTAKE_AVAILABLE) {
      SensorCollection sc = extender.getSensorCollection();
      if (sc != null)
      {
        return sc.isFwdLimitSwitchClosed();
      }
    }
    return false;
  }

  public boolean isRetracted() {
    if (Parameters.INTAKE_AVAILABLE) {
      SensorCollection sc = extender.getSensorCollection();
      if (sc != null)
      {
        return sc.isRevLimitSwitchClosed();
      }
    }
    return false;
  }

  @Override
  public void initDefaultCommand() {
  }
}