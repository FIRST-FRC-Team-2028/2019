/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

import com.phantommentalists.Parameters.MultiController;
import com.phantommentalists.commands.AlignCommand;
import com.phantommentalists.commands.GoToElevatorPositionCommand;
import com.phantommentalists.commands.SpinCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  public Joystick stick = new Joystick(0);
  public Joystick buttonBoxLeft = new Joystick(1);
  public Joystick buttonBoxRight = new Joystick(2);
  //private Parameters.MultiController controllerType;
  private Parameters.MultiController controllerType = Parameters.MultiController.PS_CONTROLLER;
  
  // Button button = new JoystickButton(stick, buttonNumber);
  Button button = new JoystickButton(stick, 1);
  Button button2 = new JoystickButton(stick, 2);
  Button buttonElevatorUp = new JoystickButton(buttonBoxRight, Parameters.BUTTON_UP);
  Button buttonElevatorDown = new JoystickButton(buttonBoxRight, Parameters.BUTTON_DOWN);
 
  Button buttonHatch1get = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_1);
  Button buttonCargo1get = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_1);
  Button buttonHatch2 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_2);
  Button buttonCargo2 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_2);
  Button buttonHatch3 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_3);
  Button buttonCargo3 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_3);
  public Button buttonLifterRetract = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_LIFTER_RETRACT);
  public Button buttonLifterDeploy = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_LIFTER_DEPLOY);
  
  public OI(Telepath robot)
  {
    if (Parameters.DRIVE_AVAILABLE) 
    {
      button.whileHeld(new SpinCommand(robot));
      button2.whileHeld(new AlignCommand(robot.getDrive()));
    }
    if ( Parameters.BUTTONBOX_AVAILABLE) {
      buttonHatch1get.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_LOW));
      buttonCargo1get.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.CARGO_LOW));
      buttonHatch2.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_MIDDLE));
      buttonCargo2.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.CARGO_MIDDLE));
      buttonHatch3.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_HIGH));
      buttonCargo3.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.CARGO_HIGH));
    }
    //private Parameters.MultiController controllerType = type;
    String type = DriverStation.getInstance().getJoystickName(0);
    SmartDashboard.putString("controller name ", type);
    SmartDashboard.putNumber("controller type",  DriverStation.getInstance().getJoystickType(0));
    //MultiController dummy=numtoMC(DriverStation.getInstance().getJoystickType(0));
    MultiController dummy=Parameters.multiContFromNum(DriverStation.getInstance().getJoystickType(0));
    controllerType = dummy;
    //try
    //{
    if(controllerType != null){
      SmartDashboard.putNumber("Current type", controllerType.getnum());
      }
    //} catch (Exception nullException) {
     // screamAndDie("Unrecognized controller!");
    //}
  }
  
  private double leftTankFromThrotYaw(double throt, double yaw)
  {
    double rawright= throt-yaw;
    double rawleft = throt+yaw;
    if (Math.abs(rawleft)>1.)
    {
      return Math.signum(rawleft);
    } else if (Math.abs(rawright)>1.)
    {
      return Math.copySign(rawleft/rawright, rawleft);
    }else{
      return rawleft;
    }
  }
  private double rightTankFromThrotYaw(double throt, double yaw)
  {
    double rawright= throt-yaw;
    double rawleft = throt+yaw;
    if (Math.abs(rawright)>1.)
    {
      return Math.signum(rawright);
    } else if (Math.abs(rawleft)>1.)
    {
      return Math.copySign(rawright/rawleft, rawright);
    }else{
      return rawright;
    }
  }

  public double getLeftStick()
  {
    double throt;
    double yaw;
    switch (controllerType) {
      case LOGITECH_EXTREME:
        throt = stick.getRawAxis(Parameters.LOGITECH_Y_AXIS);
        yaw   = -stick.getRawAxis(Parameters.LOGITECH_TWIST);
        //return  throt + yaw; MrG says BAD - see logitechControl.xlms
        yaw *= Math.abs(yaw);  // finer control at small yaw
        return leftTankFromThrotYaw(throt, yaw);
      case CUBE_STEERING_WHEEL:
        throt = stick.getRawAxis(Parameters.CUBE_RIGHT_PEDAL);
        boolean pad= stick.getRawButton(Parameters.CUBE_RIGHT_PADDLE);
        //System.out.println("paddle"+pad);
        //SmartDashboard.putBoolean("Paddle?", pad);
        throt    *= pad? 1.: (-1.);
        yaw   = -stick.getRawAxis(Parameters.CUBE_WHEEL_AXIS);
        return leftTankFromThrotYaw(throt, yaw);
      case PS_CONTROLLER:
      case XBOX_CONTROLLER:
        return stick.getRawAxis(Parameters.PS_LEFT_STICK);
      default:
        return 0.;
    }
  }
  
  public double getRightStick()
  {
    double throt;
    double yaw;
    switch (controllerType) {
      case LOGITECH_EXTREME:
        throt = stick.getRawAxis(Parameters.LOGITECH_Y_AXIS);
        yaw   = -stick.getRawAxis(Parameters.LOGITECH_TWIST);
        yaw *=Math.abs(yaw);
        return rightTankFromThrotYaw(throt, yaw);
      case CUBE_STEERING_WHEEL:
        throt = stick.getRawAxis(Parameters.CUBE_RIGHT_PEDAL)
               * (stick.getRawButton(Parameters.CUBE_RIGHT_PADDLE)?1.:-1.);
        yaw   = -stick.getRawAxis(Parameters.CUBE_WHEEL_AXIS);
        return rightTankFromThrotYaw(throt, yaw);
      case PS_CONTROLLER:
      case XBOX_CONTROLLER:
        return stick.getRawAxis(Parameters.PS_RIGHT_STICK);
      default:
        return 0.;
    }
  }

  // Moved to Parameters.java with MicroController definition
  // private MultiController numtoMC(int num)
  // {
  //   System.out.println("Num2MC: "+num);
  //   for (MultiController m : MultiController.values())
  //   {
  //     if (m.getnum() == num) 
  //     {
  //       return m;
  //     }
  //   }
  //   return null;
  // }

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  public Button getElevatorUp() {
    return buttonElevatorUp;
  }

  public Button getElevatorDown() {
    return buttonElevatorDown;
  }
}
