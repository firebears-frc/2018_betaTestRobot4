package org.firebears.betaTestRobot4.subsystems;

import static org.firebears.betaTestRobot4.RobotConfig.DEBUG;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem {

  private final DifferentialDrive robotDrive;
  private final SpeedController leftMotors;
  private final SpeedController rightMotors;
  double driveSpeed;
  double driveRotation;
  double driveLeft;
  double driveRight;

  public Chassis(SpeedController left, SpeedController right) {
    leftMotors = left;
    rightMotors = right;
    robotDrive = new DifferentialDrive(leftMotors, rightMotors);
    robotDrive.setSubsystem("Chassis");
  }

  @Override
  protected void initDefaultCommand() {}

  @Override
  public void setDefaultCommand(Command command) {
    super.setDefaultCommand(command);
  }

  /**
   * @param speed  forward speed in the range -1.0 to 1.0.
   * @param rotation  rotation speed in the range -1.0 to 1.0.
   */
  public void drive(double speed, double rotation) {
    robotDrive.arcadeDrive(speed, rotation);
    if (DEBUG) {
      SmartDashboard.putNumber("chassis.speed", speed);
      SmartDashboard.putNumber("chassis.rotation", rotation);
      SmartDashboard.putNumber("left.encoder.velocity", ((TalonSRX)leftMotors).getSelectedSensorVelocity(0));
      SmartDashboard.putNumber("right.encoder.velocity", ((TalonSRX)rightMotors).getSelectedSensorVelocity(0));
    }
    driveSpeed = speed;
    driveRotation = rotation;
    driveLeft = leftMotors.get();
    driveRight = rightMotors.get();
  }
  
  public void tankDrive(double leftSpeed, double rightSpeed) {
//    robotDrive.tankDrive(leftSpeed, rightSpeed);
    leftMotors.set(leftSpeed);
    rightMotors.set(rightSpeed);
  }

  public void stop() {
    leftMotors.stopMotor();
    rightMotors.stopMotor();
  }
  
  /** @return the most recent speed value read from the joystick, in the range -1.0 to 1.0. */
  public double getDriveSpeed() {
    return driveSpeed;
  }
  
  /** @return the most recent rotation value read from the joystick, in the range -1.0 to 1.0. */
  public double getDriveRotation() {
    return driveRotation;
  }
  
  /** @return the most recent speed of the left motors, in the range -1.0 to 1.0. */
  public double getDriveLeft() {
    return driveLeft;
  }
  
  /** @return the most recent speed of the right motors, in the range -1.0 to 1.0. */
  public double getDriveRight() {
    return driveRight;
  }
}
