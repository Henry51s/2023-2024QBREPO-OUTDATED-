package org.firstinspires.ftc.teamcode.NonOpmodes;


import com.acmerobotics.dashboard.FtcDashboard;

public class UtilDashboard {//Test

    FtcDashboard dashboard;

    public void initDashboard(){
        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(125);
    }
}
