/** 
 * @author Thomas Verbeke
 * 
 * Read frames from socket (rasp-pi); decode & send them in an agreed format known to the web client.
 * 
 * Also provides a test mode (send continous positions for runners moving at different speeds which can be used 
 * for testing the webclient.
 * 
 **/

package org.atmosphere.ttrex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletContextEvent;
import gnu.io.*; //don't need this anymore

public class SerialReader implements Runnable {
	

	Thread readThread;

	private BlockingQueue<ArrayList> readQueue = new LinkedBlockingQueue<ArrayList>();
	ArrayList<String> list = new ArrayList<String>();

	BlockingQueue<ArrayList> writeQueue = new LinkedBlockingQueue<ArrayList>();

	public SerialReader(ServletContextEvent event) throws Exception {
		ArrayList init = new ArrayList(); 
    	init.add("init");
		readQueue.add(init);
		
		event.getServletContext().setAttribute("readQueue", readQueue);	//add queue to the context	
		writeQueue = (BlockingQueue<ArrayList>) event.getServletContext().getAttribute("writeQueue");
		
	}
	
	public void run() {
		//read from socket 
		while (true) {
            try {
            	Thread.sleep(3000);
            	for (int i=0; i<300; i++){
            		//for testing we will send and update for 3 runners
            		ArrayList runnerFrame = new ArrayList(); 
            		
            		runnerFrame.add("runnerFrame"); //FrameType
            		runnerFrame.add(0); //ID
            		runnerFrame.add(0.22); //Position
                	
            		readQueue.add(runnerFrame);
            		
            		//ArrayList infoFrame = new ArrayList(); 
            		
            		//infoFrame.add("infoFrame");
            		//infoFrame.add(0);
            		//infoFrame.add(0.22);
            	}
        	
            } catch (InterruptedException e) {
            	System.out.println("Interrupted Exception");
            }
        }
	}
}
