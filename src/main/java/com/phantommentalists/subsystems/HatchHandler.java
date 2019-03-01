/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.phantommentalists.Parameters;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The hatch handler loads a hatch and releases it. 
 * It loads a hatch with vacuum.
 * It releases a hatch with a piston.
 */
public class HatchHandler extends Subsystem {

  private DoubleSolenoid suction;
  private DoubleSolenoid vent;
  private boolean loadedHatch;
  double timeout;
  
  public HatchHandler() {
    suction = new DoubleSolenoid(Parameters.PneumaticChannel.HANDLER_CREATE_VACUUM.getChannel(), Parameters.PneumaticChannel.HANDLER_RELEASE_VACUUM.getChannel());
    vent = new DoubleSolenoid(Parameters.PneumaticChannel.HANDLER_VENT_CLOSED.getChannel(), Parameters.PneumaticChannel.HANDLER_VENT_OPEN.getChannel());
    loadedHatch = false;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  /**
   * Releases the vacuum on the hatch handlers
   * By opening the vent
   * Cycle the vaccuum solenoid
   */
  public void releaseHatch() {
    suction.set(Value.kReverse);
    vent.set(Value.kReverse);
    loadedHatch = false;
  }
  /**
   * Loading the hatch is a 2 step process
   * Close the vent
   * After a small delay apply a vaccuum
   */
  public void closeVent() {
    vent.set(Value.kForward);
    loadedHatch = true;
  }

  public void applyVaccuum() {
    suction.set(Value.kForward);
    loadedHatch = true;
  }

  public boolean hasVacuum() {
    /** 
     * We can't check; we have to watch what we've done
     */
    return loadedHatch;
  }
}
