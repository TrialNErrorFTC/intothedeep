/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robotHardwareOld;

/*
 * This file works in conjunction with the External Hardware Class sample called: ConceptExternalHardwareClass.java
 * Please read the explanations in that Sample about how to use this class definition.
 *
 * This file defines a Java Class that performs all the setup and configuration for a sample robot's hardware (motors and sensors).
 * It assumes three motors (left_drive, right_drive and arm) and two servos (left_hand and right_hand)
 *
 * This one file/class can be used by ALL of your OpModes without having to cut & paste the code each time.
 *
 * Where possible, the actual hardware objects are "abstracted" (or hidden) so the OpMode code just makes calls into the class,
 * rather than accessing the internal hardware directly. This is why the objects are declared "private".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with *exactly the same name*.
 *
 * Or... In OnBot Java, add a new file named RobotHardware.java, select this sample, and select Not an OpMode.
 * Also add a new OpMode, select the sample ConceptExternalHardwareClass.java, and select TeleOp.
 *
 */

public class robotHardware {

    /* Declare OpMode members. */
    private LinearOpMode myOpMode =  null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    //drivetrain
    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor rearLeft = null;
    public DcMotor rearRight = null;
    public DcMotor[] motors = null;
    double k = 1;
    int tickConversionConstant = (int) (1425.1/537.7);


    //lift
    public DcMotor motorAngle1;
    public DcMotor motorAngle2;
    public DcMotor motorExtension1;
    public DcMotor motorExtension2;

    public Servo servoClaw;

    // Define constants.  Make them public so they CAN be used by the calling OpMode
    public static int CLAW_INIT_POS = 0;
    public static int CLAW_CLOSE_POS = 0;
    public static int CLAW_OPEN_POS = 0;


    // servo


    public static int EXTEND_INIT_POS = 0;
    public static int EXTEND_INTAKE_POS = 0;
    public static int EXTEND_HOLD_POS = 0;
    public static int EXTEND_LOW_POLE_POS = 125;
    public static int EXTEND_HIGH_POLE_POS = 250;
    public static int EXTEND_LOW_BUCKET_POS = 200;
    public static int EXTEND_HIGH_BUCKET_POS = 1000;


    public static int ANGLE_INIT_POS = 0;
    public static int ANGLE_INTAKE_POS = 0;
    public static int ANGLE_HOLD_POS = 0;
    public static int ANGLE_LOW_POLE_POS = 0;
    public static int ANGLE_HIGH_POLE_POS = 0;
    public static int ANGLE_LOW_BUCKET_POS = 0;
    public static int ANGLE_HIGH_BUCKET_POS = 0;

    enum States {
        INTAKE,
        HOLD,
        LOW_POLE,
        HIGH_POLE,
        LOW_BUCKET,
        HIGH_BUCKET,
        HANG,
    }

    public States currentState;
    

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public robotHardware (LinearOpMode opmode) {
        myOpMode = opmode;
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * <p>
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init()    {
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        frontLeft = myOpMode.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = myOpMode.hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = myOpMode.hardwareMap.get(DcMotor.class, "backLeft");
        rearRight = myOpMode.hardwareMap.get(DcMotor.class, "backRight");
        motors = new DcMotor[]{frontLeft, frontRight, rearLeft, rearRight};


        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motorAngle1 = myOpMode.hardwareMap.get(DcMotor.class, "motorAngle1");


        motorAngle1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorAngle1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorAngle1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorAngle1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        motorAngle2 = myOpMode.hardwareMap.get(DcMotor.class, "motorAngle2");


        motorAngle2.setDirection(DcMotorSimple.Direction.REVERSE);
        motorAngle2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorAngle2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorAngle2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        motorExtension1 = myOpMode.hardwareMap.get(DcMotor.class, "motorExtension1");


        motorExtension1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorExtension1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtension1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorExtension1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        motorExtension2 = myOpMode.hardwareMap.get(DcMotor.class, "motorExtension2");


        motorExtension2.setDirection(DcMotorSimple.Direction.FORWARD);
        motorExtension2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtension2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorExtension2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servoClaw = myOpMode.hardwareMap.servo.get("claw");

    }

    public void startMove(double drive, double strafe, double turn, double scale) {
        double powerFL = (drive + strafe + turn) * scale;
        double powerFR = (drive - strafe - turn) * scale;
        double powerBL = (drive - strafe + turn) * scale;
        double powerBR = (drive + strafe - turn) * scale;

        double maxPower = Math.max(Math.max(Math.abs(powerFL), Math.abs(powerFR)), Math.max(Math.abs(powerBL), Math.abs(powerBR)));
        double max = (maxPower < 1) ? 1 : maxPower;

        frontLeft.setPower(Range.clip(powerFL / max, -1, 1));
        frontRight.setPower(Range.clip(powerFR / max, -1, 1));
        rearLeft.setPower(Range.clip(powerBL / max, -1, 1));
        rearRight.setPower(Range.clip(powerBR / max, -1, 1));
    }
    public void stopMove() {
        for (DcMotor motor : motors) {
            motor.setPower(0);
        }
        motorAngle1.setPower(0);
        motorAngle2.setPower(0);
        motorExtension1.setPower(0);
        motorExtension2.setPower(0);
    }

    public void resetDriveEncoders() {
        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void up(){
        //TODO: targetPosition +10 to currentPosition
        motorAngle1.setTargetPosition(motorAngle1.getCurrentPosition() + 100);
        motorAngle2.setTargetPosition(motorAngle2.getCurrentPosition() + 100);
        //TODO: divide by k for smoothness of closing
        motorExtension1.setTargetPosition((int) (motorExtension2.getCurrentPosition() + (tickConversionConstant * 100)/k));
        motorExtension2.setTargetPosition((int) (motorExtension2.getCurrentPosition() + (tickConversionConstant * 100)/k));

        motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorAngle1.setPower(1);
        motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorAngle2.setPower(1);
        motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension1.setPower(1);
        motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension2.setPower(1);}
    public void down(){
        //TODO: targetPosition +10 to currentPosition
        motorAngle1.setTargetPosition(motorAngle1.getCurrentPosition() - 100);
        motorAngle2.setTargetPosition(motorAngle2.getCurrentPosition() - 100);
        //TODO: divide by k for smoothness of closing
        motorExtension1.setTargetPosition((int) (motorExtension1.getCurrentPosition() - (tickConversionConstant* 100)/k));
        motorExtension2.setTargetPosition((int) (motorExtension2.getCurrentPosition() - (tickConversionConstant * 100)/k));

        motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorAngle1.setPower(1);
        motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorAngle2.setPower(1);
        motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension1.setPower(1);
        motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension2.setPower(1);
    }

    public void extend(){
        //TODO: targetPosition +10 to currentPosition
        //TODO: divide by k for smoothness of closingl
        motorExtension1.setTargetPosition(motorExtension1.getCurrentPosition() + 250);
        motorExtension2.setTargetPosition(motorExtension2.getCurrentPosition() + 250);

        motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension1.setPower(1);
        motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension2.setPower(1);}
    public void retract(){
        //TODO: targetPosition +10 to currentPosition
        //TODO: divide by k for smoothness of closing
        motorExtension1.setTargetPosition(motorExtension1.getCurrentPosition() - 250);
        motorExtension2.setTargetPosition(motorExtension2.getCurrentPosition() - 250);

        motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension1.setPower(1);
        motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension2.setPower(1);
    }
            public void Intake() {
            motorAngle1.setTargetPosition(ANGLE_INTAKE_POS);
            motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle1.setPower(0.5);
            motorAngle2.setTargetPosition(ANGLE_INTAKE_POS);
            motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle2.setPower(0.5);

            motorExtension1.setTargetPosition(EXTEND_INTAKE_POS);
            motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension1.setPower(0.5);
            motorExtension2.setTargetPosition(EXTEND_INTAKE_POS);
            motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension2.setPower(0.5);
            // clow wil be in controller sepreate loop
        }
        public void Hold() {
            motorAngle1.setTargetPosition(ANGLE_HOLD_POS);
            motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle1.setPower(0.5);
            motorAngle2.setTargetPosition(ANGLE_HOLD_POS);
            motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle2.setPower(0.5);

            motorExtension1.setTargetPosition(EXTEND_HOLD_POS);
            motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension1.setPower(0.5);
            motorExtension2.setTargetPosition(EXTEND_HOLD_POS);
            motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension2.setPower(0.5);
        }

        public void LowPole() {
            motorAngle1.setTargetPosition(ANGLE_LOW_POLE_POS);
            motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle1.setPower(0.5);
            motorAngle2.setTargetPosition(ANGLE_LOW_POLE_POS);
            motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle2.setPower(0.5);

            motorExtension1.setTargetPosition(EXTEND_LOW_POLE_POS);
            motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension1.setPower(0.5);
            motorExtension2.setTargetPosition(EXTEND_LOW_POLE_POS);
            motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension2.setPower(0.5);
        }

        public void HighPole() {
            motorAngle1.setTargetPosition(ANGLE_HIGH_POLE_POS);
            motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle1.setPower(0.5);
            motorAngle2.setTargetPosition(ANGLE_HIGH_POLE_POS);
            motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle2.setPower(0.5);

            motorExtension1.setTargetPosition(EXTEND_HIGH_POLE_POS);
            motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension1.setPower(0.5);
            motorExtension2.setTargetPosition(EXTEND_HIGH_POLE_POS);
            motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension2.setPower(0.5);
        }

        public void LowBucket() {
            motorAngle1.setTargetPosition(ANGLE_LOW_BUCKET_POS);
            motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle1.setPower(0.5);
            motorAngle2.setTargetPosition(ANGLE_LOW_BUCKET_POS);
            motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle2.setPower(0.5);

            motorExtension1.setTargetPosition(EXTEND_LOW_BUCKET_POS);
            motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension1.setPower(0.5);
            motorExtension2.setTargetPosition(EXTEND_LOW_BUCKET_POS);
            motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension2.setPower(0.5);
        }

        public void HighBucket() {
            motorAngle1.setTargetPosition(ANGLE_HIGH_BUCKET_POS);
            motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle1.setPower(0.5);
            motorAngle2.setTargetPosition(ANGLE_HIGH_BUCKET_POS);
            motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorAngle2.setPower(0.5);

            motorExtension1.setTargetPosition(EXTEND_HIGH_BUCKET_POS);
            motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension1.setPower(0.5);
            motorExtension2.setTargetPosition(EXTEND_HIGH_BUCKET_POS);
            motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorExtension2.setPower(0.5);
        }
        public int findMaxDistance() {
                //constants
                int LENGTH_OF_CLAW = 1; //TODO: Find Length of Claw
                int INITIAL_OFFSET = 1; //TODO: find the length in which the offset is(be rough)
                //ticks of motor -> degree
                int degree = ticksToDegree(motorAngle1.getCurrentPosition());
                //check degree limit
                if (withinCertifiedAngleLimit(degree)) {
                    return 0;
                }
                //distance = (42 - LENGTH_OF_CLAW - DIST)/cos(degree)
                int distance = (int) ((42 - LENGTH_OF_CLAW - INITIAL_OFFSET) / Math.cos(degree));
                //dist(in inches) -> ticks
                int ticks = inchesToTicks(distance);
                return ticks;
            }

            public void setMaxDistance() {
                //set Position of Distance with given value
                //check for Max Distance
                motorAngle1.setTargetPosition(findMaxDistance());
                motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorAngle2.setTargetPosition(findMaxDistance());
                motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            public boolean withinCertifiedAngleLimit(int angle) {
                if (angle < 0 || angle > 90) { //angle less than 0, and greater than 134.475*4
                    return false;
                } else {
                    return true;
                }

            }




            private int inchesToTicks(double inches) {
            double DIAMETER = 1.0;  //TODO: find diameter
            double CIRCUMFERENCE = DIAMETER * Math.PI;
            return (int) (inches * (1425.1 / CIRCUMFERENCE));
        }

        private int ticksToDegree(int ticks) {
            return (int) (ticks * (360 / (537.7)));
        }

    public void clawOpen() {
        servoClaw.setPosition(0.5);
    }

    public void clawGrab() {
        servoClaw.setPosition(1.0);
    }

            public void hangInit() {
            //calculate ticks for level 1, 2, 3 ascent
            int ASCENT_BAR_HEIGHT = 1;  //TODO: measure height of ascent bar
            //move arm to 90 deg
            motorAngle1.setTargetPosition(134);
            motorAngle2.setTargetPosition(134);
            //increase ticks to specific height
            motorExtension1.setTargetPosition(inchesToTicks(ASCENT_BAR_HEIGHT));
            motorExtension2.setTargetPosition(inchesToTicks(ASCENT_BAR_HEIGHT));
        }

        public void hang() {
            //set distance to 0 ticks
            motorExtension1.setTargetPosition(0);
            motorExtension2.setTargetPosition(0);
        }

    public void resetEncoders() {
        //reset all encoders
        motorAngle1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorAngle2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtension1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtension2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorAngle1.setTargetPosition(0);
        motorAngle2.setTargetPosition(0);
        motorExtension2.setTargetPosition(0);
        motorExtension1.setTargetPosition(0);

        motorAngle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorAngle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void telemetryUpdate(Telemetry telemetry) {
        myOpMode.telemetry.addData("BL pos", rearLeft.getCurrentPosition());
        myOpMode.telemetry.addData("BR pos", rearRight.getCurrentPosition());
        myOpMode.telemetry.addData("FR pos", frontRight.getCurrentPosition());
        myOpMode.telemetry.addData("FL pos", frontLeft.getCurrentPosition());
        myOpMode.telemetry.addData("extension1 pos", motorExtension1.getCurrentPosition());
        myOpMode.telemetry.addData("extension2 pos", motorExtension2.getCurrentPosition());
        myOpMode.telemetry.addData("angle1 pos", motorAngle1.getCurrentPosition());
        myOpMode.telemetry.addData("angle2 pos", motorAngle2.getCurrentPosition());
        myOpMode.telemetry.update();
    }

}