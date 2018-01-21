package org.firebears.betaTestRobot4.commands;

import org.firebears.betaTestRobot4.subsystems.Chassis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {

  private final Chassis chassis;
  private final Joystick joystick;

  public DriveCommand(Chassis chassis, Joystick joystick) {
    this.chassis = chassis;
    this.joystick = joystick;
    requires(this.chassis);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  protected void execute() {
    double speed = joystick.getY();
    double rotation = joystick.getX();
    chassis.drive(speed, rotation);
  }
}
