# Hardware Configuration Guide

## Required Hardware Configuration

This document outlines the hardware configuration needed for your FTC robot to work with the code in this project.

### Drive Train Motors
Configure these motors in the Robot Controller app:

| Motor Name | Hardware Name | Type | Direction |
|------------|---------------|------|-----------|
| `leftFront` | Front Left Drive Motor | DcMotor | REVERSE |
| `rightFront` | Front Right Drive Motor | DcMotor | FORWARD |
| `leftBack` | Back Left Drive Motor | DcMotor | REVERSE |
| `rightBack` | Back Right Drive Motor | DcMotor | FORWARD |

### Sensors
Configure these sensors in the Robot Controller app:

| Sensor Name | Hardware Name | Type | Notes |
|-------------|---------------|------|-------|
| `imu` | Inertial Measurement Unit | BNO055IMU | For heading control |

### Optional Hardware
Add these if your robot has additional mechanisms:

| Device Name | Hardware Name | Type | Purpose |
|-------------|---------------|------|---------|
| `webcam` | Webcam | Webcam | For AprilTag detection |
| `colorSensor` | Color Sensor | ColorSensor | For game element detection |
| `distanceSensor` | Distance Sensor | DistanceSensor | For obstacle avoidance |

## Motor Configuration Details

### Drive Train Setup
- **Motor Type**: REV HD Hex Motors (or equivalent)
- **Gear Ratio**: Adjust `DRIVE_MOTOR_TICKS_PER_ROTATION` in RobotHardware.java
- **Wheel Diameter**: Adjust `WHEEL_DIAMETER` in RobotHardware.java (currently set to 6.0 inches)

### Encoder Configuration
All drive motors should have encoders enabled:
- Mode: `RUN_USING_ENCODER`
- Zero Power Behavior: `BRAKE`

## IMU Configuration
- **Sensor**: REV Control Hub built-in IMU or external BNO055
- **Mounting**: Mount securely to minimize vibration
- **Calibration**: IMU will auto-calibrate on startup

## Troubleshooting

### Common Issues

1. **Motors not responding**
   - Check motor names match configuration
   - Verify motor directions
   - Check power connections

2. **Robot drives in wrong direction**
   - Adjust motor directions in `RobotHardware.java`
   - Check wheel mounting

3. **IMU not calibrating**
   - Ensure IMU is mounted securely
   - Keep robot still during initialization
   - Check I2C connections

4. **Encoders not working**
   - Verify encoder cables are connected
   - Check encoder mode settings
   - Reset encoders if needed

### Testing Procedure

1. **Basic Drive Test**
   - Run `Main TeleOp` OpMode
   - Test forward/backward movement
   - Test left/right strafing
   - Test rotation

2. **Autonomous Test**
   - Run `Auto Common Base` for basic movement
   - Run `Main Autonomous` for full sequence
   - Monitor telemetry for encoder values

3. **RoadRunner Test**
   - Run `BLUE Side RoadRunner Auto`
   - Check trajectory execution
   - Verify final position accuracy

## Configuration Files

### Robot Controller Configuration
Save your configuration as `IntoTheDeep_Config.xml` with these device names.

### Calibration Files
- IMU calibration will be saved automatically
- RoadRunner tuning parameters in `MecanumDrive.java`

## Next Steps

1. Configure hardware according to this guide
2. Test basic TeleOp functionality
3. Calibrate RoadRunner parameters if using advanced autonomous
4. Develop game-specific mechanisms and add to hardware configuration 