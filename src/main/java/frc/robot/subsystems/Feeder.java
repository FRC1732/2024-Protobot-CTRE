// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
  /** Creates a new Feeder. */
private CANSparkMax feederMotorLeft;
private CANSparkMax feederMotorRight;
public static Double FEEDER_MOTOR_LEFT_SPEED = 0.50;
public static Double FEEDER_MOTOR_RIGHT_SPEED = -0.50;
  public Feeder() {
    feederMotorLeft = new CANSparkMax(Constants.FEEDER_MOTOR_LEFT_CAN_ID, CANSparkMax.MotorType.kBrushless);
    feederMotorRight = new CANSparkMax(Constants.FEEDER_MOTOR_RIGHT_CAN_ID, CANSparkMax.MotorType.kBrushless);

    feederMotorLeft.setInverted(Constants.SHOOTER_MOTOR_LEFT_INVERTED);
    feederMotorRight.setInverted(Constants.SHOOTER_MOTOR_RIGHT_INVERTED);
  }

  public void runFeederIn() {
    feederMotorLeft.set(FEEDER_MOTOR_LEFT_SPEED);
    feederMotorRight.set(FEEDER_MOTOR_RIGHT_SPEED);

  }

  public void stopFeederIn() {
    feederMotorLeft.set(0);
    feederMotorRight.set(0);

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}