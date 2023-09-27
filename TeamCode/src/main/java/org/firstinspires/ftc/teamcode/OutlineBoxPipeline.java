
package org.firstinspires.ftc.teamcode;

        import org.opencv.core.Core;
        import org.opencv.core.Mat;
        import org.opencv.core.MatOfPoint;
        import org.opencv.core.MatOfPoint2f;
        import org.opencv.core.Point;
        import org.opencv.core.Rect;
        import org.opencv.core.RotatedRect;
        import org.opencv.core.Scalar;
        import org.opencv.imgproc.Imgproc;
        import org.openftc.easyopencv.OpenCvPipeline;

        import java.util.ArrayList;
        import java.util.List;

//for dashboard
/*@Config*/
public class OutlineBoxPipeline extends OpenCvPipeline {

    //backlog of frames to average out to reduce noise
    ArrayList<double[]> frameList;
    //these are public static to be tuned in dashboard
    public static double strictLowS = 140;
    public static double strictHighS = 255;

    public final Scalar EVERY_BGR = new Scalar(255, 255, 255);
    public final Scalar YELLOW_BGR = new Scalar(0, 255, 255);
    public final Scalar RED_BGR = new Scalar(0, 0, 255);
    public final Scalar BLUE_BGR = new Scalar(255, 0, 0);
    public final Scalar GREEN_BGR = new Scalar(0, 255, 0);

    public OutlineBoxPipeline() {
        frameList = new ArrayList<>();
    }

    public double centerDetect;
    /*
     * Colors
     */
    static final Scalar TEAL = new Scalar(3, 148, 252);
    static final Scalar PURPLE = new Scalar(158, 52, 235);
    static final Scalar RED = new Scalar(255, 0, 0);
    static final Scalar GREEN = new Scalar(0, 255, 0);
    static final Scalar BLUE = new Scalar(0, 0, 255);
    public enum DetectColor {
        RED,
        BLUE,
        YELLOW
    }
    public DetectColor DETECTING = DetectColor.YELLOW;


    public int contoursCounter;
    private Mat getContours(Mat contoursFrame, Scalar boxColor) {
        Rect rectLargest = new Rect(0,0,1,1);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(contoursFrame, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        contoursCounter = contours.size();
        Imgproc.cvtColor(contoursFrame, contoursFrame, Imgproc.COLOR_GRAY2BGR);

        Imgproc.drawContours(contoursFrame, contours, -1, boxColor, 1); //Everything

        MatOfPoint2f         approxCurve = new MatOfPoint2f();
        //For each contour found
        for (int i=0; i<contours.size(); i++)
        {
            //Convert contours(i) from MatOfPoint to MatOfPoint2f
            MatOfPoint2f contour2f = new MatOfPoint2f( contours.get(i).toArray() );
            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint( approxCurve.toArray() );

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);
            // Keep only the largest rect
            if (rect.area() > rectLargest.area()) {
                rectLargest = rect;
            }
        }

        if (rectLargest.area() > 10) {
            centerDetect = rectLargest.x + rectLargest.width / 2;
            Point Center = new Point( centerDetect, rectLargest.y + rectLargest.height / 2);

            drawTagText(Center, String.format("Center %,.2f", centerDetect), contoursFrame);

            Imgproc.circle(contoursFrame, Center, 3, RED);
            // draw enclosing rectangle (all same color, but you could use variable i to make them unique)
            Imgproc.rectangle(contoursFrame, rectLargest.tl(), rectLargest.br(), RED, 2, 8, 0);
        }

        return contoursFrame;
    }

    @Override
    public Mat processFrame(Mat input) {

        //Rect rectCrop = new Rect(0, 240 , 640, 240);
        //Mat input= f_input.submat(rectCrop);

        Mat mat = new Mat();

        //mat turns into HSV value
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        if (mat.empty()) {
            return input;
        }
        Scalar lowHSV = new Scalar(20, 70, 80);
        Scalar highHSV = new Scalar(32, 255, 255);
        if (DETECTING == DetectColor.YELLOW) {
            // lenient bounds will filter out near yellow, this should filter out all near yellow things(tune this if needed)
            lowHSV = new Scalar(20, 70, 80); // lenient lower bound HSV for yellow
            highHSV = new Scalar(32, 255, 255); // lenient higher bound HSV for yellow
        } else
        if (DETECTING == DetectColor.RED) {
            lowHSV = new Scalar(0, 50, 70); // lenient lower bound HSV for red
            highHSV = new Scalar(9, 255, 255); // lenient higher bound HSV for red
        } else
        if (DETECTING == DetectColor.BLUE) {
            lowHSV = new Scalar(90, 50, 70); // lenient lower bound HSV for blue
            highHSV = new Scalar(128, 255, 255); // lenient higher bound HSV for blue
        }
////
//        color_dict_HSV = {'black': [[180, 255, 30], [0, 0, 0]],
//        'white': [[180, 18, 255], [0, 0, 231]],
//        'red1': [[180, 255, 255], [159, 50, 70]],
//        'red2': [[9, 255, 255], [0, 50, 70]],
//        'green': [[89, 255, 255], [36, 50, 70]],
//        'blue': [[128, 255, 255], [90, 50, 70]],
//        'yellow': [[35, 255, 255], [25, 50, 70]],
//        'purple': [[158, 255, 255], [129, 50, 70]],
//        'orange': [[24, 255, 255], [10, 50, 70]],
//        'gray': [[180, 18, 230], [0, 0, 40]]}


        Mat thresh = new Mat();

        // Get a black and white image of yellow objects
        Core.inRange(mat, lowHSV, highHSV, thresh);

        Mat masked = new Mat();
        //color the white portion of thresh in with HSV from mat
        //output into masked
        Core.bitwise_and(mat, mat, masked, thresh);
        //calculate average HSV values of the white thresh values
        Scalar average = Core.mean(masked, thresh);

        Mat scaledMask = new Mat();
        //scale the average saturation to 150
        masked.convertTo(scaledMask, -1, 150 / average.val[1], 0);


        Mat scaledThresh = new Mat();
        //you probably want to tune this
        Scalar strictLowHSV = new Scalar(0, strictLowS, 0); //strict lower bound HSV for yellow
        Scalar strictHighHSV = new Scalar(255, strictHighS, 255); //strict higher bound HSV for yellow
        //apply strict HSV filter onto scaledMask to get rid of any yellow other than pole
        Core.inRange(scaledMask, strictLowHSV, strictHighHSV, scaledThresh);

        Mat finalMask = new Mat();
        //color in scaledThresh with HSV, output into finalMask(only useful for showing result)(you can delete)
        Core.bitwise_and(mat, mat, finalMask, scaledThresh);

        Mat edges = new Mat();
        //detect edges(only useful for showing result)(you can delete)
        Imgproc.Canny(scaledThresh, edges, 100, 200);

        //contours, apply post processing to information
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        //find contours, input scaledThresh because it has hard edges
        Imgproc.findContours(scaledThresh, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        //list of frames to reduce inconsistency, not too many so that it is still real-time, change the number from 5 if you want
        if (frameList.size() > 5) {
            frameList.remove(0);
        }


        //release all the data
        input.release();
        scaledThresh.copyTo(input);
        scaledThresh.release();
        scaledMask.release();
        mat.release();
        masked.release();
        edges.release();
        thresh.release();
        finalMask.release();
        //change the return to whatever mat you want
        //for example, if I want to look at the lenient thresh:
        // return thresh;
        // note that you must not do thresh.release() if you want to return thresh
        // you also need to release the input if you return thresh(release as much as possible)

        getContours(input,YELLOW_BGR);

        return input;
    }


    static void drawTagText(Point center, String text, Mat mat)
    {
        Imgproc.putText(
                mat, // The buffer we're drawing on
                text, // The text we're drawing
                new Point( // The anchor point for the text
                        center.x-50,  // x anchor point
                        center.y+25), // y anchor point
                Imgproc.FONT_HERSHEY_PLAIN, // Font
                1, // Font size
                TEAL, // Font color
                1); // Font thickness
    }
}

