/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
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
import com.phantommentalists.Parameters.ElevatorPosition;
import com.phantommentalists.Parameters.Pid;
import com.phantommentalists.commands.DefaultElevatorCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem
{
    TalonSRX upDown;
    //boolean fwdlimitclosed;
    ElevatorPosition setpoint;
    boolean zeroed;
    AutoMode mode;

    /**
     * Default constructor
     */
    public Elevator()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
        {
            upDown = new TalonSRX(Parameters.CanId.ELEVATOR.getCanId());
            upDown.selectProfileSlot(1, 0);
            upDown.config_kP(1, Pid.ELEVATOR.getP(), 0);
			upDown.config_kI(1, Pid.ELEVATOR.getI(), 0);
		    upDown.config_kD(1, Pid.ELEVATOR.getD(), 0);
		    upDown.config_kF(1, Pid.ELEVATOR.getF(), 0);
		    upDown.set(ControlMode.PercentOutput, 0);
            upDown.setNeutralMode(NeutralMode.Brake);
            //upDown.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
            upDown.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
            upDown.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
            zeroed = false;
            mode = AutoMode.ZEROING;
        }
    }

    /**
     * This method is a generated getter for the output of a setpoint.
     * @return ElevatorPosition output
     */
    public ElevatorPosition getSetpoint()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
		{
			return setpoint;
		}
		return ElevatorPosition.HATCH_LOW;
    }

    /**
     * This method is a generated getter for the output of upDown.
     * @return double output
     */
    public int getPosition()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
		{
			return upDown.getSensorCollection().getQuadraturePosition();
		}
		return 0;
    }

    public void setPosition(ElevatorPosition switchPosition)
    {
        if(Parameters.ELEVATOR_AVAILABLE)
		{
			upDown.set(ControlMode.Position, switchPosition.getSetPoint());
			setpoint = switchPosition;
		}
    }

    public void zeroPosition()
	{
		if(Parameters.ELEVATOR_AVAILABLE)
		{
            // FIX ME Double Check Logic
            // upDown.getSensorCollection().setQuadraturePosition(0, 0);
            zeroed = true;
            mode = AutoMode.MANUAL;
            upDown.setSelectedSensorPosition(0);            
		}
    }

    public void stopMotor()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
        {
            mode = AutoMode.MANUAL;
            upDown.set(ControlMode.PercentOutput, 0.0);
        }
    }

    /**
     * Specifies a speed the motor should turn as a percentage.
     * 
     * @param motorPower Speed of the motor in range -1.0 ... 0.0 ... 1.0
     */
    public void setPower(double motorPower)
    {
        if (Parameters.ELEVATOR_AVAILABLE)
        {
            mode = AutoMode.MANUAL;
            upDown.set(ControlMode.PercentOutput, motorPower);
        }
    }
    
    /**
     * Setter for auto mode
     * 
     * @param AutoMode the new mode
     */
    public void setMode(AutoMode switchMode)
    {
        mode = switchMode;
    }

    /**
     * Getter for auto mode
     * 
     * @return AutoMode
     */
    public AutoMode getMode()
    {
        return mode;
    }

    public boolean isZeroed()
    {
        if (Parameters.ELEVATOR_AVAILABLE)
        {
            return zeroed;
        }
        return false;
    }

    /**
     * It tells if the elevator is completely down
     * 
     * @return boolean true if all the way down, false otherwise
     */
    public boolean isDown()
    {
        SensorCollection sc = upDown.getSensorCollection();
        if (sc != null)
        {
            if (sc.isRevLimitSwitchClosed())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called approximately 20 times per second by the
     * Robot's teleopPeriodic() or autonomousPeriodic().  It ensures
     * the elevator's position encoder is zeroed when the robot is first
     * enabled (before the elevator can be moved).
     * 
     */
    public void process()
    {
        if (Parameters.ELEVATOR_AVAILABLE)
        {
            if (!zeroed)
            {
                if (isDown())
                {
                    upDown.set(ControlMode.PercentOutput, 0.0);
                    zeroPosition();
                }
                else
                {
                    upDown.set(ControlMode.PercentOutput, Parameters.ELEVATOR_ZEROING_SPEED);
                }
            }
        }
    }

    /**
     * This method intentionally blank
     */
    public void initDefaultCommand()
    {
        setDefaultCommand(new DefaultElevatorCommand());
    }
}