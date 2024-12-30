package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;


public class robotHardware {
    private HardwareMap hardwareMap;

//    Drivetrain
    public Drivetrain drivetrain;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor rearLeft;
    public DcMotor rearRight;

    // Lift

    public Lift lift;
    public DcMotor motorAngle1;
    public DcMotor motorAngle2;
    public DcMotor motorExtension1;
    public DcMotor motorExtension2;

    // claw
    public Claw claw;
    public Servo clawServo;
    public static int CLAW_INIT_POS=0;
    public static int CLAW_CLOSE_POS=0;
    public static int CLAW_OPEN_POS =0;


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


    public robotHardware(HardwareMap hardwareMap) {

        drivetrain = new Drivetrain(this.hardwareMap);
        lift = new Lift(this.hardwareMap);
        claw = new Claw(this.hardwareMap);
    }

    public class Drivetrain {
        public Drivetrain(@NonNull HardwareMap hardwareMap) {
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            rearLeft = hardwareMap.get(DcMotor.class, "backLeft");
            rearRight = hardwareMap.get(DcMotor.class, "backRight");

            // Reverse the right side motors. This may be wrong for your setup.
            // If your robot moves backwards when commanded to go forwards,
            // reverse the left side instead.
            // See the note about this earlier on this page.
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
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
    }

    public void Intake () {
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

    public void Hold () {
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
    public class Lift{
        public Lift(@NonNull HardwareMap hardwareMap){
            motorAngle1 = hardwareMap.dcMotor.get("motorAngle1");
            motorAngle2 = hardwareMap.dcMotor.get("motorAngle2");
            motorExtension1 = hardwareMap.dcMotor.get("motorExtension1");
            motorExtension2 = hardwareMap.dcMotor.get("motorExtension2");

            //TODO: set all to run to position
            motorExtension1.setDirection(DcMotorSimple.Direction.FORWARD);
            motorExtension2.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        public int findMaxDistance(){
            //constants
            int LENGTH_OF_CLAW = 1; //TODO: Find Length of Claw
            int INITIAL_OFFSET = 1; //TODO: find the length in which the offset is(be rough)
            //ticks of motor -> degree
            int degree = ticksToDegree(motorAngle1.getCurrentPosition());
            //check degree limit
            if(withinCertifiedAngleLimit(degree)){
                return 0;
            }
            //distance = (42 - LENGTH_OF_CLAW - DIST)/cos(degree)
            int distance = (int) ((42 - LENGTH_OF_CLAW - INITIAL_OFFSET)/Math.cos(degree));
            //dist(in inches) -> ticks
            int ticks = inchesToTicks(distance);
            return ticks;
        }
        public void setMaxDistance(){
            //set Position of Distance with given value
            //check for Max Distance
            motorAngle1.setTargetPosition(findMaxDistance());
            motorAngle2.setTargetPosition(findMaxDistance());
        }
        public boolean withinCertifiedAngleLimit(int angle){
            if (angle < 0 || angle > 134.475 * 4){ //angle less than 0, and greater than 134.475*4
                return false;
            }
            else{
                return true;
            }

        }
        public void retract(){
            //check if ticks == 0;
            if(motorExtension1.getCurrentPosition() == 0){
                telemetry.addLine("At Min Length, Cannot Go Lower");
            }
            //subtract position by -10 or something
        }
        public void extend(){
            //check if ticks == findMaxDistance
            //add ticks by +10 or something
        }
    }

    private int inchesToTicks(double inches) {
        double DIAMETER = 1.0;  //TODO: find diameter
        double CIRCUMFERENCE = DIAMETER * Math.PI;
        return (int) (inches * (537.7*4/CIRCUMFERENCE));
    }
    private int ticksToDegree(int ticks) {
        return (int) (ticks * (360/(537.6*4)));
    }

    public class Claw {
        public Claw(@NonNull HardwareMap hardwareMap) {
            clawServo = hardwareMap.servo.get("claw");
        }
        public void clawOpen(){
            clawServo.setPosition(0.5);
        }
        public void clawGrab(){
            clawServo.setPosition(1.0);
        }
    }
    public void hangInit(){
        //calculate ticks for level 1, 2, 3 ascent
        int ASCENT_BAR_HEIGHT = 1;  //TODO: measure height of ascent bar
        //move arm to 90 deg
        motorAngle1.setTargetPosition(134 * 4);
        motorAngle2.setTargetPosition(134 * 4);
        //increase ticks to specific height
        motorExtension1.setTargetPosition(inchesToTicks(ASCENT_BAR_HEIGHT));
        motorExtension2.setTargetPosition(inchesToTicks(ASCENT_BAR_HEIGHT));
    }
    public void hang(){
        //set distance to 0 ticks
        motorExtension1.setTargetPosition(0);
        motorExtension2.setTargetPosition(0);
    }
    public void resetEncoders(){
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
    public void telemetryUpdate(Telemetry telemetry){
        telemetry.addData("motorExtension1 pos",motorExtension1);
        telemetry.addData("motorExtension2 pos",motorExtension2);
        telemetry.addData("motorAngle1 pos",motorAngle1);
        telemetry.addData("motorAngle2 pos",motorAngle2);
    }
}
