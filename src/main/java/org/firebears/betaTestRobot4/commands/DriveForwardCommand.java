package org.firebears.betaTestRobot4.commands;

import org.firebears.betaTestRobot4.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive forward for 5 seconds.
 */
public class DriveForwardCommand extends Command {

	private final Chassis chassis;

	public DriveForwardCommand(Chassis chassis) {
		this.chassis = chassis;
	}

	@Override
	protected void initialize() {
		setTimeout(5);
	}

	@Override
	protected void execute() {
		chassis.drive(0.5, 0.0);
	}

	@Override
	protected void end() {
		chassis.stop();
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

}
