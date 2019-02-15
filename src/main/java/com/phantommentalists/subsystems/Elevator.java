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
import com.phantommentalists.Telepath;
import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.Parameters.ElevatorPosition;
import com.phantommentalists.Parameters.Pid;
import com.phantommentalists.commands.DefaultElevatorCommand;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem 
{
    /** Motor controller for the motor to raise and lower the elevator */
    TalonSRX upDown;
    
    /** setpoint we want the elevator to go to when it is in AUTO mode */
    ElevatorPosition setpoint;

    /** Has the elevator been zeroed yet?  If not, do not enter AUTO mode */
    boolean zeroed;

    /** Mode the elevator is currently in */
    AutoMode mode;
    private Telepath robot;

    /**
     * Default constructor
     */
    public Elevator(Telepath r)
    {
        if(Parameters.ELEVATOR_AVAILABLE)
        {
            robot = r;
            upDown = new TalonSRX(Parameters.CanId.ELEVATOR.getCanId());
            upDown.setInverted(Parameters.CanId.ELEVATOR.isInverted());
            upDown.selectProfileSlot(1, 0);
            upDown.config_kP(1, Pid.ELEVATOR.getP(), 0);
			upDown.config_kI(1, Pid.ELEVATOR.getI(), 0);
		    upDown.config_kD(1, Pid.ELEVATOR.getD(), 0);
		    upDown.config_kF(1, Pid.ELEVATOR.getF(), 0);
            upDown.set(ControlMode.PercentOutput, 0.0);
            upDown.setNeutralMode(NeutralMode.Brake);
            upDown.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
            upDown.configForwardSoftLimitThreshold(Parameters.ElevatorPosition.UPPER_LIMIT.getSetPoint());
            upDown.configReverseSoftLimitThreshold(Parameters.ElevatorPosition.LOWER_LIMIT.getSetPoint());
            upDown.configForwardSoftLimitEnable(false);
            upDown.configReverseSoftLimitEnable(false);
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
     * 
     * @return int position of the elevator in ticks read from the quadrature encoder
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
            if (mode == AutoMode.AUTO)
            {
			    upDown.set(ControlMode.Position, switchPosition.getSetPoint());
                setpoint = switchPosition;
            }
		}
    }

    /**
     * Sets the encoder to zero at the current position
     */
    public void zeroPosition()
	{
		if(Parameters.ELEVATOR_AVAILABLE)
		{
            zeroed = true;
            mode = AutoMode.MANUAL;
            upDown.setSelectedSensorPosition(0);
            upDown.configForwardSoftLimitThreshold(Parameters.ElevatorPosition.UPPER_LIMIT.getSetPoint());
            upDown.configReverseSoftLimitThreshold(Parameters.ElevatorPosition.LOWER_LIMIT.getSetPoint());
            upDown.configForwardSoftLimitEnable(true);
            upDown.configReverseSoftLimitEnable(true);
		}
    }

    /**
     * Stop the elevator motor
     */
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
            if (mode == AutoMode.MANUAL)
            {
                mode = AutoMode.MANUAL;
                upDown.set(ControlMode.PercentOutput, motorPower);
            }
        }
    }
    
    /**
     * Setter for auto mode
     * 
     * NOTE:  We never change the mode if it is currently zeroing
     * 
     * @param AutoMode the new mode
     */
    public void setMode(AutoMode switchMode)
    {
        if (Parameters.ELEVATOR_AVAILABLE) 
        {
            if (mode != AutoMode.ZEROING)
            {
                mode = switchMode;
            }
        }
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

    /**
     * Getter to determine if elevator has been zeroed
     * 
     * @return boolean true if elevator has been zeroed, false otherwise
     */
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
        if (Parameters.ELEVATOR_AVAILABLE)
        {
            int position = getPosition();
            if (position <= Parameters.ElevatorPosition.LOWER_LIMIT.getSetPoint())
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
            if (mode == AutoMode.ZEROING)
            {
                if (getCurrent() >= Parameters.ELEVATOR_ZEROING_CURRENT_LIMIT)
                {
                    upDown.set(ControlMode.PercentOutput, 0.0);
                    zeroPosition();
                }
                else
                {
                    upDown.set(ControlMode.PercentOutput, Parameters.ELEVATOR_ZEROING_SPEED);
                }
            }
            SmartDashboard.putNumber("Elevator: Current", getCurrent());
            SmartDashboard.putNumber("Elevator: Position", getPosition());
            SmartDashboard.putString("Elevator: Mode", mode.toString());
        }
    }

    public double getCurrent() 
    {
        return upDown.getOutputCurrent();
    }

    /**
     * This method intentionally blank
     */
    public void initDefaultCommand()
    {
        setDefaultCommand(new DefaultElevatorCommand(robot));
    }
}