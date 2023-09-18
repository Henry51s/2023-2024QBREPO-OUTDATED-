package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;
    Hardware hardware = new Hardware();

    @Override
    public void init() {

        hardware.initRobot(hardwareMap);
        frontLeft = hardware.frontLeft;
        frontRight = hardware.frontRight;
        backLeft = hardware.backLeft;
        backRight = hardware.backRight;

    }

    @Override
    public void loop() {


        double y = gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        frontLeft.setPower(y + x + rx);
        backLeft.setPower(y - x + rx);
        frontRight.setPower(y - x - rx);
        backRight.setPower(y + x - rx);

    }
}
