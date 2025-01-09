package org.firstinspires.ftc.teamcode.pipeline;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class RedProcessor implements VisionProcessor {

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // Not useful in this case, but we do need to implement it either way
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2HSV);

        // Define the lower and upper bounds for the red color range
        Scalar lowerRed1 = new Scalar(0, 100, 100);
        Scalar upperRed1 = new Scalar(10, 255, 255);
        Scalar lowerRed2 = new Scalar(160, 100, 100);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        // Create masks for the red color ranges
        Mat mask1 = new Mat();
        Mat mask2 = new Mat();
        Core.inRange(frame, lowerRed1, upperRed1, mask1);
        Core.inRange(frame, lowerRed2, upperRed2, mask2);

        // Combine the masks
        Core.addWeighted(mask1, 1.0, mask2, 1.0, 0.0, frame);

        // Fill in the holes
        Imgproc.dilate(frame, frame, new Mat());
        Imgproc.erode(frame, frame, new Mat());

        // Find the rectangles
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(frame, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        // Draw the rectangles
        Imgproc.drawContours(frame, contours, -1, new Scalar(255, 0, 0), 2);

        return frame;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        // Not useful either
    }

}

