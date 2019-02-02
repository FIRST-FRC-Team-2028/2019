/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.phantommentalists.Parameters;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class CargoHandler extends Subsystem{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX rollers;
  DigitalInput cargoSensor;

  public CargoHandler() {
    rollers = new TalonSRX(Parameters.CanId.CARGO_HANDLER.getCanId());
    cargoSensor = new DigitalInput(Parameters.CARGO_HANDLER_SENSOR);
    rollers.setInverted(Parameters.CanId.CARGO_HANDLER.isInverted());
  }

  public void loadCargo() {
    rollers.set(ControlMode.PercentOutput, Parameters.CARGO_HANDLER_INTAKE_SPEED);
  }

  public void shootCargo() {
    rollers.set(ControlMode.PercentOutput, Parameters.CARGO_HANDLER_SHOOT_SPEED);
  }

  public void stopMotor() {
    rollers.set(ControlMode.PercentOutput, 0);
  }
  public boolean isCargoHeld() {
    // FIX ME: Get current state from sensor
    return cargoSensor.get();
  }

  @Override
  protected void initDefaultCommand() {
  }
}