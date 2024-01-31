// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import pabeles.concurrency.IntOperatorTask.Max;
import pabeles.concurrency.IntOperatorTask.Min;

public class Feeder extends SubsystemBase {
  /** Creates a new Feeder. */
private CANSparkMax feederMotorLeft;
private CANSparkMax feederMotorRight;
public static Double FEEDER_MOTOR_LEFT_SPEED = .3;
public static Double FEEDER_MOTOR_RIGHT_SPEED = .3;
private GenericEntry feederLEntry;
private GenericEntry feederREntry;

  public Feeder() {
    feederMotorLeft = new CANSparkMax(Constants.FEEDER_MOTOR_LEFT_CAN_ID, CANSparkMax.MotorType.kBrushed);
    feederMotorRight = new CANSparkMax(Constants.FEEDER_MOTOR_RIGHT_CAN_ID, CANSparkMax.MotorType.kBrushed);

    feederMotorLeft.setInverted(Constants.SHOOTER_MOTOR_LEFT_INVERTED);
    feederMotorRight.setInverted(Constants.SHOOTER_MOTOR_RIGHT_INVERTED);
    setUpShuffleboard();

  }

  public void runFeederIn() {
    feederMotorLeft.set(FEEDER_MOTOR_LEFT_SPEED);
    feederMotorRight.set(FEEDER_MOTOR_RIGHT_SPEED);

  }

  public void stopFeederIn() {
    feederMotorLeft.set(0);
    feederMotorRight.set(0);
  }

  public void setUpShuffleboard() {
    ShuffleboardTab feederMotors;
    /*feederMotors = Shuffleboard.getTab("Feeder Motors");
    feederMotors.addDouble("LeftMotor_Velocity", () -> feederMotorLeft.getEncoder().getVelocity());
    feederMotors.addDouble("RightMotor_Velocity", () -> feederMotorRight.getEncoder().getVelocity());
    feederMotors.addDouble("LeftMotor_Position", () -> feederMotorLeft.getEncoder().getPosition());
    feederMotors.addDouble("RightMotor_Position", () -> feederMotorRight.getEncoder().getPosition());
    feederLEntry = feederMotors.add("Left Feeder Speed", FEEDER_MOTOR_LEFT_SPEED).getEntry();
    feederREntry = feederMotors.add("Right_Feeder_Speed", FEEDER_MOTOR_RIGHT_SPEED).getEntry();
    */
  
    //feederMotors.addDouble("LeftMotor_Acc", () ->getAcceleration());
  }

  

  @Override
  public void periodic() {
    //FEEDER_MOTOR_LEFT_SPEED = feederLEntry.getDouble(0);
    //FEEDER_MOTOR_RIGHT_SPEED = feederREntry.getDouble(0);
    // This method will be called once per scheduler run
  }
}