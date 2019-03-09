/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

//import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Sends information about line under robot.
 */
public class LineDetector extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // Obsolete
  /*
   * public DigitalInput[][] getIrSensors() { DigitalInput[][] sensorArray =
   * {{RobotMap.irLeft1, RobotMap.irRight1}, {RobotMap.irLeft2,
   * RobotMap.irRight1}, {RobotMap.irLeft3, RobotMap.irRight3}, {RobotMap.irLeft4,
   * RobotMap.irRight4}}; return sensorArray; }
   */

  public static final int SENSOR_L1 = 1 << 0;
  public static final int SENSOR_L2 = 1 << 1;
  public static final int SENSOR_L3 = 1 << 2;
  public static final int SENSOR_R1 = 1 << 3;
  public static final int SENSOR_R2 = 1 << 4;
  public static final int SENSOR_R3 = 1 << 5;

  public int getIRSensors() {

    //Switch is activated by the current IR state
    switch (RobotMap.curIRStateLeftOne) {
      //when IDLE
      case IDLE:
        //if the ir is True/Tripped
        if(!RobotMap.irLeft1.get()) {
          //Make the state true now.
          RobotMap.curIRStateLeftOne = RobotMap.IRState.TRUE;
        }
        break;
      case TRUE:
        //counter to implement hang time.
        RobotMap.counterLeftOne = 2;
        //when the sensor goes off again, change the state to WAIT
        if(RobotMap.irLeft1.get()) {
          RobotMap.curIRStateLeftOne = RobotMap.IRState.WAIT;
        }
        break;
      case WAIT:
        //If it becomes true again, set back to TRUE
        if(!RobotMap.irLeft1.get()) {
          RobotMap.curIRStateLeftOne = RobotMap.IRState.TRUE;
        }
        //If it's less than or equal to zero, make it IDLE again
        else if(RobotMap.counterLeftOne <= 0) {
          RobotMap.curIRStateLeftOne = RobotMap.IRState.IDLE;
        }
        //Otherwise, decrease the counter by one.
        else {
            RobotMap.counterLeftOne -= 1;
          }
        break;
      }

    switch (RobotMap.curIRStateLeftTwo) {
      case IDLE:
        if(!RobotMap.irLeft2.get()) {
          RobotMap.curIRStateLeftTwo = RobotMap.IRState.TRUE;
        } 
        break;
      case TRUE:
        RobotMap.counterLeftTwo = 2;
        if (RobotMap.irLeft2.get()) {
          RobotMap.curIRStateLeftTwo = RobotMap.IRState.WAIT;
        }
        break;
      case WAIT:
        if(!RobotMap.irLeft2.get()) {
          RobotMap.curIRStateLeftTwo = RobotMap.IRState.TRUE;
        } 
        else if(RobotMap.counterLeftTwo <= 0) {
          RobotMap.curIRStateLeftTwo = RobotMap.IRState.IDLE;
        }
        else {
          RobotMap.counterLeftTwo -= 1;
          }
        break;
      }

    switch (RobotMap.curIRStateLeftThree) {
      case IDLE:
        if(!RobotMap.irLeft3.get()) {
          RobotMap.curIRStateLeftThree = RobotMap.IRState.TRUE;
        }
        break;
      case TRUE:
        RobotMap.counterLeftThree = 2;
        if(RobotMap.irLeft3.get()) {
          RobotMap.curIRStateLeftThree = RobotMap.IRState.WAIT;
        }
        break;
      case WAIT:
        if(!RobotMap.irLeft3.get()) {
          RobotMap.curIRStateLeftThree = RobotMap.IRState.TRUE;
        }
        else if(RobotMap.counterLeftThree <= 0) {
          RobotMap.curIRStateLeftThree = RobotMap.IRState.IDLE;
        }
        else {
          RobotMap.counterLeftThree -= 1;
          }
        break;
      }

    switch (RobotMap.curIRStateRightOne) {
      case IDLE:
        if(!RobotMap.irRight1.get()) {
          RobotMap.curIRStateRightOne = RobotMap.IRState.TRUE;
        }
        break;
      case TRUE:
        RobotMap.counterRightOne = 2;
        if(RobotMap.irRight1.get()) {
          RobotMap.curIRStateRightOne = RobotMap.IRState.WAIT;
        }
        break;
      case WAIT:
        if(!RobotMap.irRight1.get()) {
          RobotMap.curIRStateRightOne = RobotMap.IRState.TRUE;
        }
        else if(RobotMap.counterRightOne <= 0) {
          RobotMap.curIRStateRightOne = RobotMap.IRState.IDLE;
        }
        else {
          RobotMap.counterRightOne -= 1;
          }
        break;
      }

    switch (RobotMap.curIRStateRightTwo) {
      case IDLE:
        if(!RobotMap.irRight2.get()) {
          RobotMap.curIRStateRightTwo = RobotMap.IRState.TRUE;
        }
        break;
      case TRUE:
        RobotMap.counterRightTwo = 2;
        if(RobotMap.irRight2.get()) {
          RobotMap.curIRStateRightTwo = RobotMap.IRState.WAIT;
        }
        break;
      case WAIT:
        if(!RobotMap.irRight2.get()) {
          RobotMap.curIRStateRightTwo = RobotMap.IRState.TRUE;
        }
        else if(RobotMap.counterRightTwo <= 0) {
          RobotMap.curIRStateRightTwo = RobotMap.IRState.IDLE;
        }
        else {
          RobotMap.counterRightTwo -= 1;
          }
        break;
      }

    switch (RobotMap.curIRStateRightThree) {
      case IDLE:
        if(!RobotMap.irRight3.get()) {
          RobotMap.curIRStateRightThree = RobotMap.IRState.TRUE;
        }
        break;
      case TRUE:
        RobotMap.counterRightThree = 2;
        if(RobotMap.irRight3.get()) {
          RobotMap.curIRStateRightThree = RobotMap.IRState.WAIT;
        }
        break;
      case WAIT:
        if(!RobotMap.irRight3.get()) {
          RobotMap.curIRStateRightTwo = RobotMap.IRState.TRUE;
        } 
        else if(RobotMap.counterRightThree <= 0) {
          RobotMap.curIRStateRightThree = RobotMap.IRState.IDLE;
        }
        else {
          RobotMap.counterRightThree -= 1;
          }
        break;
      }
    
    int sensorsOn = 0;
    //if the first counter on the left, and the second on the right are "true/greater than 1:
    if(RobotMap.counterLeftOne >= 1 && RobotMap.counterLeftTwo >=1 ) {
      //set the bit on sensorsOn specified by SENSOR_L1 to 1, aka True
      sensorsOn |= SENSOR_L1;
    }

    if(RobotMap.counterLeftTwo >= 1 && RobotMap.counterLeftTwo >= 1 ) {
      sensorsOn |= SENSOR_L3;
    }

    if(RobotMap.counterRightOne >= 1 && RobotMap.counterRightTwo >=1 ) { 
      sensorsOn |= SENSOR_R1;
    }

    if(RobotMap.counterRightTwo >= 1 && RobotMap.counterRightTwo >= 1 ) {
      sensorsOn |= SENSOR_R3;
    }
    
    if(!RobotMap.irLeft2.get()) {
      sensorsOn |= SENSOR_L2;
    }

    if(!RobotMap.irRight2.get()) {
      sensorsOn |= SENSOR_R2;
    }

    return(sensorsOn);
  }
  
  /* public void lastLineDetected() {
    if((getIRSensors() & LineDetector.SENSOR_L1) == 1 &&
    (getIRSensors() & LineDetector.SENSOR_L2) == 1) {
      RobotMap.lastLeftOne[0] = RobotMap.LeftEncoder.getRaw();
      RobotMap.lastLeftOne[1] = RobotMap.RightEncoder.getRaw();
    }
    if((getIRSensors() & LineDetector.SENSOR_L3) == 1 &&
    (getIRSensors() & LineDetector.SENSOR_L2) == 1) {
      RobotMap.lastLeftThree[0] = RobotMap.LeftEncoder.getRaw();
      RobotMap.lastLeftThree[1] = RobotMap.RightEncoder.getRaw();
    } 
    if((getIRSensors() & LineDetector.SENSOR_R1) == 1 && 
    (getIRSensors() & LineDetector.SENSOR_R2) == 1) {
      RobotMap.lastRightOne[0] = RobotMap.LeftEncoder.getRaw();
      RobotMap.lastRightOne[1] = RobotMap.RightEncoder.getRaw();
    }

    if((getIRSensors() & LineDetector.SENSOR_R3) == 1 &&
    (getIRSensors() & LineDetector.SENSOR_R2) == 1) {
      RobotMap.lastRightThree[0] = RobotMap.LeftEncoder.getRaw();
      RobotMap.lastRightThree[1] = RobotMap.RightEncoder.getRaw();
    }  */
  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
