package org.usfirst.frc.team614.robot.commands.shooter;

import org.team708.robot.util.Gamepad;
import org.usfirst.frc.team614.robot.Constants;
import org.usfirst.frc.team614.robot.OI;
import org.usfirst.frc.team614.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RevShooterFeeder extends Command {

    public RevShooterFeeder() {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot.gearFeeder.set(OI.driverGamepad.getAxis(Gamepad.leftStick_Y);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.revFeeder(SmartDashboard.getNumber("Shooter Feeder Speed", 0.0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.revFeeder(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.revFeeder(0);
    }
}
