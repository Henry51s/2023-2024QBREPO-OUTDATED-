package org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CopiedPipeline extends OpenCvPipeline {
    //Idk why this shit is here. I straight up copied it from a video. It doesnt work.
    private String location; // output

    public Scalar lower = new Scalar(20, 70, 80); // HSV threshold bounds
    public Scalar upper = new Scalar(32, 255, 255);

    private Mat hsvMat = new Mat(); // converted image
    private Mat binaryMat = new Mat(); // imamge analyzed after thresholding
    private Mat maskedInputMat = new Mat();

    // Rectangle regions to be scanned
    private Point topLeft1 = new Point(10, 0), bottomRight1 = new Point(40, 20);
    private Point topLeft2 = new Point(10, 0), bottomRight2 = new Point(40, 20);

    double w1, w2;


    @Override
    public Mat processFrame(Mat input) {
        // Convert from BGR to HSV
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsvMat, lower, upper, binaryMat);


        // Scan both rectangle regions, keeping track of how many
        // pixels meet the threshold value, indicated by the color white
        // in the binary image

        // process the pixel value for each rectangle  (255 = W, 0 = B)
        for (int i = (int) topLeft1.x; i <= bottomRight1.x; i++) {
            for (int j = (int) topLeft1.y; j <= bottomRight1.y; j++) {
                if (binaryMat.get(i, j)[0] == 255) {
                    w1++;
                }
            }
        }

        for (int i = (int) topLeft2.x; i <= bottomRight2.x; i++) {
            for (int j = (int) topLeft2.y; j <= bottomRight2.y; j++) {
                if (binaryMat.get(i, j)[0] == 255) {
                    w2++;
                }
            }
        }


        // Determine object location
        if (w1 > w2) {
            location = "1";
        } else if (w1 < w2) {
            location = "2";
        }

        return binaryMat;
    }

    public String getLocation() {
        return location;
    }
}
