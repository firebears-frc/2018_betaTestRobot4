package org.firebears.betaTestRobot4;

import java.io.File;
import org.firebears.betaTestRobot4.commands.DriveCommand;
import org.firebears.betaTestRobot4.commands.PlayRecordingCommand;
import org.firebears.betaTestRobot4.commands.RightSwitchPriorityCommand;
import org.firebears.betaTestRobot4.commands.StartMotionRecordCommand;
import org.firebears.betaTestRobot4.commands.StopMotionRecordCommand;
import org.firebears.betaTestRobot4.commands.TurnCommand;
import org.firebears.betaTestRobot4.subsystems.Chassis;
import org.firebears.util.RobotReport;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Configures all components required by subsystems.
 */
public class RobotConfig {

  public static final boolean DEBUG = true;

  public static final int CAN_FRONT_LEFT = 2;
  public static final int CAN_REAR_LEFT = 3;
  public static final int CAN_FRONT_RIGHT = 4;
  public static final int CAN_REAR_RIGHT = 5;
  public static final boolean CHASSIS_BRAKE_MODE = true;

  static final double PID_P = 3.0f;
  static final double PID_I = 0;
  static final double PID_D = 0;
  static final double PID_FF = 0;
  static final int PID_IZONE = 256;
  static final double PID_RAMPRATE = 1;
  static final int PID_PROFILE = 0;
  static final int ENCODER_COUNTS_PER_REV = 255;
  static final int PID_IDX = 0;
  static final int TIMEOUT_MS = 10;

  static final boolean CLOSED_LOOP_DRIVING = false;

  public final WPI_TalonSRX frontLeftMotor;
  public final WPI_TalonSRX rearLeftMotor;
  public final WPI_TalonSRX frontRightMotor;
  public final WPI_TalonSRX rearRightMotor;

  public final Joystick joystick;
  public final JoystickButton trigger;

  public static Chassis chassis;

  public RobotReport report;

  /**
   * Construct RobotConfig. Create all low-level components to support the robot.
   */
  public RobotConfig() {
    report = new RobotReport("betaTestRobot4");
    report.setDescription("Command-based robot example, with dependency injection...");

    frontLeftMotor = new WPI_TalonSRX(CAN_FRONT_LEFT);
    frontLeftMotor.setName("Chassis", "frontLeft");
    frontLeftMotor.setNeutralMode(CHASSIS_BRAKE_MODE ? NeutralMode.Brake : NeutralMode.Coast);
    report.addCAN(CAN_FRONT_LEFT, "frontLeft", frontLeftMotor);

    frontRightMotor = new WPI_TalonSRX(CAN_FRONT_RIGHT);
    frontRightMotor.setName("Chassis", "frontRight");
    frontRightMotor.setNeutralMode(CHASSIS_BRAKE_MODE ? NeutralMode.Brake : NeutralMode.Coast);
    report.addCAN(CAN_FRONT_RIGHT, "frontRight", frontRightMotor);

    rearLeftMotor = new WPI_TalonSRX(CAN_REAR_LEFT);
    rearLeftMotor.setName("Chassis", "rearLeft");
    rearLeftMotor.setNeutralMode(CHASSIS_BRAKE_MODE ? NeutralMode.Brake : NeutralMode.Coast);
    report.addCAN(CAN_REAR_LEFT, "frontRight", rearLeftMotor);

    rearRightMotor = new WPI_TalonSRX(CAN_REAR_RIGHT);
    rearRightMotor.setName("Chassis", "rearRight");
    rearRightMotor.setNeutralMode(CHASSIS_BRAKE_MODE ? NeutralMode.Brake : NeutralMode.Coast);
    report.addCAN(CAN_REAR_RIGHT, "rearRight", rearRightMotor);

    if (CLOSED_LOOP_DRIVING) {

      frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
      frontLeftMotor.setSensorPhase(false);
      setPID(frontLeftMotor, PID_P, PID_I, PID_D, PID_FF, PID_IZONE, PID_RAMPRATE, PID_PROFILE);

      frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
      frontRightMotor.setSensorPhase(false);
      setPID(frontRightMotor, PID_P, PID_I, PID_D, PID_FF, PID_IZONE, PID_RAMPRATE, PID_PROFILE);

      rearLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
      rearLeftMotor.setSensorPhase(false);
      setPID(rearLeftMotor, PID_P, PID_I, PID_D, PID_FF, PID_IZONE, PID_RAMPRATE, PID_PROFILE);

      rearRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
      rearRightMotor.setSensorPhase(false);
      setPID(rearRightMotor, PID_P, PID_I, PID_D, PID_FF, PID_IZONE, PID_RAMPRATE, PID_PROFILE);
    }

    joystick = new Joystick(0);
    report.addJoystick(0, "joystick", joystick);
    trigger = new JoystickButton(joystick, 1);
  }

  /**
   * Perform initializations of high-level components.
   */
  public void init() {
    initializeSubsystems();
    initializeOperatorInterface();

    chassis.setDefaultCommand(new DriveCommand(chassis, joystick));

    report.write(new File(System.getProperty("user.home"), "robotReport.md"));
  }

  /**
   * Initialize high-level robot subsystems.
   */
  protected void initializeSubsystems() {
    chassis = new Chassis(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
  }

  /**
   * Initializes high-level components and attaches new Commands to Joystick buttons and Triggers.
   * Also may put Commands on the SmartDashboard.
   */
  protected void initializeOperatorInterface() {
    trigger.whenPressed(new TurnCommand(chassis, true));
    if (DEBUG) {
      SmartDashboard.putData("Recording Start", new StartMotionRecordCommand(chassis));
      SmartDashboard.putData("Recording Stop", new StopMotionRecordCommand());
      SmartDashboard.putData("Recording Play", new PlayRecordingCommand(chassis));
    }
  }

  /**
   * Determine the current autonomous Command.
   * 
   * @return Command for autonomous mode, or {@code null}.
   */
  public Command getAutonomousCommand() {
    return new RightSwitchPriorityCommand();
  }

  private static void setPID(TalonSRX talonSRX, double pidP, double pidI, double pidD, double pidF,
      int pidIZone, double pidRampRate, int slotIdx) {
    talonSRX.configClosedloopRamp(pidRampRate, TIMEOUT_MS);
    talonSRX.configNominalOutputForward(0.0, TIMEOUT_MS);
    talonSRX.configNominalOutputReverse(0.0, TIMEOUT_MS);
    talonSRX.configPeakOutputForward(1.0, TIMEOUT_MS);
    talonSRX.configPeakOutputReverse(-1.0, TIMEOUT_MS);
    talonSRX.config_kP(slotIdx, pidP, TIMEOUT_MS);
    talonSRX.config_kI(slotIdx, pidI, TIMEOUT_MS);
    talonSRX.config_kD(slotIdx, pidD, TIMEOUT_MS);
    talonSRX.config_kF(slotIdx, pidF, TIMEOUT_MS);
    talonSRX.config_IntegralZone(slotIdx, pidIZone, TIMEOUT_MS);
    talonSRX.selectProfileSlot(slotIdx, PID_IDX);
  }

}
