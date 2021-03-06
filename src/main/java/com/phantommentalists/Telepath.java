/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.phantommentalists.Parameters.AutoMode;
import com.phantommentalists.subsystems.CargoIntake;
import com.phantommentalists.subsystems.Drive;
import com.phantommentalists.subsystems.Elevator;
import com.phantommentalists.subsystems.Handler;
import com.phantommentalists.subsystems.PDP;
import com.phantommentalists.subsystems.Pressure;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Telepath extends TimedRobot {

  
  private Handler handler;
  private Elevator elevator;
  private static CargoIntake cargoIntake;
  private Drive drive;
  private OI oi;
  private Pressure pressure;
  private PIDController liftLeveler;
  private PDP pdp;
  public static CameraThread cameraThread;
  public AnalogInput ultra;
  Command autonomousCommand;
  Command defaultCommand;
  // SendableChooser<Command> chooser = new SendableChooser<>();
  UsbCamera cam1;
  UsbCamera cam2;
  VideoSink server;
  CvSink sink;
  
  /**
   * FIXME
   * global debug flag should be set false for competition
   */
  boolean debug=true;

  /**
   * Default constructor
   */
  public Telepath() {
    if (Parameters.CAMERA_AVAILABLE){
      cam1 = CameraServer.getInstance().startAutomaticCapture(0);
      // cam2 = CameraServer.getInstance().startAutomaticCapture(1);
      server = CameraServer.getInstance().getServer();
      sink = CameraServer.getInstance().getVideo();
      cam1.setResolution(Parameters.CAM_WIDTH, Parameters.CAM_HEIGHT);
      cam1.setFPS(20);
      // cam1.setExposureManual(16);

      server.setSource(cam1);
      sink.setSource(cam1);
      // defaultCommand = new DefaultCommand(drive);

      // //cameraThread = new CameraThread();
      
      // cameraThread.start();
    }
    
    if(Parameters.ULTRASONIC_AVAILABLE)
    {
      ultra = new AnalogInput(0);
    }
  
    if (Parameters.DRIVE_AVAILABLE) {
      drive = new Drive(this);
    }
    if (Parameters.HANDLER_AVAILABLE) {
      handler = new Handler(this);
    }
    if (Parameters.ELEVATOR_AVAILABLE) {
      elevator = new Elevator(this);
    }
    if (Parameters.INTAKE_AVAILABLE) {
      cargoIntake = new CargoIntake(this);
    }
    oi = new OI(this);
    pdp = new PDP(); 
    if (Parameters.COMPRESSOR_AVAILABLE) {
      pressure = new Pressure();
    }
  }

  public boolean getDebug()
  {
    return debug;
  }

  /**
   * Getter for the drive subsystem
   * 
   * @return Drive - The drive subsystem
   */
  public Drive getDrive() {
    return drive;
  }

  public AnalogInput getUltrasonic()
  {
    return ultra;
  }
  /**
   * Getter for the Handler subsystem
   * 
   * @return Handler - The handler subsystem
   */
  public Handler getHandler() {
    return handler;
  }

  /**
   * 
   * @return pdp
   */
  public PDP getPDP(){
    return pdp;
  }

  /**
   * Getter for the elevator subsystem
   * 
   * @return Elevator - The elevator subsystem
   */
  public Elevator getElevator() {
    return elevator;
  }

  /**
   * Getter for the cargo intake subsystem
   * 
   * @return Cargo intake - The cargo intake subsystem
   */
  public CargoIntake getCargoIntake() {
    return cargoIntake;
  }

  /**
   * Getter for the OI
   * 
   * @return OI
   */
  public OI getOI() {
    return oi;
  }

  /**
   * 
   * @return PIDController liftLveler
   */
  public PIDController getLiftLeveler(){
    return liftLeveler;
  }

  /**
   * Getter for the Camera Thread
   * 
   * @return Camera Thread
   */
  public CameraThread getCameraThread() {
    return cameraThread;
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    //cameraThread.start();
    // chooser.addOption("My Auto", new MyAutoCommand());
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //SmartDashboard.putNumber("size", cameraThread.getSize());
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    if (Parameters.DRIVE_AVAILABLE) {
      drive.process();
    }
    if (Parameters.HANDLER_AVAILABLE) {
      handler.process();
    }
    if (Parameters.ELEVATOR_AVAILABLE) {
      elevator.process();
    }
    if (Parameters.INTAKE_AVAILABLE) {
      cargoIntake.process();
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    /**
     * 
     * FIXME For debugging only. Remove before comp
     * 
     */
    // if(Parameters.ELEVATOR_AVAILABLE) {
    //   elevator.setMode(AutoMode.ZEROING);
    // }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    if (Parameters.CAMERA_AVAILABLE){
      // SmartDashboard.putNumber("'left' x", cameraThread.getLeft().x2);
      // SmartDashboard.putNumber("'right' x", cameraThread.getRight().x2);
    }
    if(Parameters.COMPRESSOR_AVAILABLE){
      // FIXME
      // pressure.disable();
    }

    if (Parameters.DRIVE_AVAILABLE) {
      drive.process();
    }
    if (Parameters.HANDLER_AVAILABLE) {
      handler.process();
    }
    if (Parameters.ELEVATOR_AVAILABLE) {
      elevator.process();
    }
    if (Parameters.INTAKE_AVAILABLE) {
      cargoIntake.process();
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() 
  {
    if (Parameters.DRIVE_AVAILABLE) {
      drive.process();
    }
    if (Parameters.HANDLER_AVAILABLE) {
      handler.process();
    }
    if (Parameters.ELEVATOR_AVAILABLE) {
      elevator.process();
    }
    if (Parameters.INTAKE_AVAILABLE) {
      cargoIntake.process();
    }
  }
}