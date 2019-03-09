/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;

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

/*   public static final WPI_TalonSRX leftDrive1 = new WPI_TalonSRX(14); // Front Left
	public static final WPI_TalonSRX leftDrive2 = new WPI_TalonSRX(15); // Back left
	public static final WPI_TalonSRX rightDrive1 = new WPI_TalonSRX(1); // Front Right
  public static final WPI_TalonSRX rightDrive2 = new WPI_TalonSRX(16); // Back Right */
  public static int index = 0;

  public static DigitalInput irLeft1 = new DigitalInput(0);
  public static DigitalInput irLeft2 = new DigitalInput(1);
  public static DigitalInput irLeft3 = new DigitalInput(2);
  public static DigitalInput irRight1 = new DigitalInput(3);
  public static DigitalInput irRight2 = new DigitalInput(4);
  public static DigitalInput irRight3 = new DigitalInput(5);

  public static enum IRState {
    IDLE, TRUE, WAIT;
  }
  public static IRState curIRStateLeftOne;
  public static IRState curIRStateLeftTwo;
  public static IRState curIRStateLeftThree;

  public static int counterLeftOne = 0;
  public static int counterLeftTwo = 0;
  public static int counterLeftThree = 0;

  public static IRState curIRStateRightOne;
  public static IRState curIRStateRightTwo;
  public static IRState curIRStateRightThree;

  public static int counterRightOne = 0;
  public static int counterRightTwo = 0;
  public static int counterRightThree = 0;

    //Ultrasonic Initiation
  public static Ultrasonic ultrasonicLeftOne = new Ultrasonic(10, 11);
  public static Ultrasonic ultrasonicLeftTwo = new Ultrasonic(12, 13);
  public static Ultrasonic ultrasonicRightOne = new Ultrasonic(14, 15);
  public static Ultrasonic ultrasonicRightTwo = new Ultrasonic(16, 17);
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
