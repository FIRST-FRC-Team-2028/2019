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

/**
 * Loads cargo from cargo intake
 * Shoots cargo
 * sees if cargo is held
 * 
 * Uses rollers to load cargo
 * Uses rollers to shoot cargo
 * Uses a contact switch to detect if cargo is held
 */
public class CargoHandler{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private TalonSRX rollers;
  private DigitalInput cargoSensor;

  public CargoHandler() {
    rollers = new TalonSRX(Parameters.CanId.CARGO_HANDLER.getCanId());
    cargoSensor = new DigitalInput(Parameters.CARGO_HANDLER_SENSOR);
    rollers.setInverted(Parameters.CanId.CARGO_HANDLER.isInverted());
    rollers.set(ControlMode.PercentOutput, 0.0);
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
    // FIXME Get current state from sensor
    return cargoSensor.get();
  } 
}
