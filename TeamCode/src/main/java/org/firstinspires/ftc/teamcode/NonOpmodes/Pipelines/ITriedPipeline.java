package org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvPipeline;

public class ITriedPipeline extends OpenCvPipeline {

    Mat hsvImage = new Mat();
    Mat mask = new Mat();

    Scalar lower = new Scalar(20, 70, 80); // HSV threshold bounds
    Scalar upper = new Scalar(32, 255, 255);

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsvImage,lower,upper,mask);

        Moments moments = Imgproc.moments(mask, true);
        double m00 = moments.m00; //Measure of the object's area
        double m10 = moments.m10; //Measure of the object's horizontal information
        double m01 = moments.m01; //Measure of the object's vertical information

        double xPos = (int) m10 / m00;
        double yPos = (int) m01 / m00;

        return null;
    }
}
