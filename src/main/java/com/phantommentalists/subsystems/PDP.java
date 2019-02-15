/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.phantommentalists.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
//import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Arrays;

import com.phantommentalists.Parameters;

/**
 * Control the distribution of power
 * Monitor the current going to motors.
 */
public class PDP {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private PowerDistributionPanel pdp;

  private double[] driveCurrent;

  public PDP() {
    pdp = new PowerDistributionPanel();
    driveCurrent = new double[8];
  }

  /** 
   * Getter to return reference to PowerDistributionPannel
   * 
   * @return PowerDistributionPanel The robot's PowerDistributionPanel
   */
  public PowerDistributionPanel getPdp() {
      return pdp;
  }

  /**
   * Gets the drive current.
   * 
   * @return double of the drive current
   */
    public double[] getDriveCurrent(Parameters.DriveGearbox gearbox) {
        switch (gearbox){
          case TWO_MOTOR_GEARBOX:
            driveCurrent[0]  =
                pdp.getCurrent(Parameters.CanId.LEFT_MASTER_CAN_ID.getChannel());
            driveCurrent[1]  = 
                pdp.getCurrent(Parameters.CanId.LEFT_2_FOLLOWER_CAN_ID.getChannel());
            driveCurrent[2]  = 
                pdp.getCurrent(Parameters.CanId.RIGHT_MASTER_CAN_ID.getChannel());
            driveCurrent[3] = 
                pdp.getCurrent(Parameters.CanId.RIGHT_2_FOLLOWER_CAN_ID.getChannel());
            SmartDashboard.putNumberArray("Drive Motor Current", Arrays.copyOfRange(driveCurrent, 0, 2));
            SmartDashboard.putNumber("pdp_0", driveCurrent[0]);
            return Arrays.copyOfRange(driveCurrent, 0, 4);
          case FOUR_MOTOR_GEARBOX:
            driveCurrent[0]  =
                pdp.getCurrent(Parameters.CanId.LEFT_MASTER_CAN_ID.getChannel());
            driveCurrent[1]  = 
                pdp.getCurrent(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_1.getChannel());
            driveCurrent[2]  = 
                pdp.getCurrent(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_2.getChannel());
            driveCurrent[3]  = 
                pdp.getCurrent(Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_3.getChannel());
            driveCurrent[4] = 
                pdp.getCurrent(Parameters.CanId.RIGHT_MASTER_CAN_ID.getChannel());
            driveCurrent[5] = 
                pdp.getCurrent(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_1.getChannel());
            driveCurrent[6] = 
                pdp.getCurrent(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_2.getChannel());
            driveCurrent[7] = 
                pdp.getCurrent(Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_3.getChannel());
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.LEFT_MASTER_CAN_ID.getChannel(), driveCurrent[0]);
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_1.getChannel(), driveCurrent[1]);
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_2.getChannel(), driveCurrent[2]);
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.LEFT_4_FOLLOWER_CAN_ID_3.getChannel(), driveCurrent[3]);
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.RIGHT_MASTER_CAN_ID.getChannel(), driveCurrent[4]);
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_1.getChannel(), driveCurrent[5]);
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_2.getChannel(), driveCurrent[6]);
            SmartDashboard.putNumber("pdp_"+Parameters.CanId.RIGHT_4_FOLLOWER_CAN_ID_3.getChannel(), driveCurrent[7]);
            SmartDashboard.putNumberArray("Drive Motor Current", driveCurrent);
        }
        return driveCurrent;
    }
}
