package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class robotTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

        }
    }
}
