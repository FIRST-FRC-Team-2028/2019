/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.commands.DefaultHandlerCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The handler controls the cargo handler, and hatch handler to get to the cargo
 * and hatch ports, and also deploys the climbing arm.
 * It deploys the climbing arm using a piston
 * It the CargoHandler
 * It extends the HatchHandler
 * 
 * It tells the HatchHandler when to load and deploy a hatch
 * It tells the CargoHandler to obtain cargo 
 * It tells the CargoHandler to shoots cargo out using motors
 */

public class Handler extends Subsystem {
  
  private CargoHandler cargoHandler;
  private DoubleSolenoid hatchPositioner;  // to position HatchHandler
  
  Telepath robot;
  private boolean isRetracted;

  /**
   * Default constructor. This method initializes all data members of the class
   */
  public Handler(Telepath r) 
  {
    if (Parameters.CARGO_HANDLER_AVAILABLE){
     cargoHandler = new CargoHandler();
    }
    robot=r;
    hatchPositioner = new DoubleSolenoid(Parameters.PneumaticChannel.HANDLER_DEPLOY.getChannel(), Parameters.PneumaticChannel.HANDLER_RETRACT.getChannel());

  }

  /** 
   * This method intentionally left blank. Handler will not have a default command.
   */
  @Override
  public void initDefaultCommand() 
  {
    setDefaultCommand(new DefaultHandlerCommand(robot));
  }
  /**
   * Updates all the internal variables
   */
     public void process()
    {
      if (Parameters.HANDLER_AVAILABLE) {
      }
    }
  
  public void deployHatchHandler()
  {
    if (Parameters.HANDLER_AVAILABLE) {
      hatchPositioner.set(Parameters.HATCHHANDLER_DEPLOY_POSITION);
      isRetracted = false;
    }
  }

  public void retractHatchHandler()
  {
    if (Parameters.HANDLER_AVAILABLE) {
      hatchPositioner.set(Parameters.HATCHHANDLER_RETRACTED_POSITION);
      isRetracted = true;
    }
  }

  public boolean isHatchHandlerRetracted()
  {
    return isRetracted;
  }

  /** 
   * Get a cargo from the cargo intake
   */
  public void loadCargo() 
  {
    cargoHandler.loadCargo();
  }

  public void stopCargoHandler() 
  {
    cargoHandler.stopMotor();
  }

  /** 
   * Shoots the cargo into the cargo ship or rocket
   */
  public void shootCargo() 
  {
    cargoHandler.shootCargo();
  }

  /** 
   * Determines if cargo is held
   * 
   * @return boolean true if cargo held, false otherwise
   */
  public boolean isCargoHeld() 
  {
    boolean held = cargoHandler.isCargoHeld();
    return held;
    //FIXME There is no sensor for the Cargo Handler
  }

}
