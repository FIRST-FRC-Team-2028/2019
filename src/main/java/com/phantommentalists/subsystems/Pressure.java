/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.phantommentalists.Parameters;

/**
 * Use the PCM to keep the pressure tank pressurized so the 
 * solenoids have power.
 * But during one of those events when billions of motors
 * need power,
 * ensure that the Compressor pump does not turn on.
 */
public class Pressure extends Subsystem {
  Compressor compressor;

  public Pressure(){
    if(Parameters.COMPRESSOR_AVAILABLE){
        compressor = new Compressor(0);
    }
  }

  public void disenable(boolean offOn){
    compressor.setClosedLoopControl(offOn);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
