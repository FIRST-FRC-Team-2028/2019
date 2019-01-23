package com.phantommentalists;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.phantommentalists.Parameters;

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

    public DriveSide(boolean left, Parameters.DriveGearbox gearbox) {
        Parameters.CanId masterCanId = null;
        if (left) {
            masterCanId = Parameters.CanId.LEFT_MASTER_CAN_ID;
        } else {
            masterCanId = Parameters.CanId.RIGHT_MASTER_CAN_ID;
        }
        master = new TalonSRX(masterCanId.getCanId());
        if (left) {
            // This is the left side drive-train      
            switch (gearbox) {
                case TWO_MOTOR_GEARBOX:
                    // TODO:  Construct instances for left-side practiceFollower
                    break;
                case FOUR_MOTOR_GEARBOX:
                    // TODO:  Construct instances for left-side competitionFollower1, competitionFollower2 and competitionFollower3
                    break;
            }
        } else {
            // This is the right side drive-train
            switch (gearbox) {
                case TWO_MOTOR_GEARBOX:
                    // TODO:  Construct instances for right-side practiceFollower
                    break;
                case FOUR_MOTOR_GEARBOX:
                    // TODO:  Construct instances for right-side competitionFollower1, competitionFollower2 and competitionFollower3
                    break;
            }        
        }
    }
}