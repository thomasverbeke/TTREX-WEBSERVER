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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletContextEvent;
import java.net.*;
import java.io.*;

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
		
		Socket raspSocket = null;
		//PrintWriter out = null;
		BufferedReader in = null;
		
		//we use readers and writers so we can write Unicode characters over the sockets
		try {
			raspSocket = new Socket("raspberry",7); //IP , port number
			//out = new PrintWriter(raspSocket.getOutputStream(),true);
			in = new BufferedReader (new InputStreamReader(raspSocket.getInputStream()));
			
		} catch (UnknownHostException e) {
			System.err.println("Host unknown");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("IO Exception");
			System.exit(1);
			
		}
		
		while (in.readLine() != "exit"){
			//how does the frame look like?
			
		}
		
		//close stream & socket
		in.close();
		raspSocket.close();	
	}
	
	public void run() {
		//read from socket 
		while (true) {
            try {
            	
            	//in the test mode we define 3 runners
            	//they are all moving at different speeds
            	int one=0, two=0, three=0;
            	while(true){
            		
            		one = (one%1000) + 10;
            		two = (two%1000) + 12;
            		three = (three%1000) + 15;
            		
            		Thread.sleep(1500);
            		//for testing we will send and update for 3 runners
            		ArrayList runnerOne = new ArrayList(); 
            		
            		runnerOne.add("runnerFrame"); //FrameType
            		runnerOne.add(0); //ID
            		runnerOne.add(one); //Position
            		
            		readQueue.add(runnerOne);
            		
            		ArrayList runnerTwo = new ArrayList(); 
            		
            		runnerTwo.add("runnerFrame"); //FrameType
            		runnerTwo.add(1); //ID
            		runnerTwo.add(two); //Position
            		
            		readQueue.add(runnerTwo);
            		
            		ArrayList runnerThree = new ArrayList(); 
            		
            		runnerThree.add("runnerFrame"); //FrameType
            		runnerThree.add(2); //ID
            		runnerThree.add(three); //Position
            		
            		readQueue.add(runnerThree);
            		
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
