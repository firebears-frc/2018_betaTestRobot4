package org.firebears.betaTestRobot4.commands;

import edu.wpi.first.wpilibj.command.Command;
import java.io.*;
import java.util.Scanner;
import org.firebears.betaTestRobot4.subsystems.Chassis;

/**
 * Play a previously recorded command.
 */
public class PlayRecordingCommand extends Command {
  InputStream stream;
  Scanner scanner;
  long time;
  long startTime;
  double forwardAmount;
  double rotateAmount;
  boolean hasMore;
  final String fileName;
  private final Chassis chassis;

  public PlayRecordingCommand(Chassis chassis) {
    this("/tmp/Recording.csv", chassis);
  }

  public PlayRecordingCommand(String name, Chassis chassis) {
    requires(chassis);
    fileName = name;
    this.chassis = chassis;
  }

  public boolean readLine() {
    if (!scanner.hasNext()) {
      return false;
    }
    time = scanner.nextLong();
    forwardAmount = scanner.nextDouble();
    rotateAmount = scanner.nextDouble();
    scanner.nextLine();
    return true;

  }

  protected void initialize() {
    try {
      startTime = System.currentTimeMillis();
      if (fileName.startsWith("/tmp/") || fileName.startsWith("/home/lvuser/")
          || fileName.startsWith("/U/")) {
        File f = new File(fileName);
        stream = new FileInputStream(f);
      } else {
        stream = ClassLoader.getSystemResourceAsStream(fileName);
      }
      scanner = (new Scanner(stream)).useDelimiter(",");
      hasMore = readLine();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }

  protected void execute() {
    long currentTime = System.currentTimeMillis() - startTime;
    while ((currentTime > time) && (hasMore)) {
      chassis.drive(rotateAmount, forwardAmount);
      hasMore = readLine();
    }
  }

  protected boolean isFinished() {
    return !hasMore;
  }

  protected void end() {
    scanner.close();
  }

  protected void interrupted() {
    scanner.close();
  }
}
