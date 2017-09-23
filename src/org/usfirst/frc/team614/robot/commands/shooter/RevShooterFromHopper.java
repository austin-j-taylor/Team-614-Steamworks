package org.usfirst.frc.team614.robot.commands.shooter;

import org.usfirst.frc.team614.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RevShooterFromHopper extends Command {

	public RevShooterFromHopper() {
		requires(Robot.shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shooter.talonMaster.changeControlMode(TalonControlMode.Speed);
		Robot.shooter.talonMaster.set(0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.shooter.talonMaster.changeControlMode(TalonControlMode.Speed);
		Robot.shooter.set(SmartDashboard.getNumber(
				"Shooter CAN Talon Setpoint", 0));

		if (this.timeSinceInitialized() > 1.0) {
			Robot.shooter.revFeeder(SmartDashboard.getNumber(
					"Shooter Feeder Speed", 0.0));
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooter.stop();
		Robot.shooter.revFeeder(0);
		// Robot.shooter.setEnabled(false, false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.shooter.stop();
		Robot.shooter.revFeeder(0);
		// Robot.shooter.setEnabled(false, false);
	}
}
