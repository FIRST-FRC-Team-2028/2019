package com.phantommentalists;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraThread
{

    private boolean done;

    private CameraAlignment grip;
    CvSink sink;



    CameraThread()
    {
        sink = CameraServer.getInstance().getVideo();

    }

    /**
     * 
     */
    public void run() {
        Mat mat = new Mat();

        if(sink.grabFrame(mat)!=0)
        {
            System.out.println("henloo1");
            while(true)
            {
                System.out.println("henlooooooooooooooo!11!!3?");
                grip.process(mat);
                System.out.println(grip.findLinesOutput().size());
            }
        }else
        {
            System.out.println("henlooooo2");
            System.out.println(sink.getError());
        }
        System.out.println("henlo?");
    }
    
}