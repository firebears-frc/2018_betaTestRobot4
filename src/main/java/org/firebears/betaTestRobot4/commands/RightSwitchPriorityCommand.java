package org.firebears.betaTestRobot4.commands;

import org.firebears.betaTestRobot4.RobotConfig;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PrintCommand;

public class RightSwitchPriorityCommand extends AbstractAutoCommand {

  protected Command pickAutoCommand(String data) {
    if (data.startsWith("L")) {
      return new TurnCommand(RobotConfig.chassis, true);
    } else if (data.startsWith("R")) {
      return new TurnCommand(RobotConfig.chassis, false);
    } else {
      return null;
    }
  }

  protected Command getTimeoutCommand() {
    return new PrintCommand("DriveStraightCommand");
  }

}
