package org.firstinspires.ftc.teamcode.NonRRAuto;

import android.graphics.Bitmap;
import android.os.Environment;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.testing.RobotHardware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Autonomous(name="Auto Common Base", group="Auto")
public class AutoCommon extends LinearOpMode {

    protected RobotHardware robot;
    private double initialHeading = 0;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Initialize robot hardware
        robot = new RobotHardware(this);
        
        // Wait for IMU to calibrate
        telemetry.addData("Status", "Calibrating IMU...");
        telemetry.update();
        
        while (!isStopRequested() && !robot.imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }
        
        initialHeading = getHeading();
        
        telemetry.addData("Status", "Ready for start");
        telemetry.addData("IMU Calibrated", robot.imu.isGyroCalibrated());
        telemetry.addData("Initial Heading", "%.1f degrees", initialHeading);
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {
            // This is the base class - actual autonomous logic should be in subclasses
            telemetry.addData("Status", "AutoCommon base class running");
            telemetry.addData("Runtime", runtime.toString());
            telemetry.update();
            
            // Example movement - override this in subclasses
            drive(12, 0.5); // Drive forward 12 inches at 50% power
            sleep(1000);
        }
        
        // Stop all motors
        robot.stopMove();
    }

    private double inchesToTicks(double inches) {
        return inches * robot.DRIVE_MOTOR_TICKS_PER_ROTATION / (robot.WHEEL_DIAMETER * Math.PI);
    }

    protected double getHeading() {
        return robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - initialHeading;
    }

    protected double getHeadingDiff(double targetHeading) {
        double headingDiff = getHeading() - targetHeading;
        while (headingDiff > 180) {
            headingDiff -= 360;
        }
        while (headingDiff < -180) {
            headingDiff += 360;
        }
        return headingDiff;
    }

    protected void drive(double distance, double power) {
        double dir = Math.signum(distance * power);
        if (dir == 0) return;

        double encoderTicks = inchesToTicks(Math.abs(distance));

        robot.resetDriveEncoders();
        robot.startMove(Math.abs(power) * dir, 0, 0, 1.0);
        
        while (opModeIsActive() && Math.abs(robot.frontLeft.getCurrentPosition()) < encoderTicks) {
            telemetry.addData("Target", "%.0f ticks", encoderTicks);
            telemetry.addData("Current", "%d ticks", robot.frontLeft.getCurrentPosition());
            telemetry.addData("Remaining", "%.1f inches", (encoderTicks - Math.abs(robot.frontLeft.getCurrentPosition())) / robot.DRIVE_MOTOR_TICKS_PER_ROTATION * robot.WHEEL_DIAMETER * Math.PI);
            telemetry.update();
        }
        
        robot.stopMove();
    }

    protected void driveOnHeading(double distance, double power, double targetHeading) {
        double dir = Math.signum(distance * power);
        if (dir == 0) return;

        double encoderTicks = inchesToTicks(Math.abs(distance));

        robot.resetDriveEncoders();
        
        while (opModeIsActive() && Math.abs(robot.frontLeft.getCurrentPosition()) < encoderTicks) {
            double headingError = getHeadingDiff(targetHeading);
            double turnCorrection = Range.clip(headingError * 0.01, -0.2, 0.2); // P controller for heading
            
            robot.startMove(Math.abs(power) * dir, 0, turnCorrection, 1.0);
            
            telemetry.addData("Target", "%.0f ticks", encoderTicks);
            telemetry.addData("Current", "%d ticks", robot.frontLeft.getCurrentPosition());
            telemetry.addData("Heading Error", "%.1f degrees", headingError);
            telemetry.addData("Turn Correction", "%.3f", turnCorrection);
            telemetry.update();
        }
        
        robot.stopMove();
    }

    protected void turn(double targetHeading, double power) {
        double headingError = getHeadingDiff(targetHeading);
        
        while (opModeIsActive() && Math.abs(headingError) > 2.0) { // 2 degree tolerance
            headingError = getHeadingDiff(targetHeading);
            double turnPower = Range.clip(Math.abs(headingError) * 0.02, 0.1, Math.abs(power)); // Minimum 10% power
            
            if (headingError > 0) {
                robot.startMove(0, 0, turnPower, 1.0); // Turn left
            } else {
                robot.startMove(0, 0, -turnPower, 1.0); // Turn right
            }
            
            telemetry.addData("Target Heading", "%.1f degrees", targetHeading);
            telemetry.addData("Current Heading", "%.1f degrees", getHeading());
            telemetry.addData("Heading Error", "%.1f degrees", headingError);
            telemetry.addData("Turn Power", "%.3f", turnPower);
            telemetry.update();
        }
        
        robot.stopMove();
    }
}