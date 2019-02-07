/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.phantommentalists.Parameters;
import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.Parameters.PneumaticChannel;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

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
  
  private HatchHandler hatchHandler;
  private CargoHandler cargoHandler;
  private TalonSRX leadScrewMotor;  // to position HatchHandler

  private Solenoid solenoid;

  private boolean zeroed;
  
 

  /**
   * Default constructor. This method initializes all data members of the class
   */
  public Handler() {
    cargoHandler = new CargoHandler();
    hatchHandler = new HatchHandler();

    leadScrewMotor = new TalonSRX(Parameters.CanId.HATCH_LEAD_SCREW_MOTOR.getCanId());
    leadScrewMotor.config_kP(0, Parameters.Pid.HATCH_LEADSCREW.getP(), 0);
    leadScrewMotor.config_kI(0, Parameters.Pid.HATCH_LEADSCREW.getI(), 0);
    leadScrewMotor.config_kD(0, Parameters.Pid.HATCH_LEADSCREW.getD(), 0);
    leadScrewMotor.config_kF(0, Parameters.Pid.HATCH_LEADSCREW.getF(), 0);
    leadScrewMotor.setNeutralMode(NeutralMode.Brake);
    leadScrewMotor.selectProfileSlot(0, 0);
    // leadScrewMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    // leadScrewMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    leadScrewMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

    solenoid = new Solenoid(Parameters.PneumaticChannel.HANDLER_CLIMBER_ARM.getChannel());

    zeroed = false;
  }

  /** 
   * This method intentionally left blank. Handler will not have a default command.
   */
  @Override
  public void initDefaultCommand() {
    // NOOP
  }
  /**
   * Updates all the internal variables
   */
     public void process()
    {
        if (Parameters.HANDLER_AVAILABLE)
        {
            if (!zeroed)
            {
                leadScrewMotor.set(ControlMode.PercentOutput, Parameters.HATCHHANDLER_ZEROING_SPEED);
                SensorCollection sc = leadScrewMotor.getSensorCollection();
                if (sc != null)
                {
                    if (sc.isRevLimitSwitchClosed())
                    {
                        leadScrewMotor.set(ControlMode.PercentOutput, 0.0);
                        zeroPosition();
                    }
                }
            }
        }
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
    hatchHandler.loadHatch();
  }

  /**
   * Release the vacuum for each of the 3 hatch handlers.
   */
  public void releaseHatch() {
    hatchHandler.releaseHatch();
  }

  public void forwardHatch()
  {
    // Need speed for lead screw motor
    leadScrewMotor.set(ControlMode.Position, Parameters.HATCHHANDLER_DEPLOY_POSITION);
  }

  public void retractHatch()
  {
    leadScrewMotor.set(ControlMode.Position, Parameters.HATCHHANDLER_ZERO_POSITION);
  }

  public void zeroPosition() {
    if (Parameters.HANDLER_AVAILABLE) {
      zeroed = true;
      leadScrewMotor.setSelectedSensorPosition(0);
    }
  }

  public boolean isHatchDeployed()
  {
    if (leadScrewMotor.getSelectedSensorPosition() - Parameters.HATCHHANDLER_DEPLOY_POSITION <= 20) {
      return true;
    }
    return false;
  }

  public boolean isHatchretracted()
  {
    if (leadScrewMotor.getSelectedSensorPosition() - Parameters.HATCHHANDLER_ZERO_POSITION <= 20) {
      return true;
    }
    return false;
  }

  /** 
   * Get a cargo from the cargo intake
   */
  public void loadCargo() {
    cargoHandler.loadCargo();
  }

  public void stopCargoHandler() {
    cargoHandler.stopMotor();
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

  public void extendClimbingArms() {
    solenoid.set(true);
  }
}
