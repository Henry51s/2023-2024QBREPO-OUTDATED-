package org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvPipeline;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


public class ITriedPipeline extends OpenCvPipeline {
    /*
          (0,0) ----------- (Width, 0)
            |                 |
            |                 |
            |                 |
            |                 |
            |                 |
            |                 |
            (0, Height) ----- (Width, Height)
    */
    //Dimensions for region of interest rectangle
    int x1 = 0;
    int y1 = 0;
    int w = 200;
    int h = 448;

    //Lower bound HSV values for desired color
    int lowerH = 73;
    int lowerS = 160;
    int lowerV = 59;

    //Upper bound HSV values for desired color
    int upperH = 255;
    int upperS = 188;
    int upperV = 98;

    public Scalar lower = new Scalar(lowerH, lowerS, lowerV);
    public Scalar upper = new Scalar(upperH, upperS, upperV);

    double xOffset = 0;
    double yOffset = 0;

    Mat ycrcbImage = new Mat();
    Mat mask = new Mat();
    Mat hierarchy = new Mat();
    Mat roi;

    List<MatOfPoint> contours = new ArrayList<>();

    Rect boundingBox;
    public Rect regionOfInterest = new Rect(x1,y1,w,h);

    //Code for EOCV Sim
    Telemetry telemetry;
    public ITriedPipeline(Telemetry telemetry){
        this.telemetry = telemetry;
    }


    public double getX(){
        return xOffset;
    }
    public double getY(){
        return yOffset;
    }

    @Override
    public Mat processFrame(Mat input) {
        /*
        1. Crop input image
        2. Convert cropped image into yCrCb color space
        3. Find the contours of the cropped image, put that info into a new "mask" mat
        4. Clone the mask to another mat
        5. Create a bounding box around the contour
        6. Output bounding box x and y position
        7. Profit
        */

        //Crops image
        roi = new Mat(input, regionOfInterest);

        //Converts image to yCrCb color space
        Imgproc.cvtColor(roi, ycrcbImage, Imgproc.COLOR_RGB2YCrCb);

        //Creates mask using given lower and upper bounds of color
        Core.inRange(ycrcbImage,lower,upper,mask);

        //Finds and draws contours around objects
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(mask, contours, -1, new Scalar(25,165,0), 2);

        //Creates a rectangle around each object
        for (MatOfPoint contours : contours){
            boundingBox = Imgproc.boundingRect(contours);
        }

        //Draws rectangle on mask to be displayed
        Imgproc.rectangle(mask, boundingBox, new Scalar(25,165,0), 2);

        xOffset = boundingBox.x + (0.5* boundingBox.width);
        yOffset = boundingBox.y + (0.5* boundingBox.height);

        telemetry.addData("xOffset: ", xOffset);
        telemetry.addData("yOffset: ", yOffset);
        telemetry.update();

        input.release();
        roi.release();
        ycrcbImage.release();
        return mask;
    }
}
