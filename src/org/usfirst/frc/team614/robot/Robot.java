
package org.usfirst.frc.team614.robot;

import org.usfirst.frc.team614.robot.commands.ResetEncoder;
import org.usfirst.frc.team614.robot.subsystems.Drivetrain;
import org.usfirst.frc.team614.robot.subsystems.Pneumatics;
import org.usfirst.frc.team614.robot.subsystems.Shooter;
import org.usfirst.frc.team614.robot.subsystems.Winch;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static AHRS navX;
	public static Drivetrain drivetrain;
	public static Pneumatics pneumatics;
	public static Shooter shooter;
	public static Winch winch;

	public static PowerDistributionPanel pdp;
	public static NetworkTable cameraTable;
	public static OI oi;
	
    Command autonomousCommand;
    SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        try {
            /* Begins communication with NavX.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            navX = new AHRS(SPI.Port.kMXP,(byte)200);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    	drivetrain = new Drivetrain();
    	pneumatics = new Pneumatics();
    	shooter = new Shooter();
    	winch = new Winch();
    	
    	pdp = new PowerDistributionPanel();
    	NetworkTable.setServerMode();
    	NetworkTable.setTeam(614);
    	NetworkTable.initialize();
    	cameraTable = NetworkTable.getTable("test");
    	
		oi = new OI();
		
        chooser = new SendableChooser();
//        chooser.addDefault("Drive Straight Full", new DriveStraight(.5, 1.0));
//        chooser.addObject("Drive Straight Half", new DriveStraight(.5, .5));
//        SmartDashboard.putData("Run At Full Speed", new ShooterDrive());
        SmartDashboard.putData("Drive Straight", chooser);
        SmartDashboard.putData("Reset Encoder", new ResetEncoder());
//        SmartDashboard.putNumber("Rotation Rate", .5);
//        SmartDashboard.putNumber("Vision Offset", -.5);

        SmartDashboard.putNumber("Drivetrain left Encoder Distance [???]", 0);
        SmartDashboard.putNumber("Drivetrain right Encoder Distance [???]", 0);
        
        SmartDashboard.putNumber("Shooter P", Constants.shooterP);
        SmartDashboard.putNumber("Shooter I", Constants.shooterI);
        SmartDashboard.putNumber("Shooter D", Constants.shooterD);
        SmartDashboard.putNumber("Shooter F", Constants.shooterF);
        SmartDashboard.putNumber("Shooter non-PID Target [%]", 0);
        SmartDashboard.putNumber("Shooter PID Target [XXX]", 0);
        SmartDashboard.putNumber("Shooter PID Output [XXX]", 0);
        SmartDashboard.putNumber("Shooter PID Error", 0);
        SmartDashboard.putNumber("Shooter Encoder Distance [Revs]", 0);
        SmartDashboard.putNumber("Shooter Encoder Count [Revs*4096]", 0);
        SmartDashboard.putNumber("Shooter Encoder Rate [Revs/Sec]", 0);
		SmartDashboard.putNumber("Shooter Encoder MAX Rate [Revs/Sec]", 0);

		SmartDashboard.putNumber("Winch PD ID", RobotMap.PDPWinchMotor);
		SmartDashboard.putNumber("Winch Current Draw [Amps]", -.1);
		SmartDashboard.putNumber("MAX Winch Current Draw [Amps]", -.1);

    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	// resets NavX and disables the PID controller.
    	Robot.navX.reset();
    	Robot.winch.reset();
    	drivetrain.setUsingPID(false);
//    	Robot.shooter.getPIDController().disable();
//    	Robot.shooter.getPIDController().reset();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
//    	shooter.enable();
    	Robot.navX.reset();
        autonomousCommand = (Command) chooser.getSelected();
        /*
		String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
			case "Drive Straight Full": {
				autonomousCommand = new DriveStraight(.5, 1.0);
				break;
			}
			case "Drive Straight Half": {
				autonomousCommand = new DriveStraight(.5, 1.0);
				break;
			}
			default: {
				
			}
		}*/
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
//    	shooter.enable();
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        printNavXData();
        
        // encoder distances
        SmartDashboard.putNumber("Drivetrain left Encoder Distance [???]", drivetrain.leftEncoder.getDistance());
        SmartDashboard.putNumber("Drivetrain right Encoder Distance [???]", drivetrain.rightEncoder.getDistance());
        
        
    	// current draw & update max current draw
        if(SmartDashboard.getNumber("MAX Winch Current Draw [Amps]", 0) < Robot.pdp.getCurrent(RobotMap.PDPWinchMotor)) {
        	SmartDashboard.putNumber("MAX Winch Current Draw [Amps]", Robot.pdp.getCurrent(RobotMap.PDPWinchMotor));
        }
        SmartDashboard.putNumber("Winch Current Draw [Amps]", Robot.pdp.getCurrent(RobotMap.PDPWinchMotor));
        
        // shooter
//        SmartDashboard.putNumber("Shooter PID Output [XXX]", XXX);
//        SmartDashboard.putNumber("Shooter PID Error", XXX);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    public static void printNavXData() {
		double start_time = Timer.getFPGATimestamp();
	    Timer.delay(0.020);		/* wait for one motor update time period (50Hz)     */
	
	    /* Calculate/display effective update rate in hz */
	    double delta_time = Timer.getFPGATimestamp() - start_time;
	    double update_count = navX.getUpdateCount();
	    if ( update_count > 0 ) {
	    	double avg_updates_per_sec = delta_time / update_count;
	    	if ( avg_updates_per_sec > 0.0 ) {
	    		SmartDashboard.putNumber("IMU_EffUpdateRateHz", 1.0 / avg_updates_per_sec);
	    	}
	    }
	    
	    /* Display 6-axis Processed Angle Data                                      */
	//        SmartDashboard.putData("     ");
	    SmartDashboard.putBoolean(  "IMU_Connected",        navX.isConnected());
	    SmartDashboard.putBoolean(  "IMU_IsCalibrating",    navX.isCalibrating());
	    SmartDashboard.putNumber(   "IMU_Yaw",              navX.getYaw());
	    SmartDashboard.putNumber(   "IMU_Pitch",            navX.getPitch());
	    SmartDashboard.putNumber(   "IMU_Roll",             navX.getRoll());
	    
	    /* Display tilt-corrected, Magnetometer-based heading (requires             */
	    /* magnetometer calibration to be useful)                                   */
	    
	    SmartDashboard.putNumber(   "IMU_CompassHeading",   navX.getCompassHeading());
	    
	    /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
	    SmartDashboard.putNumber(   "IMU_FusedHeading",     navX.getFusedHeading());
	
	    /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
	    /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
	    
	    SmartDashboard.putNumber(   "IMU_TotalYaw",         navX.getAngle());
	    SmartDashboard.putNumber(   "IMU_YawRateDPS",       navX.getRate());
	
	    /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
	    
	    SmartDashboard.putNumber(   "IMU_Accel_X",          navX.getWorldLinearAccelX());
	    SmartDashboard.putNumber(   "IMU_Accel_Y",          navX.getWorldLinearAccelY());
	    SmartDashboard.putBoolean(  "IMU_IsMoving",         navX.isMoving());
	    SmartDashboard.putBoolean(  "IMU_IsRotating",       navX.isRotating());
	
	    /* Display estimates of velocity/displacement.  Note that these values are  */
	    /* not expected to be accurate enough for estimating robot position on a    */
	    /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
	    /* of these errors due to single (velocity) integration and especially      */
	    /* double (displacement) integration.                                       */
	    
	    SmartDashboard.putNumber(   "Velocity_X",           navX.getVelocityX());
	    SmartDashboard.putNumber(   "Velocity_Y",           navX.getVelocityY());
	    SmartDashboard.putNumber(   "Displacement_X",       navX.getDisplacementX());
	    SmartDashboard.putNumber(   "Displacement_Y",       navX.getDisplacementY());
	    
	    /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
	    /* NOTE:  These values are not normally necessary, but are made available   */
	    /* for advanced users.  Before using this data, please consider whether     */
	    /* the processed data (see above) will suit your needs.                     */
	    
//	    SmartDashboard.putNumber(   "RawGyro_X",            navX.getRawGyroX());
//	    SmartDashboard.putNumber(   "RawGyro_Y",            navX.getRawGyroY());
//	    SmartDashboard.putNumber(   "RawGyro_Z",            navX.getRawGyroZ());
//	    SmartDashboard.putNumber(   "RawAccel_X",           navX.getRawAccelX());
//	    SmartDashboard.putNumber(   "RawAccel_Y",           navX.getRawAccelY());
//	    SmartDashboard.putNumber(   "RawAccel_Z",           navX.getRawAccelZ());
//	    SmartDashboard.putNumber(   "RawMag_X",             navX.getRawMagX());
//	    SmartDashboard.putNumber(   "RawMag_Y",             navX.getRawMagY());
//	    SmartDashboard.putNumber(   "RawMag_Z",             navX.getRawMagZ());
//	    SmartDashboard.putNumber(   "IMU_Temp_C",           navX.getTempC());
	    
	    /* Omnimount Yaw Axis Information                                           */
	    /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
	    AHRS.BoardYawAxis yaw_axis = navX.getBoardYawAxis();
	    SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
	    SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
	    
	    /* Sensor Board Information                                                 */
//	    SmartDashboard.putString(   "FirmwareVersion",      navX.getFirmwareVersion());
	    
	    /* Quaternion Data                                                          */
	    /* Quaternions are fascinating, and are the most compact representation of  */
	    /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
	    /* from the Quaternions.  If interested in motion processing, knowledge of  */
	    /* Quaternions is highly recommended.                                       */
//	    SmartDashboard.putNumber(   "QuaternionW",          navX.getQuaternionW());
//	    SmartDashboard.putNumber(   "QuaternionX",          navX.getQuaternionX());
//	    SmartDashboard.putNumber(   "QuaternionY",          navX.getQuaternionY());
//	    SmartDashboard.putNumber(   "QuaternionZ",          navX.getQuaternionZ());
	    
	    /* Sensor Data Timestamp */
//	    SmartDashboard.putNumber(   "SensorTimestamp",		navX.getLastSensorTimestamp());
	    
	    /* Connectivity Debugging Support                                           */
//	    SmartDashboard.putNumber(   "IMU_Byte_Count",       navX.getByteCount());
//	    SmartDashboard.putNumber(   "IMU_Update_Count",     navX.getUpdateCount());
    }
}
