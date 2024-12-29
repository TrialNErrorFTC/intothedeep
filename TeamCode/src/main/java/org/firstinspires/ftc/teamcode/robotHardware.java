package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

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
    public DcMotor motorAngleF;
    public DcMotor motorAngleR;
    public DcMotor motorLiftF;
    public DcMotor motorLiftR;


    // claw
    public Claw claw;
    public Servo clawServo;


    public robotHardware() {

        drivetrain = new Drivetrain(hardwareMap);
        lift = new Lift(hardwareMap);
    }

    public class Drivetrain {
        public Drivetrain(@NonNull HardwareMap hardwareMap) {
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
            rearRight = hardwareMap.get(DcMotor.class, "rearRight");

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
    public class Lift{
        public Lift(@NonNull HardwareMap hardwareMap){
            motorAngleF = hardwareMap.dcMotor.get("motorAngleF");
            motorAngleR = hardwareMap.dcMotor.get("motorAngleR");
            motorLiftF = hardwareMap.dcMotor.get("motorLiftF");
            motorLiftR = hardwareMap.dcMotor.get("motorLiftR");

            //TODO: set all to run to position
            motorLiftF.setDirection(DcMotorSimple.Direction.FORWARD);
            motorLiftR.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        //add lift movements later
        //Autonomous
        public void extendToHighBasket(){

        }
        //TeleOp
        public void extendToLegalLimit(){
            //extend to legal limit based on angle
            //all constants
            
            //motorAngle Ticks -> Deg
            double TICKS_FULL_ROTATION = 537.6;
            double DIAMETER = 1.0;
            double CIRCUMFERENCE = DIAMETER * Math.PI;
            double motorDegree = motorAngleF.getCurrentPosition() * (360 / TICKS_FULL_ROTATION);

            //equation = 42in / cos(angle)
            int toInches = (int) (42/ Math.cos(motorDegree));

            //inches to ticks
            int toTicks = (int) (toInches * (CIRCUMFERENCE/TICKS_FULL_ROTATION));
            motorLiftR.setTargetPosition(toTicks);
            motorLiftF.setTargetPosition(toTicks);
        }
        //hang
        public void hangInit(){
            int LENGTH_OF_BOX = 0;
            double DIAMETER = 1.0;
            double CIRCUMFERENCE = DIAMETER * Math.PI;
            float TICKS_FULL_ROTATION = 0;

        }
        public void hangFinal(){}
    }

    public class Claw {
        public Claw(@NonNull HardwareMap hardwareMap) {
            clawServo = hardwareMap.servo.get("claw");
        }
        public void clawOpen(@NonNull HardwareMap hardwareMap){
            clawServo.setPosition(0.5);
        }
        public void clawGrab(){
            clawServo.setPosition(1.0);
        }
    }
}
