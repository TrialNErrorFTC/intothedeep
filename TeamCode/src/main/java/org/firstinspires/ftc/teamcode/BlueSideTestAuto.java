package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "BLUE Side RoadRunner Auto", group = "RoadRunner")
public class BlueSideTestAuto extends LinearOpMode {
    
    private ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public void runOpMode() throws InterruptedException {
        // Starting position for blue side
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // Build trajectory
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToY(48)  // Move forward to scoring position
                .waitSeconds(1) // Wait at scoring position
                .lineToY(36)  // Move to intermediate position
                .turn(Math.toRadians(90)) // Turn 90 degrees
                .lineToX(24)  // Strafe to parking position
                .lineToY(12); // Move to final parking position

        Action trajectoryAction = tab1.build();

        // Wait for start
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Starting Position", "Blue Side (%.1f, %.1f) at %.1f°", 
                         initialPose.position.x, initialPose.position.y, 
                         Math.toDegrees(initialPose.heading.toDouble()));
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {
            telemetry.addData("Status", "Running RoadRunner Autonomous");
            telemetry.update();

            // Execute the trajectory
            Actions.runBlocking(
                new SequentialAction(
                    trajectoryAction
                )
            );

            telemetry.addData("Status", "Autonomous Complete");
            telemetry.addData("Runtime", runtime.toString());
            telemetry.update();
        }
    }
}