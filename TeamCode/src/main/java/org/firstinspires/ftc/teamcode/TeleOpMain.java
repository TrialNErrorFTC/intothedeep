package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.testing.RobotHardware;

@TeleOp(name="Main TeleOp", group="TeleOp")
public class TeleOpMain extends LinearOpMode {

    private RobotHardware robot;
    private ElapsedTime runtime = new ElapsedTime();

    // Drive control variables
    private double driveSpeed = 0.8;
    private double turnSpeed = 0.6;
    private boolean slowMode = false;

    @Override
    public void runOpMode() {
        // Initialize hardware
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        robot = new RobotHardware(this);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Ready for start");
        telemetry.addData("Controls", "Left stick: drive, Right stick: turn");
        telemetry.addData("Controls", "Right bumper: slow mode");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Get gamepad inputs
            double drive = -gamepad1.left_stick_y;  // Forward/backward
            double strafe = gamepad1.left_stick_x;   // Left/right
            double turn = gamepad1.right_stick_x;    // Rotation

            // Slow mode toggle
            if (gamepad1.right_bumper) {
                slowMode = true;
            } else {
                slowMode = false;
            }

            // Apply speed scaling
            double speedMultiplier = slowMode ? 0.3 : driveSpeed;
            double turnMultiplier = slowMode ? 0.2 : turnSpeed;

            // Scale inputs
            drive *= speedMultiplier;
            strafe *= speedMultiplier;
            turn *= turnMultiplier;

            // Send power to motors
            robot.startMove(drive, strafe, turn, 1.0);

            // Show telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Drive Mode", slowMode ? "SLOW" : "NORMAL");
            telemetry.addData("Drive", "%.2f", drive);
            telemetry.addData("Strafe", "%.2f", strafe);
            telemetry.addData("Turn", "%.2f", turn);
            telemetry.addData("Front Left Power", "%.2f", robot.driveTrain.motorFL.getPower());
            telemetry.addData("Front Right Power", "%.2f", robot.driveTrain.motorFR.getPower());
            telemetry.addData("Back Left Power", "%.2f", robot.driveTrain.motorBL.getPower());
            telemetry.addData("Back Right Power", "%.2f", robot.driveTrain.motorBR.getPower());
            telemetry.update();
        }

        // Stop all motors when OpMode ends
        robot.stopMove();
    }
} 