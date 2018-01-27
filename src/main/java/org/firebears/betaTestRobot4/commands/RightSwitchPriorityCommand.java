package org.firebears.betaTestRobot4.commands;

import org.firebears.betaTestRobot4.RobotConfig;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 * Autonomous command for scoring on the switch from the right side of the field.
 */
public class RightSwitchPriorityCommand extends AbstractAutoCommand {

  /**
   * @return the best command for handling the game data.
   */
  protected Command pickAutoCommand(String data) {
    if (data.startsWith("L")) {
      return new TurnCommand(RobotConfig.chassis, true);
    } else if (data.startsWith("R")) {
      return new TurnCommand(RobotConfig.chassis, false);
    } else {
      return null;
    }
  }

  /**
   * @return a command to start if we never get game data.
   */
  protected Command getTimeoutCommand() {
    return new PrintCommand("DriveStraightCommand");
  }

}
