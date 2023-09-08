package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="DrivetrainTest")//test123
public class DrivetrainTest extends OpMode {

    DcMotor front_left, front_right, back_left, back_right;

    @Override
    public void init() {
        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        back_right = hardwareMap.get(DcMotor.class, "back_right");


    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        front_left.setPower(y + x + rx);
        back_left.setPower(y - x + rx);
        front_right.setPower(y - x - rx);
        back_right.setPower(y + x - rx);

    }
}
