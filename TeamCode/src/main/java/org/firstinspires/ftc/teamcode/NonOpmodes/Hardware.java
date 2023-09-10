package org.firstinspires.ftc.teamcode.NonOpmodes;



import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.*;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines.CopiedPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;


public class Hardware{

    //Webcam---------------------
    public OpenCvCamera webcam;
    //public ObjectDetectionPipeline pipeline = new ObjectDetectionPipeline();
    public CopiedPipeline pipeline = new CopiedPipeline();

    //---------------------------

    //Robot Hardware-------------
    public DcMotor motor1 = null;
    public PIDMotor customMotor = null;
    public Servo servo1 = null;

    String MOTOR_1 = "motor1";
    //---------------------------


    public void initHardware(HardwareMap hardwareMap){


        // ------------------------------Webcam------------------------------------------------
        int cameraMonitorId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorId);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                webcam.setPipeline(pipeline);
                webcam.startStreaming(xDim, yDim, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        //--------------------------------------------------------------------------------------



        //-----------------Hub Bulk Read-------------------
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        //--------------------------------------------------------------



        //---------------------Robot Hardware---------------------------
        customMotor = new PIDMotor(hardwareMap, MOTOR_1);

        servo1 = hardwareMap.get(Servo.class, "servo1");
        //--------------------------------------------------------------
    }
}

