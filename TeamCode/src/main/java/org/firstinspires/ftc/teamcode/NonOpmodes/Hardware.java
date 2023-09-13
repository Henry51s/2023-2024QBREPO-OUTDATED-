package org.firstinspires.ftc.teamcode.NonOpmodes;



import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    public DcMotor frontLeft, frontRight, backLeft, backRight;

    //Motor wires are labelled
    String MOTOR_0 = "motor0"; //Front Left
    String MOTOR_1 = "motor1"; //Front Right
    String MOTOR_2 = "motor2"; //Back Left
    String MOTOR_3 = "motor3"; //Back Right
    //---------------------------


    public void initHardware(HardwareMap hardwareMap){


        // ------------------------------Webcam------------------------------------------------
        /*int cameraMonitorId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",hardwareMap.appContext.getPackageName());

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
        */
        //--------------------------------------------------------------------------------------



        //-----------------Hub Bulk Read-------------------
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        //--------------------------------------------------------------



        //---------------------Robot Hardware---------------------------
        frontLeft = hardwareMap.get(DcMotor.class, MOTOR_0);
        frontRight = hardwareMap.get(DcMotor.class, MOTOR_1);
        backLeft = hardwareMap.get(DcMotor.class, MOTOR_2);
        backRight = hardwareMap.get(DcMotor.class, MOTOR_3);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);


        //--------------------------------------------------------------
    }
}

