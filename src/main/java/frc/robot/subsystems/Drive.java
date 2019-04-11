package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import java.util.stream.IntStream;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.*;

public class Drive extends Subsystem {
    // Declaring Drive Masters and Slaves
    private final WPI_TalonSRX leftDriveMaster;
    private final WPI_TalonSRX leftDriveSlave;
    private final WPI_TalonSRX rightDriveMaster;
    private final WPI_TalonSRX rightDriveSlave;
    // PID Numbers
    double LeftCurrentEncoderInput = 0.0;
    double RightCurrentEncoderInput = 0.0;
    double RightEncoderTarget = 0.0;
    double LeftEncoderTarget = 0.0;
    // Right PID
    double RightP = 0.04;
    double RightI = 0.0;
    double RightD = 0.0;
    // Left PID
    double LeftP = 0.04;
    double LeftI = 0.0;
    double LeftD = 0.0;
    // PID Variables
    double RightSum = 0.0;
    double LeftSum = 0.0;
    double RightPreviousEncoderInput = 0.0;
    double LeftPreviousEncoderInput = 0.0;

    public double LeftError = 0.0;
    public double RightError = 0.0;

    double RightDelta = 0.0;
    double LeftDelta = 0.0;
    double LeftOutput = 0.0;
    double RightOutput = 0.0;
    double RightOldVel = 0.0;
    double RightOldPos = 0.0;
    double RightCurrentVel = 0.0;
    double RightCurrentAccel = 0.0;
    double LeftOldVel = 0.0;
    double LeftOldPos = 0.0;
    double LeftCurrentVel = 0.0;
    double LeftCurrentAccel = 0.0;
    double AGain = 0.0;
    double ALimit = 18.0;
    double unlimitedAccel = 0.0;
    double MaxOutput = 0.3;
    double SumLimit = 25.0;

    /**
     * Drive constructor
     * 
     * @param l1 left front motor
     * @param l2 left back motor
     * @param r1 right front motor
     * @param r2 right back motor
     */
    
    public Drive(WPI_TalonSRX l1, WPI_TalonSRX l2, WPI_TalonSRX r1, WPI_TalonSRX r2) {
        //TODO Navneeth: Implement Distance per pulse, later

        leftDriveMaster = l1;
        leftDriveSlave = l2;

        leftDriveSlave.set(ControlMode.Follower, leftDriveMaster.getDeviceID());

        rightDriveMaster = r1;
        rightDriveSlave = r2;

        rightDriveSlave.set(ControlMode.Follower, rightDriveMaster.getDeviceID());

        leftDriveMaster.configContinuousCurrentLimit(32, 0);
        leftDriveMaster.configPeakCurrentLimit(35, 0);
        leftDriveMaster.configPeakCurrentDuration(80, 0);
        leftDriveMaster.enableCurrentLimit(true);

        leftDriveSlave.configContinuousCurrentLimit(32, 0);
        leftDriveSlave.configPeakCurrentLimit(35, 0);
        leftDriveSlave.configPeakCurrentDuration(80, 0);
        leftDriveSlave.enableCurrentLimit(true);

        rightDriveMaster.configContinuousCurrentLimit(32, 0);
        rightDriveMaster.configPeakCurrentLimit(35, 0);
        rightDriveMaster.configPeakCurrentDuration(80, 0);
        rightDriveMaster.enableCurrentLimit(true);

        rightDriveSlave.configContinuousCurrentLimit(32, 0);
        rightDriveSlave.configPeakCurrentLimit(35, 0);
        rightDriveSlave.configPeakCurrentDuration(80, 0);
        rightDriveSlave.enableCurrentLimit(true);
    }

    /**
     * Sets TankDrive to default command
     */
    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

    /**
     * Gets Right Encoder
     */
    public int getRightEncoder() {
        return RobotMap.rightEncoder.getRaw();
    }

    /**
     * Gets Left Encoder
     */
    public int getLeftEncoder() {
        return RobotMap.leftEncoder.getRaw();
    }

    /**
     * Gets the average value of the right and left encoders
     */
    public int getAverageEncoder() {
        return (getRightEncoder() + getLeftEncoder()) / 2;
    }

    /**
     * Finds highest value of encoders
     * 
     * @return Highest value of the two
     */
    public int getMaxEncoder() {
        return Math.max(getRightEncoder(), getLeftEncoder());
    }

    /**
     * Finds lowest value of encoders
     * 
     * @return Lowest value of the two
     */
    public int getMinEncoder() {
        return Math.min(getRightEncoder(), getLeftEncoder());
    }

    /**
     * Resets left encoder
     */
    public void resetLeftEncoder() {
        RobotMap.leftEncoder.reset();
    }

    /**
     * Resets right Encoder
     */
    public void resetRightEncoder() {
        RobotMap.rightEncoder.reset();
    }

    /**
     * Resets all encoders
     */
    public void resetAllEncoders() {
        resetLeftEncoder();
        resetRightEncoder();
    }

    /**
     * Sets speed for the left side
     * 
     * @param speed
     */
    public void setLeftSpeed(double speed) {
        leftDriveMaster.set(-speed);
    }

    /**
     * Sets speed for right side
     * 
     * @param speed
     */
    public void setRightSpeed(double speed) {
        rightDriveMaster.set(speed);
    }

    /**
     * Sets speed for both sides
     */
    public void setAllSpeed(double leftSpeed, double rightSpeed) {
        setLeftSpeed(leftSpeed);
        setRightSpeed(rightSpeed);
    }

    /**
     * Stops all the speed
     */
    public void stopAllSpeed() {
        setAllSpeed(0, 0);
    }

    /**
     * Sets the position of the robot in encoder ticks
     * 
     * @param ticks int the desired position of the robot in encoder ticks
     */
    public void setLeftPosition(int ticks) {
        // Gets the current Left encoder ticks.
        LeftCurrentEncoderInput = RobotMap.leftEncoder.getRaw();
        LeftEncoderTarget = RobotMap.LeftEncoderTarget;
        // Resign all the left variables.
        LeftCurrentVel = LeftCurrentEncoderInput - LeftPreviousEncoderInput;
        LeftCurrentAccel = LeftCurrentVel - LeftOldVel;
        LeftError = LeftEncoderTarget - LeftCurrentEncoderInput;
        LeftSum = LeftSum + LeftError;
        // IntgoreSum limiter sum.
        if (LeftSum > SumLimit) {
            LeftSum = SumLimit;
        }
        if (LeftSum < (-SumLimit)) {
            LeftSum = (-SumLimit);
        }
        // PID equations.
        LeftOutput = ((-LeftP) * LeftError) + (LeftSum * LeftI) + (LeftD * LeftDelta) + (LeftCurrentAccel * AGain);
        // Left motor speed limiter.
        if (LeftOutput > MaxOutput) {
            LeftOutput = MaxOutput;
        }
        else if (LeftOutput < (-MaxOutput)) {
            LeftOutput = (-MaxOutput);
        }
        // Set the motor speed.
        RobotMap.leftFrontMotor.set(-LeftOutput);
        RobotMap.leftBackMotor.set(-LeftOutput);
        // Reassign some more left variables.
        LeftPreviousEncoderInput = LeftCurrentEncoderInput;
        LeftOldVel = LeftCurrentVel;
    }

    /**
     * Sets the position fo the right side of the robot
     * @param ticks int the position that you want to set the right side of the robot to 
     */
    public void setRightPosition(int ticks){
        //Gets the currnet Right encoder ticks.
        RightCurrentEncoderInput = RobotMap.rightEncoder.getRaw();
        RightEncoderTarget = RobotMap.RightEncoderTarget;
        // Resign all the right variables.
        RightCurrentVel = RightCurrentEncoderInput - RightPreviousEncoderInput;
        RightCurrentAccel = RightCurrentVel - RightOldVel;
        RightError = RightEncoderTarget - RightCurrentEncoderInput;
        // Sum limiter sum.
        RightSum = RightSum + RightError;
        if (RightSum > SumLimit) {
            RightSum = SumLimit;
        }
        if (RightSum < (-SumLimit)) {
            RightSum = -SumLimit;
        }
        // PID equations.
        RightOutput = ((-RightP) * RightError) + (RightSum * RightI) + (RightD * RightDelta) + (RightCurrentAccel * AGain);
        // Right motor speed limiter.
        if (RightOutput > MaxOutput) {
            RightOutput = MaxOutput;
        }
        if (RightOutput < (-MaxOutput)) {
            RightOutput = -MaxOutput;
        }
        // Set the motor speed.
        RobotMap.rightFrontMotor.set(RightOutput);
        RobotMap.rightBackMotor.set(RightOutput);
        // Resign some more right variables.
        RightPreviousEncoderInput = RightCurrentEncoderInput;
        RightOldVel = RightCurrentVel;
    }

    /**
     * Sets the position of both sides of the robo
     * @param leftTicks int the position that you want to set the left side of the robot to
     * @param rightTicks int the position that you want to set the right side of the robot to 
     */
    public void setPosition(int leftTicks, int rightTicks){
        setLeftPosition(leftTicks);
        setRightPosition(rightTicks);
    }
    
    int [] MotorCurrentArray = new int [20];
    int Counter = 0;
    public boolean isStuck(){
        Counter ++;

        MotorCurrentArray [Counter%MotorCurrentArray.length%20] = (int) LeftCurrentVel;
        RobotMap.MotorCurrentSum = IntStream.of(MotorCurrentArray).sum();
        if(RobotMap.MotorCurrentSum >= 20){
            return true;
        }
        else{
            return false;
        }
    }
}
