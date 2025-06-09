package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonRRAuto.AutoCommon;

@Autonomous(name="Main Autonomous", group="Auto")
public class AutonomousMain extends AutoCommon {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Initialize using parent class
        super.runOpMode();
        
        if (opModeIsActive()) {
            // Reset runtime for autonomous sequence
            runtime.reset();
            
            telemetry.addData("Status", "Starting Autonomous Sequence");
            telemetry.update();
            
            // Example autonomous sequence for Into the Deep
            // Modify this based on your team's strategy
            
            // Step 1: Drive forward to scoring position
            telemetry.addData("Step", "1 - Drive to scoring position");
            telemetry.update();
            drive(24, 0.6); // Drive forward 24 inches
            sleep(500);
            
            // Step 2: Turn to face scoring area
            telemetry.addData("Step", "2 - Turn to scoring area");
            telemetry.update();
            turn(90, 0.5); // Turn 90 degrees
            sleep(500);
            
            // Step 3: Drive to scoring area
            telemetry.addData("Step", "3 - Approach scoring area");
            telemetry.update();
            drive(12, 0.4); // Drive forward 12 inches slowly
            sleep(500);
            
            // Step 4: Score (placeholder - add your scoring mechanism here)
            telemetry.addData("Step", "4 - Scoring");
            telemetry.update();
            // TODO: Add scoring mechanism control here
            sleep(1000);
            
            // Step 5: Back away from scoring area
            telemetry.addData("Step", "5 - Back away");
            telemetry.update();
            drive(-12, 0.4); // Back up 12 inches
            sleep(500);
            
            // Step 6: Turn toward parking area
            telemetry.addData("Step", "6 - Turn to parking");
            telemetry.update();
            turn(0, 0.5); // Turn back to 0 degrees
            sleep(500);
            
            // Step 7: Drive to parking area
            telemetry.addData("Step", "7 - Drive to parking");
            telemetry.update();
            driveOnHeading(36, 0.6, 0); // Drive 36 inches while maintaining heading
            sleep(500);
            
            telemetry.addData("Status", "Autonomous Complete");
            telemetry.addData("Total Runtime", runtime.toString());
            telemetry.update();
        }
        
        // Ensure all motors are stopped
        robot.stopMove();
    }
} 