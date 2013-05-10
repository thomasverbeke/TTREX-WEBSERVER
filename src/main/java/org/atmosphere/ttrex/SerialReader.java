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
		
		try {
			System.out.print("Connecting...");
			raspSocket = new Socket("25.149.89.217",5999); //IP , port number
			str = new ObjectInputStream(raspSocket.getInputStream());
			System.out.println("OK");
		} catch (UnknownHostException e) {
			System.err.println("Host unknown");
			System.out.println(e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("IO Exception");
			System.out.println(e);
			System.exit(1);		
		}
			
	}
	
	public void run() {
		//read from socket 
		while (true) {
  
			try {
				
				if (raspSocket.isConnected()){
									
					TrackEvent object = (TrackEvent) str.readObject();
					
					System.out.println(object.getID()+":"+object.getRunnerId()+":"+object.getPercentage());
				
					ArrayList runnerOne = new ArrayList(); 
	    		
	        		runnerOne.add("runnerFrame"); //FrameType
	        		runnerOne.add(object.getRunnerId()); //ID
	        		runnerOne.add(object.getPercentage()); //Position
	        		
	        		readQueue.add(runnerOne);
				} else if (raspSocket.isClosed()){
					System.out.println("Socket closed");
					System.exit(1);
				} else {
					System.out.println("Socket closed!");
					System.exit(1);
				}
			} catch(Exception e){
	
				e.printStackTrace();
			}
				
        }
	}
	
}
