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
    requires(Robot.ultrasonicSystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  boolean finished = false;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.lineDetector.lastLineDetected();
    //Checks if there isn't a line already sensed.
    if(Robot.lineDetector.getIRSensors() == 0){

      //Checks if the previously gotten value is beneath the maximum encoder reliability value.
      if ((Math.abs(RobotMap.lastLeftOne[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
        (Math.abs(RobotMap.lastLeftOne[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        Robot.drive.setLeftPosition(RobotMap.lastLeftOne[0] + RobotMap.halfIRDistance); //TODO check these
        Robot.drive.setRightPosition(RobotMap.lastLeftOne[1] + RobotMap.halfIRDistance); //TODO check these
      }
      else if ((Math.abs(RobotMap.lastRightOne[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
        (Math.abs(RobotMap.lastRightOne[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        Robot.drive.setLeftPosition(RobotMap.lastRightOne[0] + RobotMap.halfIRDistance); //TODO check these
        Robot.drive.setRightPosition(RobotMap.lastRightOne[1] + RobotMap.halfIRDistance); //TODO check these
      }
      else if ((Math.abs(RobotMap.lastLeftThree[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
        (Math.abs(RobotMap.lastLeftThree[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        Robot.drive.setLeftPosition(RobotMap.lastLeftThree[0] - RobotMap.halfIRDistance); //TODO check these
        Robot.drive.setRightPosition(RobotMap.lastLeftThree[1] - RobotMap.halfIRDistance); //TODO check these
      }
      else if ((Math.abs(RobotMap.lastRightThree[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder) &&
        (Math.abs(RobotMap.lastRightThree[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        Robot.drive.setLeftPosition(RobotMap.lastRightThree[0] - RobotMap.halfIRDistance); //TODO check these
        Robot.drive.setRightPosition(RobotMap.lastRightThree[1] - RobotMap.halfIRDistance); //TODO check these
      }

      else {
        int encoderErrorTolerance = RobotMap.encoderErrorTolerance;

        //Robot has driven to be below the PID tolerance.
        if(Math.abs(Robot.drive.LeftError) <= encoderErrorTolerance &&
          Math.abs(Robot.drive.RightError) <= encoderErrorTolerance) {
            //If the middle sensor isn't activated, continue driving fowards.
          if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == 1 ||
            (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == 1) {

            //Add 1 inch to current value
            int precalcL = Robot.drive.getLeftEncoder() + RobotMap.oneInchEncoder;
            int precalcR = Robot.drive.getRightEncoder() + RobotMap.oneInchEncoder;

            //Move the one inch fowards
            Robot.drive.setLeftPosition(precalcL);
            Robot.drive.setRightPosition(precalcR);
            }
          //If the middle sensor is activated, stop where it is.
          else {
            Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder());
            Robot.drive.setRightPosition(Robot.drive.getRightEncoder());
          }
        }
      }
    }
      //Checks if there is an IR that's been activated.
    if(Robot.lineDetector.getIRSensors() != 0) {

      //Warning if too far, aka 18 inches ish. //TODO check.
      if(Robot.ultrasonicSystem.getLeftDist() >= RobotMap.maxUltrasonicDist){
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L1) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L3) == 1){
          System.err.println("ABORT ABORT LEFT DISTANCE TOO FAR");
          finished = true;
        }
      }

      //Warning if too far, aka 18 inches ish. //TODO check.
      if(Robot.ultrasonicSystem.getRightDist() >= RobotMap.maxUltrasonicDist){
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R1) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R3) == 1){
          System.err.println("ABORT ABORT RIGHT DISTANCE TOO FAR");
          finished = true;
        }
      }

      //Only Left middle is activated.
      if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L1) == 0 && 
      (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == 1 &&
      (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L3) == 0) {
        //Checks if it's angled too far.
        if((Robot.ultrasonicSystem.getLeftValues()[0] - Robot.ultrasonicSystem.getLeftValues()[1]) >= RobotMap.ultrasonicErrorTolerance) {

          //Temp variables, to ensure that the math is done in the proper order, as Java doesn't abide by PEMDAS.
          int leftDifference = Robot.ultrasonicSystem.getLeftValues()[0]-Robot.ultrasonicSystem.getLeftValues()[1];
          int distToTicks = leftDifference / -7; //TODO verify my math. pls k thx
          int toDriveLeft = distToTicks + Robot.drive.getLeftEncoder();
          int toDriveRight = Robot.drive.getRightEncoder() - distToTicks;

          //Turns it the proper amount of ticks
          Robot.drive.setLeftPosition(toDriveLeft);
          Robot.drive.setRightPosition(toDriveRight);
        }
        //If it isn't too far, stop the movement.
        else {
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder());
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder());
        }
      }
      //Only if Right middle is activated.
      if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R1) == 0 &&
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == 1 &&
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R3) == 0){
        //Checks that the ultrasonic is over the tolerance threshold
        if((Robot.ultrasonicSystem.getRightValues()[0] - Robot.ultrasonicSystem.getRightValues()[1]) >= RobotMap.ultrasonicErrorTolerance) {

          //Temp variables, to ensure that the math is done in the proper order, as Java doesn't abide by PEMDAS.
          //rightDifference is the error off in MMs
          int rightDifference = Robot.ultrasonicSystem.getRightValues()[0]-Robot.ultrasonicSystem.getRightValues()[1];
          //Divide by 7 to get tick distance
          int distToTicks = rightDifference / -7;//TODO verify my math.
          //Drive in the direction
          int toDriveLeft = Robot.drive.getLeftEncoder() - distToTicks;
          //Also drive in the right direction
          int toDriveRight = distToTicks + Robot.drive.getRightEncoder();

          //Drive to the proper position
          Robot.drive.setLeftPosition(toDriveLeft);
          Robot.drive.setRightPosition(toDriveRight);
        }
      }
      
      //Checks if any of the middle sensor has been tripped.
      if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == 1 ||
      (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == 1) {

        //Checks if the front sensor has been tripped
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L1) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R1) == 1) {

          //Make the robot move 1 inch fowards.
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() + RobotMap.oneInchEncoder);
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder() + RobotMap.oneInchEncoder);
        }

        //Checks if the back sensor has been tripped
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L3) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R3) == 1) {

          //Make the robot move 1 inch backwards
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() - RobotMap.oneInchEncoder);
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder() - RobotMap.oneInchEncoder);
        }
      }

      //Checks that the middle sensor is NOT tripped
      if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == 0 ||
      (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == 0) {

        //Checks that the back sensor IS tripped
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L3) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R3) == 1) {
                    
          //Move the robot
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() - RobotMap.halfIRDistance);
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder() - RobotMap.halfIRDistance);
        }

        //Checks that the front sensor IS tripped
        if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L1) == 1 ||
        (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R1) == 1) {

          //Move the robot
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() + RobotMap.halfIRDistance);
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder() + RobotMap.halfIRDistance);
        }
      }
    }
    //Checks if all of the positions have been met, if they have, end().
    if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L1) == 0 &&
    (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L2) == 1 &&
    (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_L3) == 0 &&
    (Robot.ultrasonicSystem.getLeftValues()[0] - Robot.ultrasonicSystem.getLeftValues()[1]) <= RobotMap.ultrasonicErrorTolerance) {
      finished = true;
    }

    if((Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R1) == 0 &&
      (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R2) == 1 &&
      (Robot.lineDetector.getIRSensors() & LineDetector.SENSOR_R3) == 0 &&
      (Robot.ultrasonicSystem.getRightValues()[0] - Robot.ultrasonicSystem.getRightValues()[1]) <= RobotMap.ultrasonicErrorTolerance) {
      finished = true;
    }
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
    //TODO reset counter and stored things.
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}
