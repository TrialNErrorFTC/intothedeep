package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class RobotHardware {

    public BNO055IMU imu;
    public DriveTrain driveTrain;
    public DcMotor frontLeft; // Alias for easier access

    public static final double WHEEL_DIAMETER = 6.0;
    public static final double DRIVE_MOTOR_TICKS_PER_ROTATION = 385.5; //changing from 537.6

    public static int HANG_ADJ_POS = 0;
    private static final int HANG_INIT_POS = 0;
    private static final int HANG_TOP_POS = -35000;
    private static final int HANG_GRAB_POS = 250;

    private HardwareMap hardwareMap;

    public RobotHardware(LinearOpMode opMode) {
        this.hardwareMap = opMode.hardwareMap;
        
        // Initialize IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        
        // Initialize drive train
        driveTrain = new DriveTrain(hardwareMap);
        frontLeft = driveTrain.motorFL; // Create alias for AutoCommon compatibility
    }

    // Methods that AutoCommon expects
    public void startMove(double drive, double strafe, double turn, double scale) {
        driveTrain.startMove(drive, strafe, turn, scale);
    }

    public void stopMove() {
        driveTrain.stopMove();
    }

    public void resetDriveEncoders() {
        driveTrain.resetDriveEncoders();
    }

    public class DriveTrain {
        public DcMotor motorFL;
        public DcMotor motorFR;
        public DcMotor motorBL;
        public DcMotor motorBR;
        public DcMotor[] motors;

        public DriveTrain(HardwareMap hardwareMap) {
            motorFL = hardwareMap.get(DcMotor.class, "leftFront");
            motorFR = hardwareMap.get(DcMotor.class, "rightFront");
            motorBL = hardwareMap.get(DcMotor.class, "leftBack");
            motorBR = hardwareMap.get(DcMotor.class, "rightBack");
            motors = new DcMotor[]{motorFL, motorFR, motorBL, motorBR};

            motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
            motorBL.setDirection(DcMotorSimple.Direction.REVERSE);
            motorFR.setDirection(DcMotorSimple.Direction.FORWARD);
            motorBR.setDirection(DcMotorSimple.Direction.FORWARD);

            for (DcMotor motor : motors) {
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }

        public void startMove(double drive, double strafe, double turn, double scale) {
            double powerFL = (drive + strafe + turn) * scale;
            double powerFR = (drive - strafe - turn) * scale;
            double powerBL = (drive - strafe + turn) * scale;
            double powerBR = (drive + strafe - turn) * scale;

            double maxPower = Math.max(Math.max(Math.abs(powerFL), Math.abs(powerFR)), Math.max(Math.abs(powerBL), Math.abs(powerBR)));
            double max = (maxPower < 1) ? 1 : maxPower;

            motorFL.setPower(Range.clip(powerFL / max, -1, 1));
            motorFR.setPower(Range.clip(powerFR / max, -1, 1));
            motorBL.setPower(Range.clip(powerBL / max, -1, 1));
            motorBR.setPower(Range.clip(powerBR / max, -1, 1));
        }

        public void resetDriveEncoders() {
            motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public void stopMove() {
            for (DcMotor motor : motors) {
                motor.setPower(0);
            }
        }
    }
}