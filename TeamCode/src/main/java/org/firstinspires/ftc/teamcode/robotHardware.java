 package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;


 public class robotHardware{
    public Drivetrain drivetrain;

//    public Lift lift;
     public DcMotor frontLeft;
     public DcMotor frontRight;
     public DcMotor rearLeft;
     public DcMotor rearRight;
//     public DcMotor motorLift;


     private HardwareMap hardwareMap;
    public robotHardware(){

//        lift = new Lift(hardwareMap);
        //TODO: drivetrain\
        drivetrain = new Drivetrain(hardwareMap);
    }
//    public class Lift{
//         public Lift(HardwareMap hardwareMap){
//            motorLift = hardwareMap.get(DcMotor.class, "motor");
//        }
//    }
    public class Drivetrain{

        public Drivetrain(@NonNull HardwareMap hardwareMap){
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