package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.dashboardStreamFps;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.xDim;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.yDim;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines.ITriedPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pipelines.ObjectDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@TeleOp(name="WebcamDashboardTest")
public class WebcamDashboardTest extends OpMode {
    OpenCvCamera webcam;
    ObjectDetectionPipeline pipeline = new ObjectDetectionPipeline();

    FtcDashboard dashboard;
    TelemetryPacket packet = new TelemetryPacket();

    //To DO: Set static constants for dashboard
    public static int lowerH = 0;
    public static int lowerS = 0;
    public static int lowerV = 0;
    public static int upperH = 0;
    public static int upperS = 0;
    public static int upperV = 0;

    @Override
    public void init() {
        int cameraMonitorId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorId);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                try {
                    webcam.setPipeline(pipeline);
                }
                catch(Exception exception){
                    telemetry.addLine("Error!");
                }
                webcam.startStreaming(xDim, yDim, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
        dashboard.startCameraStream(webcam, dashboardStreamFps);


    }

    @Override
    public void loop() {


        packet.put("xPos: ", pipeline.getX());
        packet.put("yPos: ", pipeline.getY());
        dashboard.sendTelemetryPacket(packet);

        telemetry.addData("xPos: ", pipeline.getX());
        telemetry.addData("yPos: ", pipeline.getY());

    }
}
