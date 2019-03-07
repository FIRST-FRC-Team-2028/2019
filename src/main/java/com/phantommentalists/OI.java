/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

import com.phantommentalists.commands.AlignReflectCommand;
import com.phantommentalists.commands.CargoHandlerLoadCommand;
import com.phantommentalists.commands.CargoIntakeLoadCommand;
import com.phantommentalists.commands.GoToElevatorPositionCommand;
import com.phantommentalists.commands.GrabHatchCommand;
import com.phantommentalists.commands.PickUpCargoCommandGroup;
import com.phantommentalists.commands.PlaceCargoCommandGroup;
import com.phantommentalists.commands.PlaceHatchCommandGroup;
import com.phantommentalists.commands.ReleaseHatchCommand;
import com.phantommentalists.commands.RetractCargoIntakeCommand;
import com.phantommentalists.commands.ElevatorRunUpCommand;
import com.phantommentalists.commands.ElevatorRunDownCommand;
import com.phantommentalists.commands.SpinCommand;
import com.phantommentalists.commands.ExtendCargoIntakeTestCommand;
import com.phantommentalists.commands.RetractCargoIntakeTestCommand;
import com.phantommentalists.commands.ShootCargoCommand;
import com.phantommentalists.commands.SwitchCommand;

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
  Button buttonElevatorUp = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_ELEVATOR_UP);
  Button buttonElevatorDown = new JoystickButton(buttonBoxRight, Parameters.BUTTON_ELEVATOR_DOWN);

  Button buttonCargoIntakeExtend = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_INTAKE_EXTEND);
  Button buttonCargoIntakeRetract = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_INTAKE_RETRACT);
 
  Button buttonHatchExtend = new JoystickButton(buttonBoxRight, 10);
  Button buttonCargoLoad = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_RUN_BOTH_ROLLERS);
  Button buttonCargoShoot = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_HANDLER_INTAKE);
  Button buttonCargoHandlerSuck = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_HANDLER_INTAKE);
  Button buttonHatch1get = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_1);
  Button buttonCargo1get = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_1);
  Button buttonHatch2 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_2);
  Button buttonCargo2 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_2);
  Button buttonHatch3 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_3);
  Button buttonCargo3 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_3);
  Button switchBlue = new JoystickButton(buttonBoxRight, Parameters.SWITCH_BLUE);

  Button buttonHatchSuck = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_HATCHHANDLER_SUCK);
  Button buttonHatchBlow = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_HATCHHANDLER_BLOW);

  // public Button buttonLifterRetract = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_LIFTER_RETRACT);
  // public Button buttonLifterDeploy = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_LIFTER_DEPLOY);

  public OI(Telepath r)
  {
    button.whileHeld(new SpinCommand(r));
    button2.whileHeld(new AlignReflectCommand(r));
    if ( Parameters.BUTTONBOX_AVAILABLE) {
      buttonElevatorUp.whileHeld(new ElevatorRunUpCommand(r));
      buttonElevatorDown.whileHeld(new ElevatorRunDownCommand(r));
      buttonHatch1get.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_LOW, r));
      buttonCargo1get.whenPressed(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_LOW, r));
      buttonHatch2.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_MIDDLE, r));
      buttonCargo2.whenPressed(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_MIDDLE, r));
      buttonHatch3.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_HIGH, r));
      buttonCargo3.whenPressed(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_HIGH, r));

      buttonHatchSuck.whenPressed(new GrabHatchCommand(r));
      buttonHatchBlow.whenPressed(new PlaceHatchCommandGroup(r));

      // buttonHatchExtend.whenPressed(new DeployHatchHandlerCommand(r));
      // buttonHatchExtend.whenReleased(new HatchHandlerRetractCommand(r));


      // buttonCargoLoad.whileHeld(new CargoHandlerLoadCommand(r));
      // buttonCargoLoad.whileHeld(new PickUpCargoCommandGroup(r));
      // buttonCargoLoad.whenReleased(new RetractCargoIntakeCommand(r));

      buttonCargoHandlerSuck.whileHeld(new CargoHandlerLoadCommand(r));
      buttonCargoLoad.whileHeld(new CargoIntakeLoadCommand(r));
      buttonCargoShoot.whileHeld(new ShootCargoCommand(r));

      buttonCargoIntakeExtend.whileHeld(new ExtendCargoIntakeTestCommand(r));
      buttonCargoIntakeRetract.whileHeld(new RetractCargoIntakeTestCommand(r));
    
      //buttonCargoIntakeExtend.whenReleased(new RetractCargoIntakeTestCommand(r));
      switchBlue.whileHeld(new SwitchCommand(r, true));
      switchBlue.whenPressed(new SwitchCommand(r, false));
    }
    String type = DriverStation.getInstance().getJoystickName(0);
    SmartDashboard.putString("controller name ", type);
    SmartDashboard.putNumber("controller type",  DriverStation.getInstance().getJoystickType(0));
    controllerType = Parameters.multiContFromNum(DriverStation.getInstance().getJoystickType(0));
    //try
    //{
    if(controllerType != null){
      SmartDashboard.putNumber("Current type", controllerType.getnum());
      }
    //} catch (Exception nullException) {
    //      screamAndDie("Unrecognized controller!");
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

  public double getSlider() {
    //System.out.println(stick.getRawAxis(3));
    return stick.getRawAxis(3);
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

}
