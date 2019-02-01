/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  public static Ultrasonic ultrasonicOne = new Ultrasonic(0, 1);
  public static Ultrasonic ultrasonicTwo = new Ultrasonic(2, 3);
  public static Ultrasonic ultrasonicThree = new Ultrasonic(4, 5);
  public static Ultrasonic ultrasonicFour = new Ultrasonic(6, 7);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();


  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {;
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
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
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    ///Sets all the ultrasonics to manual mode, NOT automatic.
    ultrasonicOne.setAutomaticMode(false);
    
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    /*
    //System.out.println("GyroscopeSubsystem Angle:" + gyroscope.getGyroAngle());
    if(RobotMap.x > 100){
       System.out.println("Ultrasonic Voltage:" + ultrasonicsecond.getVoltage());
    }
    RobotMap.x += 1;
    System.out.println("Cycle count:" + RobotMap.x);
    //System.out.println("Ultrasonic Voltage B:" + ultrasonic.getVoltage());
    */
    /* if(RobotMap.pingWhich) {
      if(RobotMap.ultrasonicPing) {
        ultrasonicOne.ping();
        RobotMap.ultrasonicPing = false;
      }
      
      else {
        if(ultrasonicOne.isRangeValid()){
          System.out.print("Ultrasonic One: " + Math.floor(ultrasonicOne.getRangeInches()) + " ");
          RobotMap.pingWhich = !RobotMap.pingWhich;
          RobotMap.ultrasonicPing = true;
        }
      }
    }

    else {
      if(RobotMap.ultrasonicPing) {
        ultrasonicTwo.ping();
        RobotMap.ultrasonicPing = false;
      }
      if(ultrasonicTwo.isRangeValid()){
      System.out.println("Ultrasonic Two: " + Math.floor(ultrasonicTwo.getRangeInches()));
      RobotMap.pingWhich = !RobotMap.pingWhich;
      RobotMap.ultrasonicPing = true;
      }
    } */

    if(RobotMap.pingWhich == 0) {
      if(RobotMap.ultrasonicPing) {
        ultrasonicOne.ping();
        RobotMap.ultrasonicPing = false;
      }
      
      else {
        if(ultrasonicOne.isRangeValid()){
          System.out.print("Ultrasonic One: " + Math.floor(ultrasonicOne.getRangeInches()) + " ");
          RobotMap.pingWhich += 1;
          RobotMap.ultrasonicPing = true;
        }
      }
    }

    else if(RobotMap.pingWhich == 1) {
      if(RobotMap.ultrasonicPing) {
        ultrasonicTwo.ping();
        RobotMap.ultrasonicPing = false;
      }
      if(ultrasonicTwo.isRangeValid()){
      System.out.println("Ultrasonic Two: " + Math.floor(ultrasonicTwo.getRangeInches()));
      RobotMap.pingWhich += 1;
      RobotMap.ultrasonicPing = true;
      }
    }

    else if(RobotMap.pingWhich == 2) {
      if(RobotMap.ultrasonicPing) {
        ultrasonicThree.ping();
        RobotMap.ultrasonicPing = false;
      }
      if(ultrasonicThree.isRangeValid()){
      System.out.print("Ultrasonic Three: " + Math.floor(ultrasonicThree.getRangeInches()));
      RobotMap.pingWhich += 1;
      RobotMap.ultrasonicPing = true;
      }
    }

    else if(RobotMap.pingWhich == 3) {
      if(RobotMap.ultrasonicPing) {
        ultrasonicFour.ping();
        RobotMap.ultrasonicPing = false;
      }
      if(ultrasonicFour.isRangeValid()){
      System.out.println("Ultrasonic Three: " + Math.floor(ultrasonicFour.getRangeInches()));
      RobotMap.pingWhich = 0;
      RobotMap.ultrasonicPing = true;
      }
    }

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
