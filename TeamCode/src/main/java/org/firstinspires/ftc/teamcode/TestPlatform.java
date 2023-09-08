package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.NonOpmodes.CopiedPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.PIDMotor;
import org.firstinspires.ftc.teamcode.NonOpmodes.UtilGamepad;
import org.openftc.easyopencv.OpenCvCamera;


@TeleOp(name="TestPlatform")
public class TestPlatform extends OpMode {

    Hardware hardware = new Hardware();
    UtilGamepad gamepad = new UtilGamepad();

    OpenCvCamera webcam;
    PIDMotor customMotor;

    double targetPosition;

    FtcDashboard dashboard;
    TelemetryPacket packet = new TelemetryPacket();
    CopiedPipeline pipeline = new CopiedPipeline();

    @Override
    public void init() {
        //pipeline = new CopiedPipeline();
        gamepad.initGamepad(gamepad1);
        hardware.initHardware(hardwareMap);

        customMotor = hardware.customMotor;
        webcam = hardware.webcam;

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
        dashboard.startCameraStream((CameraStreamSource) webcam, 5);

    }

    @Override
    public void loop() {
        /*telemetry.addData("Toggle State A: ", gamepad.toggleA());
        telemetry.addData("Toggle State B: ", gamepad.toggleB());
        telemetry.addData("Toggle State X: ", gamepad.toggleX());
        telemetry.addData("Toggle State Y: ", gamepad.toggleY());
        */
        telemetry.addData("Location", pipeline.getLocation());



        customMotor.setCoefficients(kP, kI, kD, kF);

        if(gamepad.toggleA()){
            customMotor.runToPosition(TARGET_POS);
            targetPosition = TARGET_POS;

        }
        else if (!gamepad.toggleA()){
            customMotor.runToPosition(TARGET_POS_2);
            targetPosition = TARGET_POS_2;
        }


        packet.put("Pos: ", customMotor.getCurrentPosition());
        packet.put("Target: ", targetPosition);
        packet.put("Error: ", customMotor.getCurrentPosition() - targetPosition);

        dashboard.sendTelemetryPacket(packet);
    }

}

