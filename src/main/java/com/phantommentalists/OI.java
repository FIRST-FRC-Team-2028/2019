/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

import com.phantommentalists.Parameters.MultiController;
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
  
  public double getLeftStick()
  {
    switch (controllerType) {
      case LOGITECH_EXTREME:
        return stick.getRawAxis(Parameters.LOGITECH_Y_AXIS) + stick.getRawAxis(Parameters.LOGITECH_TWIST);
      case PS_CONTROLLER:
      case XBOX_CONTROLLER:
        return stick.getRawAxis(Parameters.PS_LEFT_STICK);
      default:
        return 0.;
    }
  }

  private MultiController numtoMC(int num)
  {
    System.out.println("Num2MC: "+num);
    for (MultiController m : MultiController.values())
    {
      if (m.getnum() == num) 
      {
        return m;
      }
    }
    return null;
  }

  public double getRightStick()
  {
    switch (controllerType) {
      case LOGITECH_EXTREME:
        return stick.getRawAxis(Parameters.LOGITECH_Y_AXIS) - stick.getRawAxis(Parameters.LOGITECH_TWIST);
      case PS_CONTROLLER:
      case XBOX_CONTROLLER:
        return stick.getRawAxis(Parameters.PS_RIGHT_STICK);
      default:
        return 0.;
    }
  }
  // Button button = new JoystickButton(stick, buttonNumber);
  Button button = new JoystickButton(stick, 1);
  
  public OI()
  {
    button.whileHeld(new SpinCommand());
    //private Parameters.MultiController controllerType = type;
    String type = DriverStation.getInstance().getJoystickName(0);
    SmartDashboard.putString("controller name ", type);
    SmartDashboard.putNumber("controller type",  DriverStation.getInstance().getJoystickType(0));
    MultiController dummy=numtoMC(DriverStation.getInstance().getJoystickType(0));
    controllerType = dummy;
    if(controllerType != null){
    SmartDashboard.putNumber("Current type", controllerType.getnum());
    }
  }

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
}
