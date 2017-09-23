package org.usfirst.frc.team614.robot.commands.autonomous;

import org.usfirst.frc.team614.robot.Constants;
import org.usfirst.frc.team614.robot.Robot;
import org.usfirst.frc.team614.robot.commands.drivetrain.DriveForADistance;
import org.usfirst.frc.team614.robot.commands.drivetrain.DriveUntilStopped;
import org.usfirst.frc.team614.robot.commands.drivetrain.RotateToAngle;
import org.usfirst.frc.team614.robot.commands.drivetrain.RotateToVisionTarget;
import org.usfirst.frc.team614.robot.commands.shooter.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BlueKnockHopperAndShoot extends CommandGroup {
    public BlueKnockHopperAndShoot() {
    	double speed = 0.8;
    	
    	addSequential(new DriveForADistance(140 /*distance is in feet?*/, speed));
    	addSequential(new RotateToAngle(-90, true));
    	addSequential(new DriveUntilStopped(speed, 5)); // use this command until exact measurments are made.
    	//addSequential(new RotateToAngle(Robot.navX.getYaw() + SmartDashboard.getNumber("Shooter Camera Angle", 0), false));
    	addSequential(new Shoot(false, true));
    	
    	/*
    	addSequential(new DriveStraightForADistance(-118, -Constants.DRIVETRAIN_AUTONOMOUS_SPEED));
    	addSequential(new RotateToAngle(90, true));
//    	addSequential(new DriveStraightForADistance(62, Constants.DRIVETRAIN_AUTONOMOUS_SPEED));
    	addSequential(new DriveUntilStopped(Constants.DRIVETRAIN_AUTONOMOUS_SPEED));
    	addSequential(new RotateToAngle(15, true));
//    	addSequential(new DriveStraightForADistance(-12, -Constants.DRIVETRAIN_AUTONOMOUS_SPEED));
//    	addSequential(new RotateToVisionTarget(false, true, true));
//    	addSequential(new DriveUntilStopped(Constants.DRIVETRAIN_AUTONOMOUS_SPEED));
    	*/
    }
}
