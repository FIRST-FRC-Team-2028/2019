/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.Parameters.PneumaticChannel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The handler controls the cargo handler, and hatch handler to get to the cargo
 * and hatch ports, and also deploys the climbing arm.
 * It deploys the climbing arm using a piston
 * It manipulates the CargoHandler
 * It manipulates the HatchHandler
 * 
 * It needs to use a vacuum to control the hatch
 * It uses a piston(?) to release the hatch
 * It uses the CargoIntake and motors to obtain cargo 
 * It shoots cargo out using motors
 */

public class Handler extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  /** Handler is composed of 3 hatchHandler objects */
  private HatchHandler hatchHandler[];

  /** Handler is composed of 1 cargoHandler */
  private CargoHandler cargoHandler;
  
  private DoubleSolenoid suction;

  /**
   * Default constructor. This method initializes all data members of the class
   */
  public Handler() {
    cargoHandler = new CargoHandler();
    hatchHandler = new HatchHandler[3];
    hatchHandler[0] = new HatchHandler();
    hatchHandler[1] = new HatchHandler();
    hatchHandler[2] = new HatchHandler();

    suction = new DoubleSolenoid(PneumaticChannel.HANDLER_CREATE_VACUUM.getChannel(), PneumaticChannel.HANDLER_RELEASE_VACUUM.getChannel());
  }

  /** 
   * This method intentionally left blank. Handler will not have a default command.
   */
  @Override
  public void initDefaultCommand() {
    // NOOP
  }

  public void process() {

  }

  /** 
   * Activates a solenoid to provide vacuum for the hatch handler
   */
  public void releaseVacuum() {
    suction.set(Value.kReverse);
  }

  /** 
   * Activates a solenoid to remove vacuum for the hatch handler
   */
  public void createVacuum() {
    suction.set(Value.kForward);
  }
  
  /** 
   * TBD
   * 
   * @param AutoMode sets the auto pilot mode for the handler
   */
  public void setMode(AutoMode autoMode) {

  }
  
  /** 
   * Create a vacuum for each of the 3 hatch handlers.
   * Note: There is currently no way to know if a hatch handler is sealed on the hatch.
   */
  public void loadHatch() {
    hatchHandler[0].loadHatch();
    hatchHandler[1].loadHatch();
    hatchHandler[2].loadHatch();
    createVacuum();
  }

  /**
   * Release the vacuum for each of the 3 hatch handlers.
   */
  public void releaseHatch() {
    hatchHandler[0].releaseHatch();
    hatchHandler[1].releaseHatch();
    hatchHandler[2].releaseHatch();
    releaseVacuum();
  }

  /** 
   * Get a cargo from the cargo intake
   */
  public void loadCargo() {
    cargoHandler.loadCargo();
  }

  /** 
   * Shoots the cargo into the cargo ship or rocket
   */
  public void shootCargo() {
    cargoHandler.shootCargo();
  }

  /** 
   * Determines if cargo is held
   * 
   * @return boolean true if cargo held, false otherwise
   */
  public boolean isCargoHeld() {
    boolean held = cargoHandler.isCargoHeld();
    return held;
  }
}
