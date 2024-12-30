package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
        testAngleControl();
        testExtendoControl();

        // java functions stop other code running, so we make a switch case, switch case code are non-blocking code, so other code can be run
//        switch (robot.currentState) {
//            // the cases for intake
//            case INTAKE:
//                robot.Intake();
//            case HOLD:
//                robot.Hold();
//            case LOW_POLE:
//                robot.LowPole();
//            case HIGH_POLE:
//                robot.HighPole();
//            case HIGH_BUCKET:
//                robot.HighBucket();
//            case LOW_BUCKET:
//                robot.LowBucket();
//            case HANG:
//                //
//                // this code is not blocked, because of the FSM. You can do this even if the other is happening.
//        }
        telemetry.update();
    }

    private void testExtendoControl() {
//        if (gamepad1.b) {
//            robot.motorExtension2.setPower(1);
//            robot.motorExtension1.setPower(1);
//        } else if (gamepad1.a) {
//            robot.motorExtension2.setPower(-1);
//            robot.motorExtension1.setPower(-1);
//        } else {
//            robot.motorExtension2.setPower(0);
//            robot.motorExtension1.setPower(0);
//        }
        if (gamepad1.a) {
            robot.motorExtension2.setPower(1);
        } else {
            robot.motorExtension2.setPower(0);
        }
        if (gamepad1.b) {
            robot.motorExtension1.setPower(1);
        } else {
            robot.motorExtension1.setPower(0);
        }
        if (gamepad1.x) {
            robot.motorAngle1.setPower(1);
        } else {
            robot.motorAngle1.setPower(0);
        }
        if (gamepad1.y){
            robot.motorAngle2.setPower(1);
        } else {
            robot.motorAngle2.setPower(0);
        }

        if(gamepad1.dpad_up){
            robot.motorExtension2.setPower(-1);
        } else {
            robot.motorExtension2.setPower(0);
        }
        if (gamepad1.dpad_down) {
            robot.motorExtension1.setPower(-1);
        } else {
            robot.motorExtension1.setPower(0);
        }
        if (gamepad1.dpad_left) {
            robot.motorAngle1.setPower(-1);
        } else {
            robot.motorAngle1.setPower(0);
        }
        if (gamepad1.dpad_right){
            robot.motorAngle2.setPower(-1);
        } else {
            robot.motorAngle2.setPower(0);
        }
        telemetry.update();
    }


    private void testAngleControl() {
        if (gamepad1.x) {
            robot.motorAngle1.setPower(1);
            robot.motorAngle2.setPower(1);
        } else if (gamepad1.y){

            robot.motorAngle1.setPower(-1);
            robot.motorAngle2.setPower(-1);

        }else {
            robot.motorAngle2.setPower(0);
            robot.motorAngle1.setPower(0);
        }
    }


    private void hangControl() {
    }

    private void armControl() {

    }



    private void clawControl() {
        if(gamepad1.left_trigger >= 0.5 || robot.currentState == robotHardware.States.INTAKE){
            robot.claw.clawOpen();
        } else{
            robot.claw.clawGrab();
        }
    }


    private void driveControl() {
        double scale = 0.5;
        if (gamepad1.left_bumper) {
            gamepad1.rumble(500);
            scale = 0.8;
        } else if (gamepad1.left_trigger > 0.5) {
            scale = 0.1;
        }

        double drive = gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        robot.drivetrain.startMove(drive, strafe, turn, scale);

        robot.telemetryUpdate(telemetry);
    }

}
