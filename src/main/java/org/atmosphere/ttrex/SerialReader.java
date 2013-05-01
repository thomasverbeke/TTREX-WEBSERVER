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
import java.io.ObjectInputStream;
import java.net.Socket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletContextEvent;

import be.ttrax.raspi.frames.TrackEvent;

import java.net.*;
import java.io.*;

public class SerialReader implements Runnable {
	
	Thread readThread;

	private BlockingQueue<ArrayList> readQueue = new LinkedBlockingQueue<ArrayList>();

	BlockingQueue<ArrayList> writeQueue = new LinkedBlockingQueue<ArrayList>();
	ObjectInputStream str;
	Socket raspSocket;
	
	public SerialReader(ServletContextEvent event) throws Exception {
		ArrayList init = new ArrayList(); 
    	init.add("init");
		readQueue.add(init);
		
		event.getServletContext().setAttribute("readQueue", readQueue);	//add queue to the context	
		writeQueue = (BlockingQueue<ArrayList>) event.getServletContext().getAttribute("writeQueue");
		
		
		//we use readers and writers so we can write Unicode characters over the sockets
		try {
			System.out.println("Inside serialReader opening socket");
			raspSocket = new Socket("25.149.89.217",5999); //IP , port number
			str = new ObjectInputStream(raspSocket.getInputStream());
		
		} catch (UnknownHostException e) {
			System.err.println("Host unknown");
			//System.exit(1);
		} catch (IOException e) {
			System.err.println("IO Exception");
			System.exit(1);
			
		}
			
	}
	
	public void run() {
		//read from socket 
		while (true) {
  
			try {
				TrackEvent object = (TrackEvent) str.readObject();
				System.out.println(object.getID()+":"+object.getRunnerId()+":"+object.getPercentage());
			
				ArrayList runnerOne = new ArrayList(); 
    		
        		runnerOne.add("runnerFrame"); //FrameType
        		runnerOne.add(object.getRunnerId()); //ID
        		runnerOne.add(object.getPercentage()); //Position
        		
        		readQueue.add(runnerOne);
			} catch(Exception e){
	
				System.out.println(e.getMessage());
			}
				
				
			
				
//            		one = (one%1000) + 10;
//            		two = (two%1000) + 12;
//            		three = (three%1000) + 15;
//            		
//            		Thread.sleep(1500);
//            		//for testing we will send and update for 3 runners
//            		ArrayList runnerOne = new ArrayList(); 
//            		
//            		runnerOne.add("runnerFrame"); //FrameType
//            		runnerOne.add(0); //ID
//            		runnerOne.add(one); //Position
//            		
//            		readQueue.add(runnerOne);
//            		
//            		ArrayList runnerTwo = new ArrayList(); 
//            		
//            		runnerTwo.add("runnerFrame"); //FrameType
//            		runnerTwo.add(1); //ID
//            		runnerTwo.add(two); //Position
//            		
//            		readQueue.add(runnerTwo);
//            		
//            		ArrayList runnerThree = new ArrayList(); 
//            		
//            		runnerThree.add("runnerFrame"); //FrameType
//            		runnerThree.add(2); //ID
//            		runnerThree.add(three); //Position
//            		
//            		readQueue.add(runnerThree);
//            		
//            		//ArrayList infoFrame = new ArrayList(); 
//            		
//            		//infoFrame.add("infoFrame");
//            		//infoFrame.add(0);
//            		//infoFrame.add(0.22);
			
        }
	}
	
}
