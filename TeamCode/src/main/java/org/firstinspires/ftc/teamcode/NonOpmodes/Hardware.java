package org.firstinspires.ftc.teamcode.NonOpmodes;



import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;


public class Hardware{

    //Webcam---------------------
    public CVMaster webcam = new CVMaster();
    //---------------------------

    //Robot Hardware-------------
    public DcMotor frontLeft, frontRight, backLeft, backRight;

    //Motor wires are labelled
    String MOTOR_0 = "motor0"; //Front Left
    String MOTOR_1 = "motor1"; //Front Right
    String MOTOR_2 = "motor2"; //Back Left
    String MOTOR_3 = "motor3"; //Back Right

    String WEBCAM = "webcam";
    //---------------------------


    public void initDrive(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, MOTOR_0);
        frontRight = hardwareMap.get(DcMotor.class, MOTOR_1);
        backLeft = hardwareMap.get(DcMotor.class, MOTOR_2);
        backRight = hardwareMap.get(DcMotor.class, MOTOR_3);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void initIntake(HardwareMap hardwareMap){
        //Insert code to init intake motor + anything else
    }
    public void initPickup(HardwareMap hardwareMap){
        //Insert code to init pickup hardware
    }
    public void initSensors(HardwareMap hardwareMap){
        //Insert code to init sensors

    }
    public void initRobot(HardwareMap hardwareMap){


        // ------------------------------Webcam------------------------------------------------
        webcam.initCamera(hardwareMap, WEBCAM);
        //--------------------------------------------------------------------------------------



        //-----------------Hub Bulk Read-------------------
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        //--------------------------------------------------------------



        //---------------------Robot Hardware---------------------------
        initDrive(hardwareMap);
        initIntake(hardwareMap);
        initPickup(hardwareMap);
        //--------------------------------------------------------------
    }
}

