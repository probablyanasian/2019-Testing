/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class UltrasonicSystem extends Subsystem {

   /**
   * Gets the angle at the left side of the robot in relation to the wall beside it.
   * @return left in array
   */

  public int[] getLeftValues() {
    int[] array = new int[]{(int)RobotMap.ultrasonicLeftOne.getRangeMM(), (int)RobotMap.ultrasonicLeftTwo.getRangeMM()};
    return(array);
  }
  /**  */
  public int[] getRightValues() {
    int[] array = new int[]{(int)RobotMap.ultrasonicRightOne.getRangeMM(), (int)RobotMap.ultrasonicRightTwo.getRangeMM()};
    return(array);
  }

  public int getLeftDist() {
    return((getLeftValues()[0] + getLeftValues()[1])/2);
  }

  public int getRightDist() {
    return((getRightValues()[0] + getRightValues()[1])/2);
  }

  /* Changed for more readable stuf.
  public int[] getLeftValues() {
    int leftOne = -1;
    int leftTwo = -1;
    int[] array = new int[2];
    boolean ultrasonicPingWhichLeft = true;
    boolean ultrasonicLeftPing = true;

    if(ultrasonicPingWhichLeft) {
      if(ultrasonicLeftPing) {
        RobotMap.ultrasonicLeftOne.ping();
        ultrasonicLeftPing = false;
      }
      
      else {
        if(RobotMap.ultrasonicLeftOne.isRangeValid()){
          leftOne = (int)RobotMap.ultrasonicLeftOne.getRangeMM();
          ultrasonicPingWhichLeft = !ultrasonicPingWhichLeft;
          ultrasonicLeftPing = true;
        }
      }
    }

    else {
      if(ultrasonicLeftPing) {
        RobotMap.ultrasonicLeftTwo.ping();
        ultrasonicLeftPing = false;
      }
      if(RobotMap.ultrasonicLeftTwo.isRangeValid()){
      leftTwo = (int)RobotMap.ultrasonicLeftTwo.getRangeMM();
      ultrasonicPingWhichLeft = !ultrasonicPingWhichLeft;
      ultrasonicLeftPing = true;
      }
    }
    array[0] = leftOne;
    array[1] = leftTwo;
    return(array);
  } */
  
  /* MUCH MUCH MUCH better method of doing it now.
  public int[] getRightValues() {
    int[] array = new int[1];
    int rightOne = -1;
    int rightTwo = -1;
    boolean ultrasonicPingWhichRight = true;
    boolean ultrasonicRightPing = true;

    if(ultrasonicPingWhichRight) {
      if(ultrasonicRightPing) {
        RobotMap.ultrasonicRightOne.ping();
        ultrasonicRightPing = false;
      }
      
      else {
        if(RobotMap.ultrasonicRightOne.isRangeValid()){
          rightOne = (int)RobotMap.ultrasonicRightOne.getRangeMM();
          ultrasonicPingWhichRight = !ultrasonicPingWhichRight;
          ultrasonicRightPing = true;
        }
      }
    }

    else {
      if(ultrasonicRightPing) {
        RobotMap.ultrasonicRightTwo.ping();
        ultrasonicRightPing = false;
      }
      if(RobotMap.ultrasonicRightTwo.isRangeValid()){
      rightTwo = (int)RobotMap.ultrasonicRightTwo.getRangeMM();
      ultrasonicPingWhichRight = !ultrasonicPingWhichRight;
      ultrasonicRightPing = true;
      }
    }

    array[0] = rightOne;
    array[1] = rightTwo;

    return(array);
  } */

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
