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
  private boolean loadedHatch;
  public HatchHandler() {
    suction = new DoubleSolenoid(Parameters.PneumaticChannel.HANDLER_CREATE_VACUUM.getChannel(), Parameters.PneumaticChannel.HANDLER_RELEASE_VACUUM.getChannel());
    loadedHatch = false;
    //TODO How do we start the match with a hatch?
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  /**
   * Releases the vacuum on the hatch handlers
   */
  public void releaseHatch() {
    suction.set(Value.kReverse);
    loadedHatch = false;
  }
  /**
   * Applys vacuum to the hatch handlers
   */
  public void loadHatch() {
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
