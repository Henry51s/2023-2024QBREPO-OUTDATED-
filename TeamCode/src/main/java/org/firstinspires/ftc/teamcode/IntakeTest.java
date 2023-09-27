package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="IntakeTest")
public class IntakeTest extends OpMode {

    DcMotor intakeMotor;
    Hardware hardware = new Hardware();

    @Override
    public void init() {
        hardware.initIntake(hardwareMap);
        intakeMotor = hardware.intake;
    }

    @Override
    public void loop() {
        intakeMotor.setPower(gamepad1.left_stick_y);

    }
}
