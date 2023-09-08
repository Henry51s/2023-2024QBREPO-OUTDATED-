package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.dashboard.config.Config;

@Config
public class UtilConstants {

    //TO-DO: List config names of motors, servos, camera

    //Vision Constants-------------
    public static final int xWidth = 800; //Webcam Resolution X
    public static final int yWidth = 448; //Webcam Resolution Y

    public static final int dashboardStreamFps = 5;

    public static final int lowerH = 20;
    public static final int lowerS = 70; //Lower bound HSV values for desired color
    public static final int lowerV = 80;

    public static final int upperH = 32;
    public static final int upperS = 255; //Upper bound HSV values for desired color
    public static final int upperV = 255;
    //-----------------------------

    //PID Constants for TestPlatform OpMode
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    //-------------------------------------

    public static double TARGET_POS = 1000;
    public static double TARGET_POS_2 = 100;
}
