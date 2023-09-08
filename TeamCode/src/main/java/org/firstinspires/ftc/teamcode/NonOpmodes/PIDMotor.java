package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDMotor {

    private DcMotor motor;

    private double kP;
    private double kI;
    private double kD;
    private double kF;

    PIDFController pidf = new PIDFController(kP, kI, kD, kF);

    public PIDMotor(HardwareMap hardwareMap, String configName){
        this.motor = hardwareMap.get(DcMotor.class, configName);
    }

    public void setCoefficients(double kP, double kI, double kD, double kF){
        pidf.setPIDF(kP, kI, kD, kF);
    }

    public void setDirection(DcMotorSimple.Direction direction){
        motor.setDirection(direction);
    }

    public double getCurrentPosition(){
        return motor.getCurrentPosition();
    }

    public void setPower(double power){
        motor.setPower(power);
    }

    private double output(double target){
        double output = pidf.calculate(
                motor.getCurrentPosition(), target
        );
        return output;
    }

    public void runToPosition(double target){
        double currentPos = motor.getCurrentPosition();
        if (currentPos != target){
            motor.setPower(output(target));
        }
    }

}
