// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private CANSparkMax shooterMotorHigh;
  private CANSparkMax shooterMotorLow;
  private CANSparkMax shooterMotorTilt;
  private GenericEntry speedEntryHigh;
  private GenericEntry speedEntryLow;
  // private GenericEntry speedEntryTilt;
  
  /** Creates a new Shooter. */

 
  public static Double SHOOTER_MOTOR_HIGH_SPEED = 0.50;
  public static Double SHOOTER_MOTOR_LOW_SPEED = 0.50;
  public static Double SHOOTER_MOTOR_TILT_SPEED = 0.50;

  public Shooter() {
    shooterMotorHigh = new CANSparkMax(Constants.SHOOTER_MOTOR_HIGH_CAN_ID, CANSparkMax.MotorType.kBrushless);
    shooterMotorLow = new CANSparkMax(Constants.SHOOTER_MOTOR_LOW_CAN_ID, CANSparkMax.MotorType.kBrushless);
    shooterMotorTilt = new CANSparkMax(Constants.SHOOTER_MOTOR_TILT_CAN_ID, CANSparkMax.MotorType.kBrushless);
    
    
    shooterMotorHigh.setInverted(Constants.SHOOTER_MOTOR_HIGH_INVERTED);
    shooterMotorLow.setInverted(Constants.SHOOTER_MOTOR_LOW_INVERTED);
    shooterMotorTilt.setInverted(Constants.SHOOTER_MOTOR_TILT_INVERTED);
    setUpShuffleboard();
  }

  public void runShooter() {
    shooterMotorHigh.set(SHOOTER_MOTOR_HIGH_SPEED);
    shooterMotorLow.set(SHOOTER_MOTOR_LOW_SPEED);

  }
  public void stopShooter() {
    shooterMotorHigh.set(0);
    shooterMotorLow.set(0);
  }

  public void setUpShuffleboard() {
    ShuffleboardTab shooterMotors;
    shooterMotors = Shuffleboard.getTab("Shooter Motors");
    shooterMotors.addDouble("HighVelocity", () -> shooterMotorHigh.getEncoder().getVelocity());
    shooterMotors.addDouble("LowVelocity", () -> shooterMotorLow.getEncoder().getVelocity());
    shooterMotors.addDouble("TiltVelocity", () -> shooterMotorTilt.getEncoder().getVelocity());
    shooterMotors.addDouble("TiltAngle", () -> shooterMotorTilt.getEncoder().getPosition());
    shooterMotors.addDouble("HighAcceleration", () -> getAcceleration());
    shooterMotors.addDouble("LowAcceleration", () -> getAcceleration());

    speedEntryHigh = shooterMotors.add("MotorHighSpeed", SHOOTER_MOTOR_HIGH_SPEED ).getEntry();
    speedEntryLow = shooterMotors.add("MotorLowSpeed", SHOOTER_MOTOR_LOW_SPEED ).getEntry();
    //speedEntryTilt = shooterMotors.add("MotorTiltSpeed", SHOOTER_MOTOR_TILT_SPEED ).getEntry(); 
  }

  double prev_vel = Double.MIN_VALUE;
  double prev_time = Double.MIN_VALUE;
  private double getAcceleration() {
      double current_vel = shooterMotorHigh.getEncoder().getVelocity();
      double acc = 0;
      if (prev_vel == Double.MIN_VALUE) {
        prev_vel = current_vel;
      } else {
        acc = (current_vel - prev_vel)/(RobotController.getFPGATime()/ 60000000.0 - prev_time);

      } 
      prev_vel = current_vel;
      prev_time = RobotController.getFPGATime() / 60000000.0;
      return acc;

      }
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SHOOTER_MOTOR_HIGH_SPEED = speedEntryHigh.getDouble(0);
    SHOOTER_MOTOR_LOW_SPEED = speedEntryLow.getDouble(0);
    // SHOOTER_MOTOR_TILT_SPEED = speedEntryTilt.getDouble(0);
  }
}
