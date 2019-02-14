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
    private TapePipeline tape;
    CvSink sink;
    int count;
    Line initialization = new Line(0,0,0,0);
    com.phantommentalists.TapePipeline.Line initialization2 = new com.phantommentalists.TapePipeline.Line(0,0,0,0);
    Line highperm;
    Line secondhighperm;

    com.phantommentalists.TapePipeline.Line leftlineperm;
    com.phantommentalists.TapePipeline.Line rightlineperm;

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
            tape = new TapePipeline();
            double jeff = sink.grabFrame(mat,1);

            if(jeff != 0)
            {
                while(true)
                {
                    sink.grabFrame(mat);
                    // grip.process(mat);
                    tape.process(mat);
                    int linecount = 0;
                    int count = 0;
                    double highest = 999999.0d;
                    double secondhighest = 0d;
                    Line hightemp = initialization;
                    Line secondhightemp = initialization;
                    com.phantommentalists.TapePipeline.Line leftlinetemp = initialization2;
                    com.phantommentalists.TapePipeline.Line rightlinetemp = initialization2;
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
                        if((i.y2) < 20)
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
                        // System.out.println("Line Length: " + i.length());
                        // System.out.println("x1: " + i.x1);
                        // System.out.println("y1: " + i.y1);
                        // System.out.println("x2: " + i.x2);
                        // System.out.println("y2: " + i.y2);
                        // System.out.println("angle: " + i.angle());
                        // System.out.println("================NEW LINE================");
                        // linecount++;
                        // count++;
                    }

                    for(com.phantommentalists.TapePipeline.Line i : tape.findLinesOutput())
                    {
                        
                        if(Math.abs(i.angle()) < 35 || (Math.abs(i.angle()) > 137 && Math.abs(i.angle()) < 223)) 
                        {
                            continue;
                        }
                        int f = filtered(tape.findLinesOutput());
                        double angle = i.angle();
                        if (angle < 0 && angle >= -180)
                        {
                            angle += 180;
                        }
                        else if(angle < 0 && angle <= -180)
                        {
                            angle += 360;
                        }

                        if(leftlinetemp.x1 == initialization2.x1)
                        {
                            leftlinetemp = i;
                            rightlinetemp = i;
                        }

                        if(f <= 3)
                        {
                            if(angle < 90)
                            {
                                continue;
                            }
                            if(i.x1 < leftlinetemp.x1)
                            {
                                rightlinetemp = leftlinetemp;
                                leftlinetemp = i;
                            }
                        }
                        else if(f <= 5)
                        {
                            if(angle < 90)
                            {
                                continue;
                            }
                            if(i.x1 < leftlinetemp.x1)
                            {
                                rightlinetemp = leftlinetemp;
                                leftlinetemp = i;
                            }
                        }
                        else if(f <= 7)
                        {
                            if(angle < 90)
                            {
                                continue;
                            }
                            else if(i.x1 < leftlinetemp.x1)
                            {
                                rightlinetemp = leftlinetemp;
                                leftlinetemp = i;
                            }
                        }
                        else if(f <= 9)
                        {
                            if(angle < 90)
                            {
                                continue;
                            }
                            if(Math.abs(Parameters.CAM_WIDTH-i.x1) < Math.abs(Parameters.CAM_WIDTH-leftlinetemp.x1))
                            {
                                rightlinetemp = leftlinetemp;
                                leftlinetemp = i;
                            }
                        }

                        System.out.println("Line Length: " + i.length());
                        System.out.println("x1: " + i.x1 + "   x2: "+ i.x2);
                        System.out.println("y1: " + i.y1 + "   y2: " + i.y2);
                        System.out.println("angle: " + angle);
                        System.out.println("================NEW LINE================");
                        linecount++;
                        count++;
                    }

                    leftlineperm = leftlinetemp;
                    rightlineperm = rightlinetemp;

                    highperm = hightemp;
                    // if(secondhightemp.x2 != 0)
                    // {
                    //     secondhighperm = secondhightemp;
                    // }
                    highx = highest;
                    secondhighx = secondhighest;
                    System.out.println("|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_| NEW FRAME |_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
                    count = grip.findLinesOutput().size();
                    System.out.println("Line count unfiltered: "+ grip.findLinesOutput().size());
                    System.out.println("Line count: " + linecount);

                }
            }
            else
            {
                SmartDashboard.putNumber("sink2", sink.grabFrame(mat));
                System.out.println("henlooooo2");
                System.out.println(sink.getError());
            }

    }
    public int filtered(ArrayList<com.phantommentalists.TapePipeline.Line> fawef)
    {
        count = fawef.size();
        
        for(com.phantommentalists.TapePipeline.Line la : fawef)
        {
            if(Math.abs(la.angle()) < 35 || (Math.abs(la.angle()) > 137 && Math.abs(la.angle()) < 223)) 
            {
                count-=1;
                continue;
            }
        }
        return count;
    }
    public Line getLeftline()
    {
        return highperm;
    }
    public Line getRightline()
    {
        return secondhighperm;
    }
    public com.phantommentalists.TapePipeline.Line getLeft()
    {
        return leftlineperm;
    }
    public com.phantommentalists.TapePipeline.Line getRight()
    {
        return rightlineperm;
    }
    
    public int getSize()
    {
        return count;
    }
}