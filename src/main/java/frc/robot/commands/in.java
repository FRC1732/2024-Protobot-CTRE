// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class in extends Command {
  private final Intake ourIntake;

  /** Creates a new in. 
   * @param thisIntake The intake subsystem this robot will use
  */
  public in(Intake thisIntake) {
    // Use addRequirements() here to declare subsystem dependencies.
    ourIntake = thisIntake;
    addRequirements(thisIntake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ourIntake.takeIN();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ourIntake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
