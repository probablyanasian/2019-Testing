/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
<<<<<<< HEAD
=======
  public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  AnalogInput ultrasonic = new AnalogInput(1);
  public static int ultrasonicPortsecond = 2;
  public static int ultrasonicPort = 1;
  public static int x = 0; 
  public static int ultrasonicpingPort = 0;
  public static int ultrasonicoutputPort = 1;
>>>>>>> parent of 3475d60... WORKING CODE
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:

}
