/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.Thread;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
//import frc.robot.commands.UltSonic;
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
  public static GyroscopeSubsystem gyroscope = new GyroscopeSubsystem(RobotMap.gyro);
  public static UltrasonicSubsystem ultrasonic = new UltrasonicSubsystem();
  public static Ultrasonic potato = new Ultrasonic(0, 1);
  public static Ultrasonic totato = new Ultrasonic(2, 3);

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
  public void robotPeriodic() {
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
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */
    potato.setAutomaticMode(false);
    //totato.setAutomaticMode(false);

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

    //Ultrasonic potato = new Ultrasonic(0, 1);
    if(RobotMap.baz) {
      if(RobotMap.foo) {
      potato.ping();
      RobotMap.foo = false;
    }
    
    if(potato.isRangeValid()){
     //System.out.println("Potato: " +  potato.getRangeInches());
      System.out.print("Potato: " + Math.floor(potato.getRangeInches()) + " ");
      RobotMap.foo = true;
      RobotMap.baz = false;
    }
  }
    if(!RobotMap.baz) {
    if(RobotMap.bar) {
      totato.ping();
      RobotMap.bar = false;
    }
    
    if(totato.isRangeValid()){
      //System.out.println("Totato: " +  totato.getRangeInches());

      System.out.println("Totato: " + Math.floor(totato.getRangeInches()));
      RobotMap.bar = true;
      RobotMap.baz = true;
    }
  }


    //potato.close();

/*     Ultrasonic totato = new Ultrasonic(2, 3);
    totato.setAutomaticMode(true);
    System.out.println("Totato: " +  totato.getRangeInches());
    totato.close(); */

  } //END OF AUTONOMOU

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
