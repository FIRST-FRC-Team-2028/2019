/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package  com.phantommentalists.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.phantommentalists.Parameters;
//import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
//import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.phantommentalists.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoIntake extends Subsystem 
{
  TalonSRX intakeMotor;
  TalonSRX handlerMotor;

  public CargoIntake()
  {
    intakeMotor = RobotMap.cargo_motor_intake;
    intakeMotor.set(ControlMode.PercentOutput, 0);
		intakeMotor.setNeutralMode(NeutralMode.Brake);
    intakeMotor.setInverted(Parameters.CanId.CARGO_INTAKE.isInverted());
    
    handlerMotor = RobotMap.cargo_motor_handler;
    //handlerMotor.set(ControlMode.PercentOutput, 0);
    handlerMotor.follow(intakeMotor);
		handlerMotor.setNeutralMode(NeutralMode.Brake);
    handlerMotor.setInverted(Parameters.CanId.CARGO_HANDLER.isInverted());

    /**
    *E_Motor.config_kP(1, Parameters.Pid.ELEVATOR.getP(), 0);
		*E_Motor.config_kI(1, Parameters.Pid.ELEVATOR.getI(), 0);
		*E_Motor.config_kD(1, Parameters.Pid.ELEVATOR.getD(), 0);
		*E_Motor.config_kF(1, Parameters.Pid.ELEVATOR.getF(), 0);
    *E_Motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
    *E_Motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    */
  }

  public boolean isCubeHeld()
  {
    if(Parameters.GRIPPER_AVAILABLE)
    {
      return intakeMotor.getSensorCollection().isFwdLimitSwitchClosed();
      //Limit Switch???
    }
    return true;
  }
  
	public void pickupCube()
	{
		if(Parameters.GRIPPER_AVAILABLE){
			intakeMotor.set(ControlMode.PercentOutput, Parameters.GRIPPER_INFEED_SPEED);
		}
	}

	public void launchCube()
	{
		if(Parameters.GRIPPER_AVAILABLE){
			intakeMotor.set(ControlMode.PercentOutput, Parameters.GRIPPER_LAUNCH_SPEED);
		}
  }
  
  public void stopGripper()
  {
    if(Parameters.GRIPPER_AVAILABLE)
    {
      intakeMotor.set(ControlMode.PercentOutput, 0.);
    }
  }
  
  @Override
  public void initDefaultCommand() 
  {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
