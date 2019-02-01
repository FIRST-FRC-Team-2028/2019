package com.phantommentalists;

import java.util.ArrayList;
import java.util.ListIterator;

import com.phantommentalists.CameraAlignment.Line;

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
    int count;
    Line initialization = new Line(0,0,0,0);
    Line highperm;
    Line secondhighperm;
    double highx = 0;
    double secondhighx = 0;
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

            if(jeff != 0)
            {
                System.out.println("henloo1");
                int x=0;
                while(true)
                {
                    sink.grabFrame(mat);
                    grip.process(mat);
                    int linecount = 0;
                    int count = 0;
                    double highest = 999999.0d;
                    double secondhighest = 0d;
                    Line hightemp = initialization;
                    Line secondhightemp = initialization;

                    // ArrayList<Line> lines = grip.filterLinesOutput();
                    // System.out.println(grip.filterLinesOutput());
                    Runtime.getRuntime().gc();
                    for(Line i : grip.findLinesOutput())
                    {
                        if(i.length()<Parameters.CAM_FILTER_LINES_MINIMUM_LENGTH)
                        {
                            continue;
                        }
                        if((i.angle() < 30 || i.angle() > 150) && (i.angle() < 210 || i.angle() > 330))
                        {
                            continue;
                        }

                        if(i.y2 < highest)
                        {
                            secondhightemp = hightemp;
                            hightemp = i;
                            secondhighest = highest;
                            highest = i.y2;
                        }

                        System.out.println("Line Length: " + i.length());
                        System.out.println("x1: " + i.x1);
                        System.out.println("y1: " + i.y1);
                        System.out.println("x2: " + i.x2);
                        System.out.println("y2: " + i.y2);
                        System.out.println("angle: " + i.angle());
                        System.out.println("================NEW LINE================");
                        linecount++;
                        count++;
                        
                    }
                    highperm = hightemp;
                    if(secondhightemp.x2 != 0)
                    {
                        secondhighperm = secondhightemp;
                    }
                    highx = highest;
                    secondhighx = secondhighest;
                    System.out.println("|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_| NEW FRAME |_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
                    count = grip.findLinesOutput().size();
                    System.out.println("Line count unfiltered: "+ grip.findLinesOutput().size());
                    System.out.println("Line count: " + linecount);
                }
            }else
            {
                SmartDashboard.putNumber("sink2", sink.grabFrame(mat));
                System.out.println("henlooooo2");
                System.out.println(sink.getError());
            }

    }
    
    public Line getLeft()
    {
        if(highperm.x2 > secondhighperm.x2)
        {
            return secondhighperm;
        }
        else
        {
            return highperm;
        }
    }
    public Line getRight()
    {
        if(highperm.x2 < secondhighperm.x2)
        {
            return secondhighperm;
        }
        else
        {
            return highperm;
        }
    }
    
    public int getSize()
    {
        return count;
    }
}