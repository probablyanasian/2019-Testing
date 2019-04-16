/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
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

  public static enum cargoAutoState {
    IDLE, LINE, ANGLE; 
  }
  public static cargoAutoState curCargoAutoState = cargoAutoState.IDLE;

  public static enum cargoAutoSide {
    NONE, LEFT, RIGHT;
  }
  public static cargoAutoSide curCargoAutoSide = cargoAutoSide.LEFT;

  public static enum IRState {
    IDLE, TRUE, WAIT;
  }


  public static IRState curIRStateLeftOne = IRState.IDLE;
  public static IRState curIRStateLeftTwo = IRState.IDLE;
  public static IRState curIRStateLeftThree = IRState.IDLE;

  public static int counterLeftOne = 0;
  public static int counterLeftTwo = 0;
  public static int counterLeftThree = 0;

  public static IRState curIRStateRightOne = IRState.IDLE;
  public static IRState curIRStateRightTwo = IRState.IDLE;
  public static IRState curIRStateRightThree = IRState.IDLE;

  public static int counterRightOne = 0;
  public static int counterRightTwo = 0;
  public static int counterRightThree = 0;
  
  public static Encoder rightEncoder = new Encoder(9, 6);
  public static Encoder leftEncoder = new Encoder(7, 8); 
  public static int LeftEncoderTarget = 0;
  public static int RightEncoderTarget = 0;
  public static int MotorCurrentSum = 0;

  public static int[] lastLeftOne = new int[2];
  public static int[] lastLeftThree = new int[2];

  public static int[] lastRightOne = new int[2];
  public static int[] lastRightThree = new int[2];
  
  public static int maxReliableEncoder = 20; //currently around 5 inches //maximum distance we can trust the last known line encoder position to stay valid.
  public static int maxUltrasonicDist = 457; //Defined in MMs, currently around 13 inches
  public static int encoderErrorTolerance = 4;
  public static int ultrasonicErrorTolerance = 33; // in millimeters. //TODO change this.
  public static int halfIRDistance = 3; //in ticks, from the middle between the sensors. //TODO change this. //TODO William: check encoder Ratios.
  public static int IRDistance = 7; // in ticks, distance between 2 sensors //TODO, check to make sure.
  public static int oneInchEncoder = 4; 

  public static final boolean[][] activatedIRs = {{false, false},{false, false},{false, false}};
  public static int IRClearCounter = 0;
  
    //Ultrasonic Initiation
  public static Ultrasonic ultrasonicLeftOne = null; 
  public static Ultrasonic ultrasonicLeftTwo = null; 
  public static Ultrasonic ultrasonicRightOne = null;
  public static Ultrasonic ultrasonicRightTwo = null;
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  public static WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(14);
  public static WPI_TalonSRX leftBackMotor = new WPI_TalonSRX(15);
  public static WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(16);
  public static WPI_TalonSRX rightBackMotor = new WPI_TalonSRX(1);

  public static int[] lastLine = {20, 20};
}
