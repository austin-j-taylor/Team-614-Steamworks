package org.usfirst.frc.team614.robot.commands.drivetrain;

import org.usfirst.frc.team614.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Makes the the robot drive forward until the robot is not moving (nearly 0 velocity)
 */

//Might be necessary to include specific tolerances (because "a" rarely exactly equals 0)
//However isMoving seems to include thresholds of its own, not sure if they can be modified
public class DriveUntilStopped extends Command {
	private double speed;
    public DriveUntilStopped(double speed, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	this.speed = speed;
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot.navX.reset();
//    	Robot.navX.zeroYaw();
    	
    	Robot.drivetrain.getTurnController().enable();
    	Robot.drivetrain.setUsingTurnPID(true);
    	

        Robot.drivetrain.getTurnController().setSetpoint(Robot.navX.getYaw());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.arcadeDrive(speed, Robot.drivetrain.getPIDRotateRate());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (!Robot.navX.isMoving() && this.timeSinceInitialized() > .2)
    	{
    		return true;
    	}
    	else
    		return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.setUsingTurnPID(false);
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.setUsingTurnPID(false);
    	Robot.drivetrain.stop();
    }
}
