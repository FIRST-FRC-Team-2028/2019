package com.phantommentalists;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraThread extends Thread
{

    private boolean done;

    private CameraAlignment grip;
    CvSink sink;
    int size;


    CameraThread()
    {
        sink = CameraServer.getInstance().getVideo();
    }

    /**
     * 
     */
    public void run() 
    {
            Mat mat = new Mat();
            grip = new CameraAlignment();
            double jeff = sink.grabFrame(mat,0.5);
            SmartDashboard.putNumber("sinkgrabframe", jeff);
            if(jeff != 0)
            {
                System.out.println("henloo1");
                while(true)
                {
                    sink.grabFrame(mat);
                    grip.process(mat);
                    Runtime.getRuntime().gc();
                    size = grip.findLinesOutput().size();
                    System.out.println(grip.findLinesOutput().size());
                }
            }else
            {
                SmartDashboard.putNumber("sink2", sink.grabFrame(mat));
                System.out.println("henlooooo2");
                System.out.println(sink.getError());
            }
            System.out.println("henlo?");
    }

    public int getSize()
    {
        return size;
    }
}