package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.arcrobotics.ftclib.controller.*;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
@Disabled
public class PIDSoftware{

    double kP;
    double kI;
    double kD;
    double kF;

    PIDFController pidf = new PIDFController(kP, kI, kD, kF);


    public void setCoefficients(double kP, double kI, double kD){
        pidf.setP(kP);
        pidf.setI(kI);
        pidf.setD(kD);
    }
    public void setCoefficients(double kP, double kI, double kD, double kF){
        pidf.setP(kP);
        pidf.setI(kI);
        pidf.setD(kD);
        pidf.setF(kF);
    }

    public double output(DcMotor motor, double target){
        double output = pidf.calculate(
                motor.getCurrentPosition(), target
        );
        return output;
    }


    public void runToPosition(DcMotor motor, double target){
        double currentPos = motor.getCurrentPosition();
        if (currentPos != target){
            motor.setPower(output(motor, target));
        }
    }

    public double getCurrentPosition(DcMotor motor){
        return motor.getCurrentPosition();
    }




}
