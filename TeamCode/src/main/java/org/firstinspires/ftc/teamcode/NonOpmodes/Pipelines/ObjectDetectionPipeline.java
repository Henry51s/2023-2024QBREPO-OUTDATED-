package org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.*;

import java.util.ArrayList;
import java.util.List;

@Config
public class ObjectDetectionPipeline extends OpenCvPipeline {
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





    Mat ycbcrImage = new Mat();
    Mat mask = new Mat();
    Mat hierarchy = new Mat();


    List<MatOfPoint> contours = new ArrayList<>();

    Rect boundingBox = new Rect();
    public Rect regionOfInterest = new Rect(x1,y1,w,h);

    double xOffset = 0;
    double yOffset = 0;



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
        Scalar lower = new Scalar(lowerY, lowerCb, lowerCr);
        Scalar upper = new Scalar(upperY, upperCb, upperCr);
        if (input != null) {
            //Crops image
            Mat roi = new Mat(input, regionOfInterest);

            //Converts image to yCrCb color space
            Imgproc.cvtColor(roi, ycbcrImage, Imgproc.COLOR_RGB2YCrCb);

            //Creates mask using given lower and upper bounds of color
            Core.inRange(ycbcrImage, lower, upper, mask);

            //Finds and draws contours around objects
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            Imgproc.drawContours(mask, contours, -1, new Scalar(25, 165, 0), 2);

            //Creates a rectangle around each object
            for (MatOfPoint contours : contours) {
                boundingBox = Imgproc.boundingRect(contours);
            }

            //Draws rectangle on mask to be displayed
            Imgproc.rectangle(mask, boundingBox, new Scalar(25, 165, 0), 2);

            xOffset = boundingBox.x;
            yOffset = boundingBox.y;

            roi.release();
            ycbcrImage.release();
            contours.clear();
            return mask;
        }
        return input;
    }

    public double getX(){
        return xOffset;
    }
    public double getY(){
        return yOffset;
    }


}
