
package org.usfirst.frc.team614.robot.commands.pneumatics;

import org.usfirst.frc.team614.robot.Constants;
import org.usfirst.frc.team614.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Toggles the shooter piston; if it's in, make it out, and vice versa.
 */
public class TogglePiston extends Command {
	
	// true if dropper piston, false if squeezer piston
	boolean dropper = true;
	
    public TogglePiston(boolean dropper) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.pneumatics);
        this.dropper = dropper;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(dropper) {
        	if (Robot.pneumatics.getDropperState().equals(Constants.pistonIn)) {
        		Robot.pneumatics.setDropperState(Constants.pistonOut);
        	} else {
        		Robot.pneumatics.setDropperState(Constants.pistonIn);
        	}
    	} else {
    		Robot.pneumatics.ringlight.set(!Robot.pneumatics.ringlight.get());
    	}
//        	if (Robot.pneumatics.squeezer.get().equals(DoubleSolenoid.Value.kForward)) {
//        		Robot.pneumatics.squeezer.set(DoubleSolenoid.Value.kReverse);
//        	} else {
//        		Robot.pneumatics.squeezer.set(DoubleSolenoid.Value.kForward);
//        	}
//    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
