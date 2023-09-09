package org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvPipeline;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.*;

public class ITriedPipeline extends OpenCvPipeline {

    //Courtesy of ChatGPT
    Mat hsvImage = new Mat();
    Mat mask = new Mat();

    Scalar lower = new Scalar(lowerH, lowerS, lowerV); // HSV threshold bounds
    Scalar upper = new Scalar(upperH, upperS, upperV);

    double xOffset = 0;
    double yOffset = 0;
    double distance = 0;

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsvImage,lower,upper,mask);

        Moments moments = Imgproc.moments(mask, true);
        double m00 = moments.m00; //Measure of the object's area
        double m10 = moments.m10; //Measure of the object's horizontal information
        double m01 = moments.m01; //Measure of the object's vertical information

        xOffset = (int) m10 / m00;
        yOffset = (int) m01 / m00;
        distance = Math.sqrt(Math.pow(xOffset,2)+Math.pow(yOffset,2));


        return mask;
    }

    public double getX(){
        return xOffset;
    }
    public double getY(){
        return yOffset;
    }
    public double getDistance(){
        return distance;
    }
}
