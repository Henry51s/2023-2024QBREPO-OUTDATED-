package org.firstinspires.ftc.teamcode.NonOpmodes;


import com.acmerobotics.dashboard.FtcDashboard;

public class UtilDashboard {

    FtcDashboard dashboard;

    public void initDashboard(){
        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(125);
    }
}
