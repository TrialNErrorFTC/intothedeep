package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.robotHardware;

@TeleOp
public class robotTeleOp extends OpMode {
robotHardware robot;

    @Override
    public void init() {
        robot = new robotHardware(hardwareMap);

        robot.resetEncoders();

        robot.claw.clawOpen();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }


    @Override
    public void start() {
        telemetry.addData("Status", "Running");
    }

    @Override
    public void loop() {
        driveControl();
        clawControl();
        armControl();
        hangControl();
    }

    private void hangControl() {
    }

    private void armControl() {
        if(gamepad1.left_trigger >= 0.75){
            robot.claw.clawGrab();
        } else{
            robot.claw.clawOpen();
        }
    }

    private void clawControl() {
    }


    private void driveControl() {
        double scale = 0.8;
        if (gamepad1.left_bumper) {
            gamepad1.rumble(500);
            scale = 1;
        } else if (gamepad1.left_trigger > 0.5) {
            scale = 0.5;
        }

        double drive = gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        robot.drivetrain.startMove(drive, strafe, turn, scale);

        robot.telemetryUpdate(telemetry);

    }

}
