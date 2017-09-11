package org.usfirst.frc.team614.robot.commands.autonomous;

import org.usfirst.frc.team614.robot.Constants;
import org.usfirst.frc.team614.robot.commands.drivetrain.DriveForADistance;
import org.usfirst.frc.team614.robot.commands.drivetrain.DriveUntilStopped;
import org.usfirst.frc.team614.robot.commands.drivetrain.RotateToAngle;
import org.usfirst.frc.team614.robot.commands.drivetrain.RotateToVisionTarget;
import org.usfirst.frc.team614.robot.commands.shooter.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RedKnockHopperAndShoot extends CommandGroup {

	public RedKnockHopperAndShoot() {
		double speed = 0.8;

		// drives to hopper
		addSequential(new DriveForADistance(8 /* distance is in feet */, speed));
		addSequential(new RotateToAngle(90, true));
		// run into hopper
		addSequential(new DriveUntilStopped(speed, 5)); // use this command until exact measurements are made.
    	
		addParallel(new Shoot(false, false, false, false, false));

		/*
		 * addSequential(new DriveStraightForADistance(-118,
		 * -Constants.DRIVETRAIN_AUTONOMOUS_SPEED)); addSequential(new
		 * RotateToAngle(90, true)); // addSequential(new
		 * DriveStraightForADistance(62,
		 * Constants.DRIVETRAIN_AUTONOMOUS_SPEED)); addSequential(new
		 * DriveUntilStopped(Constants.DRIVETRAIN_AUTONOMOUS_SPEED));
		 * addSequential(new RotateToAngle(15, true)); // addSequential(new
		 * DriveStraightForADistance(-12,
		 * -Constants.DRIVETRAIN_AUTONOMOUS_SPEED)); // addSequential(new
		 * RotateToVisionTarget(false, true, true)); // addSequential(new
		 * DriveUntilStopped(Constants.DRIVETRAIN_AUTONOMOUS_SPEED));
		 */
	}
}
