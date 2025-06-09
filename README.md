# FTC Into the Deep - Team Robot Code

This repository contains the robot code for our FTC team's Into the Deep (2024-2025) season robot.

## 🏗️ Project Structure

```
├── FtcRobotController/          # FTC Robot Controller module
├── TeamCode/                    # Team-specific code
│   ├── src/main/java/org/firstinspires/ftc/teamcode/
│   │   ├── TeleOpMain.java      # Main TeleOp OpMode
│   │   ├── AutonomousMain.java  # Main Autonomous OpMode
│   │   ├── BlueSideTestAuto.java # RoadRunner autonomous
│   │   ├── VisionTest.java      # AprilTag vision testing
│   │   ├── NonRRAuto/           # Non-RoadRunner autonomous
│   │   │   └── AutoCommon.java  # Common autonomous functions
│   │   └── testing/             # Testing and utility classes
│   │       ├── RobotHardware.java    # Hardware abstraction
│   │       └── GameMechanisms.java   # Game-specific mechanisms
├── HARDWARE_CONFIG.md           # Hardware configuration guide
└── README.md                   # This file
```

## 🛠️ Setup Instructions

### Prerequisites
- Android Studio Ladybug (2024.2) or later
- Java 8 or later
- Android SDK (automatically configured)

### Quick Start
1. Clone this repository
2. Open in Android Studio
3. Build the project: `./gradlew build`
4. Deploy to Robot Controller

### Hardware Configuration
See [HARDWARE_CONFIG.md](HARDWARE_CONFIG.md) for detailed hardware setup instructions.

## 🎮 OpModes

### TeleOp
- **TeleOpMain**: Primary driver control OpMode with mecanum drive
  - Gamepad 1: Drive controls (left stick = translation, right stick = rotation)
  - Gamepad 2: Mechanism controls
  - Features: Speed control, field-centric drive, slow mode

### Autonomous
- **AutonomousMain**: Basic autonomous routine
- **BlueSideTestAuto**: RoadRunner-based autonomous with advanced path following

### Testing
- **VisionTest**: AprilTag detection and vision processing

## 🔧 Key Features

### Hardware Abstraction
- **RobotHardware.java**: Centralized hardware management
- **GameMechanisms.java**: Into the Deep specific mechanisms
- Proper initialization and error handling

### Drive System
- Mecanum drive implementation
- Field-centric control option
- Speed limiting and control modes

### Vision System
- AprilTag detection for autonomous navigation
- Camera calibration support
- Vision-based positioning

### RoadRunner Integration
- Advanced path planning and following
- Trajectory generation
- Precise autonomous movement

## 📦 Dependencies

- **FTC SDK**: 10.1 (2024-2025 season)
- **RoadRunner**: 0.1.14
- **AprilTag**: Latest (included in FTC SDK)
- **OpenCV**: Latest (included in FTC SDK)

## 🚀 Building and Deployment

### Build Commands
```bash
# Build entire project
./gradlew build

# Build only TeamCode
./gradlew :TeamCode:assembleDebug

# Clean build
./gradlew clean build
```

### Deployment
1. Connect Robot Controller via USB or Wi-Fi
2. Build and deploy through Android Studio
3. Or use `adb install` with generated APK

## 🔍 Testing

### Self-Test Checklist
- [ ] All motors respond correctly
- [ ] Sensors provide valid readings
- [ ] Vision system detects AprilTags
- [ ] Autonomous paths execute properly
- [ ] TeleOp controls feel responsive

### Hardware Validation
- Use the Self-Inspect feature in the Robot Controller app
- Verify all devices are properly configured
- Check for any warnings or errors

## 🏆 Competition Readiness

This codebase includes:
- ✅ Robust error handling
- ✅ Comprehensive telemetry
- ✅ Multiple autonomous options
- ✅ Vision-based navigation
- ✅ Modular hardware abstraction
- ✅ Competition-tested drive code

## 🤝 Contributing

When making changes:
1. Test thoroughly on the robot
2. Update documentation as needed
3. Follow FTC programming best practices
4. Ensure code is competition-legal

## 📚 Resources

- [FTC Documentation](https://ftc-docs.firstinspires.org/)
- [RoadRunner Documentation](https://rr.brott.dev/)
- [Game Manual](https://www.firstinspires.org/resource-library/ftc/game-and-season-info)

## 📄 License

This project is licensed under the terms specified by FIRST Tech Challenge.

---

**Team**: [Your Team Number]  
**Season**: 2024-2025 Into the Deep  
**Last Updated**: January 2025

