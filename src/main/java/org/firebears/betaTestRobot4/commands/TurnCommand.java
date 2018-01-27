package org.firebears.betaTestRobot4.commands;

import org.firebears.betaTestRobot4.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class TurnCommand extends Command {

  private final Chassis chassis;
  private final boolean turnLeft;

  public TurnCommand(Chassis chassis, boolean turnLeft) {
    this.chassis = chassis;
    this.turnLeft = turnLeft;
  }

  @Override
  protected void initialize() {
    setTimeout(2);
  }

  @Override
  protected void execute() {
    double rotationSpeed = turnLeft ? -0.5 : 0.5;
    chassis.drive(0.0, rotationSpeed);
  }

  @Override
  protected void end() {
    chassis.stop();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
  
  @Override
  public String toString() {
    return "TurnCommand(" + turnLeft + ")";
  }
}
