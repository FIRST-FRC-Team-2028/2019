/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.phantommentalists.Parameters;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem
{
    TalonSRX e_motor;
    //boolean fwdlimitclosed;
    double setpoint;
    int autoMode;

    public Elevator()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
        {
            e_motor = new TalonSRX(Parameters.CanId.ELEVATOR.getCanId());
            e_motor.config_kP(1, Parameters.Pid.ELEVATOR.getP(), 0);
			e_motor.config_kI(1, Parameters.Pid.ELEVATOR.getI(), 0);
		    e_motor.config_kD(1, Parameters.Pid.ELEVATOR.getD(), 0);
		    e_motor.config_kF(1, Parameters.Pid.ELEVATOR.getF(), 0);
		    e_motor.set(ControlMode.Position, 0);
            e_motor .setNeutralMode(NeutralMode.Brake);
            e_motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
			e_motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
        }
    }

    public double getSetpoint()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
		{
			return setpoint;
		}
		return 0;
    }

    public double getPosition()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
		{
			return e_motor.getSensorCollection().getQuadraturePosition();
		}
		return 0;
    }

    public void setPosition(double switchPosition)
    {
        if(Parameters.ELEVATOR_AVAILABLE)
		{
			e_motor.set(ControlMode.Position, switchPosition);
			setpoint = switchPosition;
		}
    }

    public void zeroPosition()
	{
		if(Parameters.ELEVATOR_AVAILABLE)
		{
			e_motor.getSensorCollection().setQuadraturePosition(0, 0);
		}
    }

    public void stopMotor()
    {
        if(Parameters.ELEVATOR_AVAILABLE)
        {
            e_motor.set(ControlMode.PercentOutput,0.);
        }
    }

    public double elevatorUp()
    {
        e_motor.set(ControlMode.PercentOutput, 0.5);
        return getPosition();
    }

    public double elevatorDown()
    {
        e_motor.set(ControlMode.PercentOutput, -0.5);
        return getPosition();
    }
    
    public void setMode(int switchMode)
    {
        autoMode = switchMode;
    }

    public int getMode()
    {
        return autoMode;
    }

    public void initDefaultCommand()
    {
        
    }
}