package org.firstinspires.ftc.teamcode.NonOpmodes;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.cameraOrientation;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.yResolution;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines.ObjectDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class CVMaster {
    OpenCvCamera camera;
    ObjectDetectionPipeline pipeline = new ObjectDetectionPipeline();

    public void initCamera(HardwareMap hardwareMap, String configName){
        int cameraMonitorId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, configName), cameraMonitorId);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                camera.setPipeline(pipeline);
                camera.startStreaming(xResolution, yResolution, cameraOrientation);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }
}
