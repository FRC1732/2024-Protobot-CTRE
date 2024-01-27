// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class RGBStatusSubsystem extends SubsystemBase {
  /*-
   * Outputs 0 and 1 set the scoring height.
   *   Low Score - Bit 0
   *   Mid Score - Bit 1
   *   High Score - Bits 0 and 1
   *
   * Outputs 2 amd 3 set the game piece
   *   Cone - Bit 3
   *   Cube - Bit 2
   *
   * Output 4 is the modifer for every other mode we want to represent
   *   Capture Game Piece - Bit 4 and 0
   *
   */

  private DigitalOutput out0 = new DigitalOutput(1);
  private DigitalOutput out1 = new DigitalOutput(2);
  private DigitalOutput out4 = new DigitalOutput(5);

  private ScoreHeight scoreHeight;
  private Timer timer;
  private SpecialMode specialMode;
  private double targetElapsedTimeSeconds;

  private RobotContainer robotContainer;

  /** Creates a new RGBStatus. */
  public RGBStatusSubsystem(RobotContainer robotContainer) {
    this.robotContainer = robotContainer;
    scoreHeight = ScoreHeight.NONE;
    specialMode = SpecialMode.NONE;
    targetElapsedTimeSeconds = 0;
    timer = new Timer();
  }

  @Override
  public void periodic() {

  

    // Invert the digital sigs; HIGH is 0, LOW is 1
    if (specialMode != SpecialMode.NONE) {
      if (timer.hasElapsed(targetElapsedTimeSeconds)) {
        specialMode = SpecialMode.NONE;
        timer.stop();
      } else {
        switch (specialMode) {
          case GAME_PIECE_CAPTURED:
            // bits 4 and 0 -- 17
            out4.set(!true);
            out0.set(!true);

            out1.set(!false);
            break;

          case NONE:
          default:
            break;
        }
      }
    }

    if (specialMode == SpecialMode.NONE) {
      out4.set(!false);

      setScoreHieghtBits();
      
    }
  }

  
  private void setScoreHieghtBits() {
    switch (scoreHeight) {
      case HIGH: // 3
        out0.set(!true);
        out1.set(!true);
        break;

      case LOW: // 1
        out0.set(!true);
        out1.set(!false);
        break;

      case MEDIUM: // 2
        out0.set(!false);
        out1.set(!true);
        break;

      case NONE:
      default:
        out0.set(!false);
        out1.set(!false);
        break;
    }
  }

  public void capturedGamePiece() {
    timer.reset();
    timer.start();
    targetElapsedTimeSeconds = 1.5;
    specialMode = SpecialMode.GAME_PIECE_CAPTURED;
    System.out.println("RGB - Capture Game Piece");
  }

  public void off() {
    scoreHeight = ScoreHeight.NONE;
    specialMode = SpecialMode.NONE;
    timer.stop();
  }


  public void scoringPosition(ScoreHeight scoreColors) {
    this.scoreHeight = scoreColors;
  }

  public enum ScoreHeight {
    HIGH,
    MEDIUM,
    LOW,
    NONE;
  }

  protected enum SpecialMode {
    NONE,
    GAME_PIECE_CAPTURED;
  }
}