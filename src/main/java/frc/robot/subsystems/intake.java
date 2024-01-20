package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.hal.CANAPITypes.CANDeviceType;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake{
    private CANSparkMax intakeLCanSparkMax;
    private CANSparkMax intakeRCanSparkMax;
    private ShuffleboardTab intakeMotors;
    private double motorSetpt;
    private double motor2Setpt;
    private GenericEntry speedEntryIntakeL;
    private GenericEntry speedEntryIntakeR;

    public static Double intakeMotorsLSpeed = 0.50;
    public static Double intakeMotorRSpeed = 0.50;

    
    public intake() {
        intakeLCanSparkMax = new CANSparkMax(Constants.INTAKE_MOTOR_CAN_ID,  CANSparkMax.MotorType.kBrushless);
        intakeRCanSparkMax = new CANSparkMax(Constants.INTAKE_SECOND_MOTOR_CAN_ID, CANSparkMax.MotorType.kBrushless);

        intakeLCanSparkMax.setInverted(Constants.INTAKE_MOTOR_L_INVERTED);
        intakeRCanSparkMax.setInverted(Constants.INTAKE_MOTOR_R_INVERTED);

        setUpShuffleboard();
        //establishes motors, sets setpoints to 0 sets up shuffleboard
    }

    
    public void periodic(){
        //method called once per scheduler run
    }

    public void takeIN() {
        intakeLCanSparkMax.set(-0.5);
        intakeRCanSparkMax.set(-0.5);
    }

    public void spitOUT() {
        intakeLCanSparkMax.set(0.5);
        intakeRCanSparkMax.set(0.5);
    }

    public void stop() {
        intakeLCanSparkMax.set(0);
        intakeRCanSparkMax.set(0);
    }


    public void setUpShuffleboard() {
        ShuffleboardTab intakeMotors;
        intakeMotors = Shuffleboard.getTab("Intake Motors");
        intakeMotors.addBoolean("Inverted", () -> intakeLCanSparkMax.getInverted());
        intakeMotors.addBoolean("Inverted", () -> intakeRCanSparkMax.getInverted());
        intakeMotors.addDouble("VelocityL", () -> intakeLCanSparkMax.getEncoder().getVelocity());
        intakeMotors.addDouble("VelocityR", () -> intakeRCanSparkMax.getEncoder().getVelocity());
        intakeMotors.addDouble("AccelerationL",()-> getAcceleration());
        intakeMotors.addDouble("AccelerationR", ()-> getAcceleration());


        speedEntryIntakeL = intakeMotors.add("MotorHighSpeed", intakeMotorsLSpeed).getEntry();
        speedEntryIntakeR = intakeMotors.add("MotorHighSpeed", intakeMotorRSpeed).getEntry();

    }

        double vel = Double.MIN_VALUE;
        double prev_time = Double.MIN_VALUE;
        private double getAcceleration();
        double current_vel = shooterMotorHigh.getEncoder().getVelocity();
        double acc = 0;
        if(prev vel == Double.MIN_VALUE){
            prev_vel=current_vel;
        }
        else {
            acc = (current_vel-prev_vel)/(RobotController.getFGPATime()/60000000.0 - prev_time);   
        }
        
        prev_vel = current_vel;
        prev_time = RobotController.getFPGATime()/60000000.0;
        return acc;
    
}