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
import com.phantommentalists.commands.DeployHatchHandlerCommand;
import com.phantommentalists.commands.GoToElevatorPositionCommand;
import com.phantommentalists.commands.GrabHatchCommandGroup;
import com.phantommentalists.commands.HatchHandlerRetractCommand;
import com.phantommentalists.commands.PickUpCargoCommandGroup;
import com.phantommentalists.commands.PlaceCargoCommandGroup;
import com.phantommentalists.commands.PlaceHatchCommandGroup;
import com.phantommentalists.commands.ReleaseHatchCommandGroup;
import com.phantommentalists.commands.RetractCargoIntakeCommand;
import com.phantommentalists.commands.ElevatorRunUpCommand;
import com.phantommentalists.commands.ElevatorRunDownCommand;
import com.phantommentalists.commands.SpinCommand;
import com.phantommentalists.commands.ExtendCargoIntakeTestCommand;
import com.phantommentalists.commands.RetractCargoIntakeTestCommand;
import com.phantommentalists.commands.ShootCargoCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
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

  /**
   * Uses the switch to change the behavior of a button
   */
  class SwitchedButton extends Trigger
  {
    Button button;
    Button modeSwitch;
    boolean mode;

    public SwitchedButton(Button button, Button modeSwitch, boolean mode) {
      this.button = button;
      this.modeSwitch = modeSwitch;
      this.mode = mode;
    }

    @Override
    public boolean get() {
      return (button.get() && (modeSwitch.get() == mode));
    }
  }

  public Joystick stick = new Joystick(0);
  public Joystick buttonBoxLeft = new Joystick(1);
  public Joystick buttonBoxRight = new Joystick(2);
  //private Parameters.MultiController controllerType;
  private Parameters.MultiController controllerType = Parameters.MultiController.LOGITECH_EXTREME;
  
  // Button button = new JoystickButton(stick, buttonNumber);
  Button button = new JoystickButton(stick, 1);
  Button button2 = new JoystickButton(stick, 2);
  Button buttonElevatorUp = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_ELEVATOR_UP);
  Button buttonElevatorDown = new JoystickButton(buttonBoxRight, Parameters.BUTTON_ELEVATOR_DOWN);

  Button buttonCargoIntakeExtend = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_INTAKE_EXTEND);
  Button buttonCargoIntakeRetract = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_INTAKE_RETRACT);
 
  Button buttonCargoIntakeRollers = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_INTAKE_ROLLERS);

  // Button buttonCargoLoad = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_RUN_BOTH_ROLLERS);
  Button buttonCargoShoot = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_HANDLER_INTAKE);
  // Button buttonCargoHandlerSuck = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_CARGO_HANDLER_INTAKE);
  Button buttonHatch1get = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_1);
  Button buttonCargo1get = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_1);
  Button buttonHatch2 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_2);
  Button buttonCargo2 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_2);
  Button buttonHatch3 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_HATCH_3);
  Button buttonCargo3 = new JoystickButton(buttonBoxRight, Parameters.BUTTON_CARGO_3);
  Button switchBlue = new JoystickButton(buttonBoxRight, Parameters.SWITCH_BLUE);
  Button buttonHatchExtend = new JoystickButton(buttonBoxRight, Parameters.SWITCH_GREEN_HATCH_EXTEND);

  SwitchedButton switchedHatch1ButtonOn = new SwitchedButton(buttonHatch1get, switchBlue, true);
  SwitchedButton switchedHatch1ButtonOff = new SwitchedButton(buttonHatch1get, switchBlue, false);
  SwitchedButton switchedHatch2ButtonOn = new SwitchedButton(buttonHatch2, switchBlue, true);
  SwitchedButton switchedHatch2ButtonOff = new SwitchedButton(buttonHatch2, switchBlue, false);
  SwitchedButton switchedHatch3ButtonOn = new SwitchedButton(buttonHatch3, switchBlue, true);
  SwitchedButton switchedHatch3ButtonOff = new SwitchedButton(buttonHatch3, switchBlue, false);
  SwitchedButton switchedCargo1ButtonOn = new SwitchedButton(buttonCargo1get, switchBlue, true);
  SwitchedButton switchedCargo1ButtonOff = new SwitchedButton(buttonCargo1get, switchBlue, false);
  SwitchedButton switchedCargo2ButtonOn = new SwitchedButton(buttonCargo2, switchBlue, true);
  SwitchedButton switchedCargo2ButtonOff = new SwitchedButton(buttonCargo2, switchBlue, false);
  SwitchedButton switchedCargo3ButtonOn = new SwitchedButton(buttonCargo3, switchBlue, true);
  SwitchedButton switchedCargo3ButtonOff = new SwitchedButton(buttonCargo3, switchBlue, false);

  Button buttonHatchGet = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_HATCHHANDLER_GET);
  Button buttonHatchPut = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_HATCHHANDLER_PUT);

  // public Button buttonLifterRetract = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_LIFTER_RETRACT);
  // public Button buttonLifterDeploy = new JoystickButton(buttonBoxLeft, Parameters.BUTTON_LIFTER_DEPLOY);

  public OI(Telepath r)
  {
    button.whileHeld(new SpinCommand(r));
    button2.whileHeld(new AlignReflectCommand(r));
    if ( Parameters.BUTTONBOX_AVAILABLE) {
      // buttonElevatorUp.whileHeld(new ElevatorRunUpCommand(r));
      // buttonElevatorDown.whileHeld(new ElevatorRunDownCommand(r));
      // buttonHatch1get.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_LOW, r));
      // buttonCargo1get.whenPressed(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_LOW, r));
      // buttonHatch2.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_MIDDLE, r));
      // buttonCargo2.whenPressed(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_MIDDLE, r));
      // buttonHatch3.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_HIGH, r));
      // buttonCargo3.whenPressed(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_HIGH, r));
      
      buttonElevatorUp.whileHeld(new ElevatorRunUpCommand(r));
      buttonElevatorDown.whileHeld(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.CARGO_SHIP, r));
      switchedHatch1ButtonOn.whenActive(new PlaceHatchCommandGroup(Parameters.ElevatorPosition.HATCH_LOW, r));
      switchedHatch1ButtonOff.whileActive(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_LOW, r));
      switchedCargo1ButtonOn.whileActive(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_LOW, r));
      switchedCargo1ButtonOff.whenActive(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.CARGO_LOW, r));
      switchedHatch2ButtonOn.whenActive(new PlaceHatchCommandGroup(Parameters.ElevatorPosition.HATCH_MIDDLE, r));
      switchedHatch2ButtonOff.whileActive(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_MIDDLE, r));
      switchedCargo2ButtonOn.whileActive(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_MIDDLE, r));
      switchedCargo2ButtonOff.whenActive(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.CARGO_MIDDLE, r));
      switchedHatch3ButtonOn.whenActive(new PlaceHatchCommandGroup(Parameters.ElevatorPosition.HATCH_HIGH, r));
      switchedHatch3ButtonOff.whileActive(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.HATCH_HIGH, r));
      switchedCargo3ButtonOn.whileActive(new PlaceCargoCommandGroup(Parameters.ElevatorPosition.CARGO_HIGH, r));
      switchedCargo3ButtonOff.whenActive(new GoToElevatorPositionCommand(Parameters.ElevatorPosition.CARGO_HIGH, r));

      buttonHatchGet.whenPressed(new GrabHatchCommandGroup(r));
      buttonHatchPut.whenPressed(new ReleaseHatchCommandGroup(r));

      // buttonHatchExtend.whenPressed(new DeployHatchHandlerCommand(r));
      // buttonHatchExtend.whenReleased(new HatchHandlerRetractCommand(r));

      // buttonCargoIntakeRollers.whileHeld(new CargoIntakeLoadCommand(r));

      // buttonCargoLoad.whileHeld(new CargoHandlerLoadCommand(r));
      // buttonCargoLoad.whileHeld(new PickUpCargoCommandGroup(r));
      // buttonCargoLoad.whenReleased(new RetractCargoIntakeCommand(r));

      //buttonHatchExtend.whileHeld(new DeployHatchHandlerCommand(r));
      buttonHatchExtend.whenPressed(new DeployHatchHandlerCommand(r));
      buttonHatchExtend.whenReleased(new HatchHandlerRetractCommand(r));

      // buttonCargoHandlerSuck.whileHeld(new CargoHandlerLoadCommand(r));
      // buttonCargoLoad.whileHeld(new PickUpCargoCommandGroup(r));
      buttonCargoShoot.whileHeld(new ShootCargoCommand(r));

      buttonCargoIntakeExtend.whileHeld(new ExtendCargoIntakeTestCommand(r));
      buttonCargoIntakeRetract.whileHeld(new RetractCargoIntakeTestCommand(r));
    
      //buttonCargoIntakeExtend.whenReleased(new RetractCargoIntakeTestCommand(r));
    }
    String type = DriverStation.getInstance().getJoystickName(0);
    SmartDashboard.putString("controller name ", type);
    SmartDashboard.putNumber("controller type",  DriverStation.getInstance().getJoystickType(0));
    // controllerType = Parameters.multiContFromNum(DriverStation.getInstance().getJoystickType(0));
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

  public Joystick getButtonBoxleft()
  {
    return buttonBoxLeft;
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
        return leftTankFromThrotYaw(throt, yaw*0.75);
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
