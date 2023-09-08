package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.arcrobotics.ftclib.gamepad.*;
import com.qualcomm.robotcore.hardware.Gamepad;

public class UtilGamepad {

    private Gamepad gamepad;
    private GamepadEx gamepadEx;
    private ToggleButtonReader aToggle, bToggle, xToggle, yToggle;


    public void initGamepad(Gamepad gamepad){
        this.gamepad = gamepad;
        gamepadEx = new GamepadEx(gamepad);

        aToggle = new ToggleButtonReader(
                gamepadEx, GamepadKeys.Button.A
        );
        bToggle = new ToggleButtonReader(
                gamepadEx, GamepadKeys.Button.B
        );
        xToggle = new ToggleButtonReader(
                gamepadEx, GamepadKeys.Button.X
        );
        yToggle = new ToggleButtonReader(
                gamepadEx, GamepadKeys.Button.Y
        );

    }

    public boolean toggleA(){
        aToggle.readValue();
        if(aToggle.getState()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean toggleB(){
        bToggle.readValue();
        if(bToggle.getState()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean toggleX(){
        xToggle.readValue();
        if(xToggle.getState()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean toggleY(){
        yToggle.readValue();
        if(yToggle.getState()){
            return true;
        }
        else{
            return false;
        }
    }
}
