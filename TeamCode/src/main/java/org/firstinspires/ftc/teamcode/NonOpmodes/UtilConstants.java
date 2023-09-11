package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.dashboard.config.Config;

@Config
public class UtilConstants {

    //TO-DO: List config names of motors, servos, camera

    //Vision Constants-------------

    //Webcam Resolution
    public static final int xDim = 800;
    public static final int yDim = 448;

    public static final int dashboardStreamFps = 5;


    //Lower bound HSV values for desired color
    public static int lowerH = 73;
    public static int lowerS = 160;
    public static int lowerV = 59;

    //Upper bound HSV values for desired color
    public static int upperH = 255;
    public static int upperS = 188;
    public static int upperV = 98;

    //Dimensions for region of interest rectangle
    public static int x1 = 0;
    public static int y1 = 0;
    public static int w = 800;
    public static int h = 448;
    //-----------------------------

    //TestPlatform Constants
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;

    public static double TARGET_POS = 1000;
    public static double TARGET_POS_2 = 100;
    //-------------------------------------


}
