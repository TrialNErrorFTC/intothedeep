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
    public DcMotor motorAngleF;
    public DcMotor motorAngleR;
    public DcMotor motorLiftF;
    public DcMotor motorLiftR;

    // claw
    public Claw claw;
    public Servo clawServo;


    public robotHardware(HardwareMap hardwareMap) {

        drivetrain = new Drivetrain(this.hardwareMap);
        lift = new Lift(this.hardwareMap);
        claw = new Claw(this.hardwareMap);
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
        public int findMaxDistance(){
            //constants
            int LENGTH_OF_CLAW = 1; //TODO: Find Length of Claw
            int INITIAL_OFFSET = 1; //TODO: find the length in which the offset is(be rough)
            //ticks of motor -> degree
            int degree = ticksToDegree(motorAngleF.getCurrentPosition());
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
            motorAngleF.setTargetPosition(findMaxDistance());
            motorAngleR.setTargetPosition(findMaxDistance());
        }
        public boolean withinCertifiedAngleLimit(int angle){
            //convert 90deg angle to ticks
            if (angle < 0 || angle > 134.475 * 4){ //angle less than 0, and greater than 134.475*4
                return false;
            }
            else{
                return true;
            }

        }
        public void retract(){
            //check if ticks == 0;
            if(motorLiftF.getCurrentPosition() == 0){
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
        //move arm to 90 deg
        //increase ticks to specific height
    }
    public void hang(){
        //set distance to 0 ticks
    }
    public void resetEncoders(){
        //reset all encoders
        motorAngleF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorAngleR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLiftF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLiftR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorAngleF.setTargetPosition(0);
        motorAngleR.setTargetPosition(0);
        motorLiftR.setTargetPosition(0);
        motorLiftF.setTargetPosition(0);


    }
    public void telemetryUpdate(Telemetry telemetry){

    }
}
