/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.phantommentalists.Parameters;
import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.commands.DefaultHandlerCommand;

import edu.wpi.first.wpilibj.Solenoid;
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
  
  private HatchHandler hatchHandler;
  private CargoHandler cargoHandler;
  private TalonSRX leadScrewMotor;  // to position HatchHandler

  private Solenoid solenoid;

  private AutoMode mode;
  
  private boolean zeroed;
  Telepath robot;

  /**
   * Default constructor. This method initializes all data members of the class
   */
  public Handler(Telepath r) 
  {
    if (Parameters.CARGO_HANDLER_AVAILABLE){
     cargoHandler = new CargoHandler();
    }
    if(Parameters.HATCH_HANDLER_AVAILABLE){
      hatchHandler = new HatchHandler();
    }
    robot=r;

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
    leadScrewMotor.setSensorPhase(true);
    leadScrewMotor.setInverted(true);
    // leadScrewMotor.configForwardSoftLimitThreshold(Parameters.HANDLER_LIMIT);
    leadScrewMotor.configForwardSoftLimitThreshold(Parameters.HATCHHANDLER_DEPLOY_POSITION);
    leadScrewMotor.configReverseSoftLimitThreshold(Parameters.HATCHHANDLER_ZERO_POSITION);
    leadScrewMotor.configForwardSoftLimitEnable(false);
    leadScrewMotor.configReverseSoftLimitEnable(false);
    // TODO set actual encoder values for limit
    //
    solenoid = new Solenoid(Parameters.PneumaticChannel.HANDLER_CLIMBER_ARM.getChannel());

    mode = AutoMode.ZEROING;
    //FIXME Manual mode is only for testing, zeroing mode is for competition
    mode = AutoMode.MANUAL;
    zeroed = false;
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
        if (mode == AutoMode.ZEROING) {
          leadScrewMotor.set(ControlMode.PercentOutput, Parameters.HATCHHANDLER_ZEROING_SPEED);
          Double leadScrewMotorCurrent = leadScrewMotor.getOutputCurrent();
          //FIXME Make sure 2.0 is a good threshold for current
          if (leadScrewMotorCurrent >= Parameters.HATCHHANDLER_ZEROING_CURRENT_LIMIT) {
            leadScrewMotor.set(ControlMode.PercentOutput, 0.0);
            zeroPosition();
          }
        }

        SmartDashboard.putNumber("Hatch Handler: Current", leadScrewMotor.getOutputCurrent());
        SmartDashboard.putNumber("Hatch Handler: Position", leadScrewMotor.getSensorCollection().getQuadraturePosition());
        SmartDashboard.putString("Hatch Handler: Mode", mode.toString());
      }
    }

  /** 
   * TBD TODO
   * 
   * @param AutoMode sets the auto pilot mode for the handler
   */
  public void setMode(AutoMode mode) 
  {
    if (mode == AutoMode.AUTO && !zeroed)
    {
      return;
    }
    this.mode = mode;
  }
  
  /** 
   * Create a vacuum for each of the 3 hatch handlers.
   * Note: There is currently no way to know if a hatch handler is sealed on the hatch.
   * Loading a hatch is a 2 step process
   * Close the vent
   * After a small delay apply a vaccuum
   */
  public void applyVaccuum() 
  {
    hatchHandler.applyVaccuum();
  }

  /**
   * Loading a hatch is a 2 step process
   * Close the vent
   * After a small delay apply a vaccuum
   */
  public void closeVent()
  {
    hatchHandler.closeVent();
  }

  /**
   * Release the vacuum for each of the 3 hatch handlers.
   */
  public void releaseHatch() 
  {
    hatchHandler.releaseHatch();
  }

  public boolean isHatchLoaded()
  {
    return hatchHandler.hasVacuum();
  }

  public void deployHatchHandler()
  {
    // Need speed for lead screw motor
    if (Parameters.HANDLER_AVAILABLE) {
      if (mode == AutoMode.AUTO) {
        leadScrewMotor.set(ControlMode.Position, Parameters.HATCHHANDLER_DEPLOY_POSITION);
      }
    }
  }

  public void retractHatch()
  {
    if (Parameters.HANDLER_AVAILABLE) {
      if (mode == AutoMode.AUTO) {
        leadScrewMotor.set(ControlMode.Position, Parameters.HATCHHANDLER_RETRACTED_POSITION);
      }
    }
  }

  public void setLeadScrewPower(double power) 
  {
    if (Parameters.HANDLER_AVAILABLE) {
      if (mode == AutoMode.MANUAL) {
        leadScrewMotor.set(ControlMode.PercentOutput, power);
      }
    }
  }

  public void zeroPosition() 
  {
    if (Parameters.HANDLER_AVAILABLE) 
    {
      zeroed = true;
      mode = AutoMode.MANUAL;
      leadScrewMotor.setSelectedSensorPosition(0);
    }
  }

  public boolean isHatchDeployed()
  {
    if (Math.abs(leadScrewMotor.getSelectedSensorPosition() - Parameters.HATCHHANDLER_DEPLOY_POSITION) <= Parameters.HATCHHANDLER_SET_POINT_CLOSE) {
      return true;
    }
    return false;
    // FIXME please
    // return true;
  }

  public boolean isHatchRetracted()
  {
    if (Math.abs(leadScrewMotor.getSelectedSensorPosition() - Parameters.HATCHHANDLER_ZERO_POSITION) <= Parameters.HATCHHANDLER_SET_POINT_CLOSE) {
      return true;
    }
    return false;
  }

  public void stopHatchHandler()
  {
    leadScrewMotor.set(ControlMode.PercentOutput, 0);
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

  public void extendClimbingArms() 
  {
    solenoid.set(true);
  }
}
