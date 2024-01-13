package frc.robot.subsystems;

import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    private CANSparkMax elevatorBaseMotorOne, elevatorBaseMotorTwo;
    private SparkPIDController pidController;
    private double prevSetpoint;
    private double setPoint;
    private double motorSpeed;
    private boolean brakeMode;
    



    public ElevatorSubsystem() {
        elevatorBaseMotorOne =
            new CANSparkMax(Constants.ELEVATOR_BASE_MOTOR_ONE_CAN_ID, MotorType.kBrushless);
        elevatorBaseMotorTwo =
            new CANSparkMax(Constants.ELEVATOR_BASE_MOTOR_TWO_CAN_ID, MotorType.kBrushless);
        elevatorBaseMotorOne.restoreFactoryDefaults();
        elevatorBaseMotorTwo.restoreFactoryDefaults();
        elevatorBaseMotorTwo.follow(elevatorBaseMotorOne, true);

        pidController = elevatorBaseMotorOne.getPIDController();
        pidController.setFeedbackDevice(elevatorBaseMotorOne.getEncoder());
        pidController.setReference(
            Constants.ELEVATOR_STARTING_POSITION_INCHES, ControlType.kSmartMotion);
        prevSetpoint = Constants.ELEVATOR_STARTING_POSITION_INCHES;
        setPoint = prevSetpoint;
        motorSpeed = .4; 

        pidController.setP(Constants.ELEVATOR_P_VALUE);
        pidController.setI(Constants.ELEVATOR_I_VALUE);
        pidController.setD(Constants.ELEVATOR_D_VALUE);
        pidController.setOutputRange(
            Constants.ELEVATOR_PID_MIN_OUTPUT, Constants.ELEVATOR_PID_MAX_OUTPUT);
        pidController.setIZone(0);
        pidController.setFF(0);
        pidController.setSmartMotionMaxVelocity(Constants.ELEVATOR_MAX_SPEED_RPM, 0);
        pidController.setSmartMotionMaxAccel(Constants.ELEVATOR_MAX_ACCELERATION_RPM2, 0);
        pidController.setSmartMotionAllowedClosedLoopError(0.05, 0);
    }

    public void setBrakeMode() {
      elevatorBaseMotorOne.setIdleMode(IdleMode.kBrake);
      elevatorBaseMotorTwo.setIdleMode(IdleMode.kBrake);
    }
    public void setCoastMode() {
      elevatorBaseMotorOne.setIdleMode(IdleMode.kCoast);
      elevatorBaseMotorTwo.setIdleMode(IdleMode.kCoast);
    }
    public void goUp(double speed) {
      elevatorBaseMotorOne.set(speed);
    }
    public void goDown(double speed) {
      elevatorBaseMotorOne.set(-speed);
    }
    public void off() {
      elevatorBaseMotorOne.stopMotor();
    }
    public void setSetPoint(double point) {
      setPoint = point;
    }
    public boolean isAtSetpoint() {
      boolean temp =
          Math.abs(elevatorBaseMotorOne.getEncoder().getPosition() - setPoint)
              < Constants.ELEVATOR_DEADBAND;
      if (temp) {
        System.out.println("Elevator at setpoint");
      }
      return temp;
    }
    public Double getPosition() {
      return elevatorBaseMotorOne.getEncoder().getPosition();
    }

    @Override
    public void periodic() {
      if (DriverStation.isEnabled() && !brakeMode) {
            brakeMode = true;
            setBrakeMode();
          } else if (DriverStation.isDisabled() && brakeMode) {
            brakeMode = false;
            setCoastMode();
          }
          if (Math.abs(prevSetpoint - setPoint) >= 10e-7) {
            System.out.println("Elevator CHANGING SETPOINT FROM:" + prevSetpoint + "TO: " + setPoint);
            pidController.setReference(setPoint, ControlType.kSmartMotion);
            prevSetpoint = setPoint;
          }
      }
}
