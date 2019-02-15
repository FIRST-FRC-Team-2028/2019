package com.phantommentalists;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.phantommentalists.Parameters;
import com.phantommentalists.Parameters.DriveGearbox;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSide {

    /** Reference to master motor controller */
    private TalonSRX master;

    /** Reference to follower motor controller.  ONLY USED with dual CIM motor gearbox on practice chassis */
    private TalonSRX practiceFollower;
  
    /** First of 3 follower motor controllers.  ONLY USED with quad 775 motor gearbox on competition chassis */
    private VictorSPX competitionFollower1;
  
    /** Second of 3 follower motor controllers.  ONLY USED with quad 775 motor gearbox on competition chassis */
    private VictorSPX competitionFollower2;
  
    /** Third of 3 follower motor controllers.  ONLY USED with quad 775 motor gearbox on competition chassis */
    private VictorSPX competitionFollower3;

    /** Class variable to determine how many motor controllers we have */
    private Parameters.DriveGearbox gearboxType;

    private Telepath robot;

    public DriveSide(boolean left, Parameters.DriveGearbox gearbox, Telepath robot) {
        gearboxType = gearbox;
        this.robot = robot;
        Parameters.CanId masterCanId = null;
        if (left) {
            masterCanId = Parameters.CanId.LEFT_MASTER_CAN_ID;
        } else {
            masterCanId = Parameters.CanId.RIGHT_MASTER_CAN_ID;
        }
        master = new TalonSRX(masterCanId.getCanId());
        master.setInverted(masterCanId.isInverted());
        master.configContinuousCurrentLimit(Parameters.DRIVE_CURRENT_LIMIT_CONTINUOUS);
        master.configPeakCurrentLimit(Parameters.DRIVE_CURRENT_LIMIT_PEAK);
        master.configPeakCurrentDuration(Parameters.DRIVE_CURRENT_LIMIT_PEAK_DURATION);
        master.enableCurrentLimit(true);
        if (left) {
            // This is the left side drive-train      
            switch (gearbox) {
                case TWO_MOTOR_GEARBOX:
                    practiceFollower = new TalonSRX(Parameters.CanId.LEFT_2_FOLLOWER_CAN_ID.getCanId());
                    practiceFollower.follow(master);
                    practiceFollower.setInverted(Parameters.CanId.LEFT_2_FOLLOWER_CAN_ID.isInverted());
                    break;
                case FOUR_MOTOR_GEARBOX:
                    competitionFollower1 = new VictorSPX(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_1.getCanId());
                    competitionFollower1.follow(master);
                    //competitionFollower1.set(ControlMode.Follower, masterCanId.getCanId());
                    competitionFollower1.setInverted(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_1.isInverted());
                    competitionFollower2 = new VictorSPX(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_2.getCanId());
                    competitionFollower2.follow(master);
                    competitionFollower2.setInverted(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_2.isInverted());
                    competitionFollower3 = new VictorSPX(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_3.getCanId());
                    competitionFollower3.follow(master);
                    competitionFollower3.setInverted(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_3.isInverted());
                    break;
            }
        } else {
            // This is the right side drive-train
            switch (gearbox) {
                case TWO_MOTOR_GEARBOX:
                    practiceFollower = new TalonSRX(Parameters.CanId.RIGHT_2_FOLLOWER_CAN_ID.getCanId());
                    practiceFollower.follow(master);
                    practiceFollower.setInverted(Parameters.CanId.RIGHT_2_FOLLOWER_CAN_ID.isInverted());
                    break;
                case FOUR_MOTOR_GEARBOX:
                    competitionFollower1 = new VictorSPX(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_1.getCanId());
                    competitionFollower1.follow(master);
                    competitionFollower1.setInverted(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_1.isInverted());
                    competitionFollower2 = new VictorSPX(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_2.getCanId());
                    competitionFollower2.follow(master);
                    competitionFollower2.setInverted(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_2.isInverted());
                    competitionFollower3 = new VictorSPX(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_3.getCanId());
                    competitionFollower3.follow(master);
                    competitionFollower3.setInverted(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_3.isInverted());
                    break;
            }        
        }
    }

    /**
     * Gets the current draw (in amps) for all the motors on this side of the drive
     * (it will account for a 2 motor drive or a 4 motor drive)
     * 
     * @return double Current in amps
     */
    public double getMotorCurrentOutput() {
        double outputCurrent =  master.getOutputCurrent();
        switch (gearboxType) {
            case TWO_MOTOR_GEARBOX:
                outputCurrent = outputCurrent + practiceFollower.getOutputCurrent();
                break;
            case FOUR_MOTOR_GEARBOX:
                PowerDistributionPanel pdp = robot.getPDP().getPdp();
                outputCurrent = outputCurrent + pdp.getCurrent(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_1.getChannel());
                outputCurrent = outputCurrent + pdp.getCurrent(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_2.getChannel());
                outputCurrent = outputCurrent + pdp.getCurrent(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_3.getChannel());
                break;
        }
        return outputCurrent;
    } 
    public void setPercentOutput(double speed)
    {
        master.set(ControlMode.PercentOutput, speed);
    }

    public void process() {
        SmartDashboard.putNumber("Drive: Left Current", getMotorCurrentOutput());
    }
}