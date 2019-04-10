/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.LineDetector;

public class CargoLineAuto extends Command {
  public CargoLineAuto() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drive);
    requires(Robot.lineDetector);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
  
  boolean finished = false;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    switch(RobotMap.curCargoAutoState) {

      case IDLE:
        if(Robot.m_oi.getDriverStick().getRawButton(6)) {
          RobotMap.curCargoAutoState = RobotMap.cargoAutoState.LINE;
        }
        else {
          RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
        }
        break;

      case LINE:
        if(!Robot.m_oi.getDriverStick().getRawButton(6)) {
        RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
        break;
        }

        //Checks if the previously gotten value is beneath the maximum encoder reliability value.
        if ((Math.abs(RobotMap.lastLeftOne[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
          (Math.abs(RobotMap.lastLeftOne[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
          RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.LEFT;
          Robot.drive.setLeftPosition(RobotMap.lastLeftOne[0] + RobotMap.halfIRDistance); //TODO check these
          Robot.drive.setRightPosition(RobotMap.lastLeftOne[1] + RobotMap.halfIRDistance); //TODO check these
        }

        else if ((Math.abs(RobotMap.lastRightOne[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
          (Math.abs(RobotMap.lastRightOne[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
          RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.RIGHT;
          Robot.drive.setLeftPosition(RobotMap.lastRightOne[0] + RobotMap.halfIRDistance); //TODO check these
          Robot.drive.setRightPosition(RobotMap.lastRightOne[1] + RobotMap.halfIRDistance); //TODO check these
        }

        else if ((Math.abs(RobotMap.lastLeftThree[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
          (Math.abs(RobotMap.lastLeftThree[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
          RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.LEFT;
          Robot.drive.setLeftPosition(RobotMap.lastLeftThree[0] - RobotMap.halfIRDistance); //TODO check these
          Robot.drive.setRightPosition(RobotMap.lastLeftThree[1] - RobotMap.halfIRDistance); //TODO check these
        }

        else if ((Math.abs(RobotMap.lastRightThree[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
          (Math.abs(RobotMap.lastRightThree[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
          RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.RIGHT;
          Robot.drive.setLeftPosition(RobotMap.lastRightThree[0] - RobotMap.halfIRDistance); //TODO check these
          Robot.drive.setRightPosition(RobotMap.lastRightThree[1] - RobotMap.halfIRDistance); //TODO check these
        }

        else if(Robot.lineDetector.getIRSensors() == 0) {
          //Robot has driven to be below the PID tolerance.
          if(Math.abs(Robot.drive.LeftError) <= RobotMap.encoderErrorTolerance &&
            Math.abs(Robot.drive.RightError) <= RobotMap.encoderErrorTolerance) {

            //Move the one inch fowards
            Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() + RobotMap.oneInchEncoder);
            Robot.drive.setRightPosition(Robot.drive.getRightEncoder() + RobotMap.oneInchEncoder);
          }
        }
        
        else if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == LineDetector.SENSOR_L2 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == LineDetector.SENSOR_R2) {
          RobotMap.curCargoAutoState = RobotMap.cargoAutoState.ANGLE;
        }

        break;
      
      case ANGLE:
        if(!Robot.m_oi.getDriverStick().getRawButton(6)) {
          RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
          break;
        }
        if(Robot.lineDetector.getIRSensors() == 0) {
          RobotMap.curCargoAutoState = RobotMap.cargoAutoState.LINE;
          break;
        }
        switch(RobotMap.curCargoAutoSide) {

          case NONE:
            System.err.println("ERROR: NONE sided");
            RobotMap.curCargoAutoState = RobotMap.cargoAutoState.LINE;
            break;

          case LEFT:
            if(Robot.ultrasonicSubsystem.getLeftDist() >= RobotMap.maxUltrasonicDist) {
              System.err.println("ERROR: Too far, get closer");
            }
            
            else {
              if((Robot.ultrasonicSubsystem.getLeftValues()[0] - Robot.ultrasonicSubsystem.getLeftValues()[1]) >= RobotMap.ultrasonicErrorTolerance) {

                int leftDifference = Robot.ultrasonicSubsystem.getLeftValues()[0]-Robot.ultrasonicSubsystem.getLeftValues()[1];
                int distToTicks = leftDifference / -7; //TODO verify my math properly
                //Turns it the proper amount of ticks

                Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() + distToTicks);
                Robot.drive.setRightPosition(Robot.drive.getRightEncoder() - distToTicks);
              }
              else {
                finished = true;
              }
            }
            break;

          case RIGHT:
            if(Robot.ultrasonicSubsystem.getRightDist() >= RobotMap.maxUltrasonicDist) {
              System.err.println("ERROR: Too far, get closer");
            }
            else {
              if((Robot.ultrasonicSubsystem.getRightValues()[0] - Robot.ultrasonicSubsystem.getRightValues()[1]) >= RobotMap.ultrasonicErrorTolerance) {
                
                int rightDifference = Robot.ultrasonicSubsystem.getRightValues()[0] - Robot.ultrasonicSubsystem.getRightValues()[1];
                int distToTicks = rightDifference / -7; //TODO verify my math properly
                //Turns it the proper amount of ticks

                Robot.drive.setRightPosition(Robot.drive.getRightEncoder() - distToTicks);
                Robot.drive.setRightPosition(Robot.drive.getRightEncoder() + distToTicks);
              }
              else {
                finished = true;
              }
            }
            break;
        }
        break;
    }

    /* --}
      //Checks if there is an IR that's been activated.
    if(Robot.lineDetector.getIRSensors() != 0) {

      //Checks if any of the middle sensor has been tripped.
      if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == LineDetector.SENSOR_L2 ||
      (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == LineDetector.SENSOR_R2) {

        //Checks if the front sensor has been tripped
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L1) == LineDetector.SENSOR_L1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R1) == LineDetector.SENSOR_R1) {

          //Make the robot move 1 inch fowards.
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() + RobotMap.oneInchEncoder);
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder() + RobotMap.oneInchEncoder);
        }

        //Checks if the back sensor has been tripped
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L3) == LineDetector.SENSOR_L3 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R3) == LineDetector.SENSOR_L3) {

          //Make the robot move 1 inch backwards
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() - RobotMap.oneInchEncoder);
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder() - RobotMap.oneInchEncoder);
        } 
      } 
    } */
     
    /* -- //Checks if all of the positions have been met, if they have, end().
    if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == 1) {
      finished = true;
    }

    if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == 1) {
      finished = true;
    } */
}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder());
    Robot.drive.setRightPosition(Robot.drive.getRightEncoder());
    Robot.drive.setAllSpeed(0, 0);
    RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
    RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.NONE;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}
