package org.firebears.betaTestRobot4;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	RobotConfig robotConfig = null;
	Command autonomousCommand = null;

	@Override
	public void robotInit() {
		robotConfig = new RobotConfig();
		robotConfig.init();
	}

	@Override
	public void autonomousInit() {
		autonomousCommand = robotConfig.getAutonomousCommand();
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
			autonomousCommand = null;
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.updateValues();
	}
}
