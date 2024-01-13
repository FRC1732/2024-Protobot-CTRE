package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorSetPoint extends Command {
    private double setPoint;
    private ElevatorSubsystem elevator;

    public ElevatorSetPoint(double setPoint) {
        this.setPoint = setPoint;
        elevator = new ElevatorSubsystem();
    }
    public void initialize() {
        
    } 
    public void execute() {
        if (elevator.getPosition() > setPoint) {
            elevator.goUp(Constants.ELEVATOR_SPEED);
        } else if (elevator.getPosition() < setPoint) {
            elevator.goDown(Constants.ELEVATOR_SPEED);
        } else {
            end(false);
        }
    }
    public void end(boolean interrupted) {
        elevator.off();
    }
    public boolean isFinished() {
        if (elevator.isAtSetpoint()) {
            return true;
        }
        return false;
    }
}
