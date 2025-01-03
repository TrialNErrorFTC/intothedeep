package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robotHardware;

@TeleOp
public class robotTeleOp extends LinearOpMode {

    robotHardware robot = new robotHardware(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();
        robot.resetEncoders();
        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {
            clawControl();
            angleControl();
            extensionControl();
            driveControl();
        }
    }

    private void clawControl() {
        if (gamepad1.a) {
            robot.clawOpen();
        }
        if (gamepad1.b) {
            robot.clawGrab();
        }
    }

    private void angleControl() {
        if (gamepad1.dpad_up) {
            robot.up();
        }
        ;
        if (gamepad1.dpad_down) {
            robot.down();
        }
        ;
    }

    private void extensionControl() {
        if (gamepad1.left_bumper) {
            robot.extend();
        } else if (gamepad1.right_bumper) {
            robot.retract();
        }

    }

    private void testAngleControl() {
        //angle stuff
//        if (gamepad1.left_bumper){
//            robot.motorAngle1.setPower(0.7);
//            robot.motorAngle2.setPower(0.7);
//        } else {
//            robot.motorAngle1.setPower(0);
//            robot.motorAngle2.setPower(0);
//        }

    }

    private void testExtensionControl() {
        //extension stuff
//        if (gamepad1.right_bumper){
//            robot.motorExtension1.setPower(0.7);
//            robot.motorExtension2.setPower(0.7);
//        } else {
//            robot.motorExtension1.setPower(0);
//            robot.motorExtension2.setPower(0);
//        }
    }

    private void driveControl() {
        double scale = 0.5;
        if (gamepad1.left_trigger > 0.5) {
            gamepad1.rumble(500);
            scale = 0.8;
        } else if (gamepad1.right_trigger > 0.5) {
            scale = 0.1;
        }

        double drive = gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        robot.startMove(drive, strafe, turn, scale);

        robot.telemetryUpdate(telemetry);
    }

}


