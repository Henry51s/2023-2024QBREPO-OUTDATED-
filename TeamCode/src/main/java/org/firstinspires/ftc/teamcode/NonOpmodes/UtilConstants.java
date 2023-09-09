package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.dashboard.config.Config;

@Config
public class UtilConstants {

    //TO-DO: List config names of motors, servos, camera

    //Vision Constants-------------

    //Webcam Resolution
    public static final int xWidth = 800;
    public static final int yWidth = 448;

    public static final int dashboardStreamFps = 5;

    //Lower bound HSV values for desired color
    public static final int lowerH = 20;
    public static final int lowerS = 70;
    public static final int lowerV = 80;

    //Upper bound HSV values for desired color
    public static final int upperH = 32;
    public static final int upperS = 255;
    public static final int upperV = 255;
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
