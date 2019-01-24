/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.robot.Robot;
/**
 * Add your docs here.
 */
public class Gyroscope extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  ADXRS450_Gyro gyro; 

  public Gyroscope(ADXRS450_Gyro g){
    gyro = g;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  /**
   * Gets the angle of the gyroscope
   * @return the angle of the gyroscope in double 
   */
  public double getGyroAngle(){
    return gyro.getAngle();
  }

  /**
   * Resets the angle of the gyroscope to 0
   */
  public void resetAngle(){
    gyro.reset();
  }
}
