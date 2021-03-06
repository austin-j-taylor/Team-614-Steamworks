package org.usfirst.frc.team614.robot.commands.shooter;

import org.usfirst.frc.team614.robot.Robot;
import org.usfirst.frc.team614.robot.commands.drivetrain.RotateToVisionTarget;
import org.usfirst.frc.team614.robot.commands.hopper.RevHopper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Shoot extends CommandGroup {

    public Shoot(boolean isTeleop, boolean shootingFromAirship, boolean shouldRotateAtAll, boolean shouldRotateIfNoVision, boolean rotationDirection) {

//    	 rev shooter
    	if(shootingFromAirship) {
    		addParallel(new RevShooterFromAirship());
    	} else {
    		addParallel(new RevShooterFromHopper());
    	}
    	
    	// line up to boiler
    	// if in autonomous, rotate left or right if vision target isn't on screen
    	// if in teleop, don't rotate if no vision targeting is recieved; default left/right rotation is ignored.
    	// if camera is broken, don't rotate at all
    	if(shouldRotateAtAll) {
//    		addSequential(new RotateToVisionTarget(false, shouldRotateIfNoVision, rotationDirection)); // on blue side => rotate right and vice versa
    		addParallel(new RotateToVisionTarget(false, shouldRotateIfNoVision, rotationDirection)); // on blue side => rotate right and vice versa
    	}
    	// wait until shooter is up to speed...
//    	addSequential(new WaitUntilShooterIsAtTargetSpeed());
//    	feed balls into shooter...
//    	addSequential(new RevHopper());
    	if(isTeleop) {
    		// button held to let command last
    	} else {
    		// autonomous:
    		// approx. time for all balls to shoot
//        	addSequential(new WaitUntilAllBallsAreShot()); // doesnt work haha woops
    	}
    	
    }
    protected void end() {
//    	Robot.hopper.stop();
//    	Robot.shooter.setEnabled(false, false);
    }
    protected void interrupted() {
//    	Robot.hopper.stop();
//    	Robot.shooter.setEnabled(false, false);
    }
}
