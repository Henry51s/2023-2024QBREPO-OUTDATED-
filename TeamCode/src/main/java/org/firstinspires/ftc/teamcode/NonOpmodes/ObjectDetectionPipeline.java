package org.firstinspires.ftc.teamcode.NonOpmodes;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ObjectDetectionPipeline extends OpenCvPipeline {

    //Lower and upper bounds for yellow HSV values
    int lowH = 20;
    int lowS = 70;
    int lowV = 80;
    int highH = 32;
    int highS = 255;
    int highV = 255;

    int strictLowH = 0;
    int strictLowS = 150;
    int strictLowV = 100;
    int strictHighH = 255;
    int strictHighS = 255;
    int strictHighV = 255;

    Mat mat = new Mat();
    Mat thresh = new Mat(); //Filtered matrix
    Mat masked = new Mat();
    Mat scaledMask = new Mat();
    Mat scaledThresh = new Mat();
    Mat finalMask = new Mat();
    Mat edges = new Mat();

    Scalar lower = new Scalar(lowH,lowS,lowV); //Lower bound for yellow
    Scalar upper = new Scalar(highH,highS,highV); //Upper bound for yellow
    Scalar strictLower = new Scalar(strictLowH, strictLowS, strictLowV);
    Scalar strictUpper = new Scalar(strictHighH, strictHighS, strictHighV);
    Scalar average;

    @Override
    public Mat processFrame(Mat input) {


        //Convert mat to HSV
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        Core.inRange(mat,lower,upper,thresh);
        Core.bitwise_and(mat, mat, masked, thresh);

        average = Core.mean(masked, thresh);

        masked.convertTo(scaledMask,-1,150/average.val[1],0);

        Core.inRange(scaledMask, strictLower, strictUpper, scaledThresh);

        Core.bitwise_and(mat, mat, finalMask, scaledThresh);

        Imgproc.Canny(finalMask, edges, 100, 200);

        input.release();
        scaledThresh.copyTo(input);
        scaledThresh.release();
        scaledMask.release();
        mat.release();
        masked.release();
        edges.release();
        thresh.release();
        finalMask.release();

        return input;
    }
}
