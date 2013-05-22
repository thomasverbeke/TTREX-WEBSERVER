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

import org.atmosphere.api.RunnerBean;
import org.atmosphere.ext.xmlReader;
import org.atmosphere.ttrex.SerialWriter;
import org.xml.sax.SAXException;

import be.ttrax.raspi.frames.TrackEvent;

import java.net.*;
import java.io.*;

public class SerialReader implements Runnable {
	
	Thread readThread;

	private BlockingQueue<ArrayList> readQueue = new LinkedBlockingQueue<ArrayList>();
	BlockingQueue<ArrayList> writeQueue = new LinkedBlockingQueue<ArrayList>();
	Storage tmpStorage = new Storage();
	
	ObjectInputStream str;
	Socket raspSocket;
	ObjectOutputStream strOut;
	ServletContextEvent event; 
	public SerialReader(ServletContextEvent event) throws Exception {
		ArrayList init = new ArrayList(); 
    	init.add("init");
		readQueue.add(init);
		
		event.getServletContext().setAttribute("readQueue", readQueue);	//add queue to the context	
		event.getServletContext().setAttribute("storageClass", tmpStorage);	//add storage class to context
		writeQueue = (BlockingQueue<ArrayList>) event.getServletContext().getAttribute("writeQueue");
		 
		this.event = event;
		
	}	
	
	
	public void run() {
		//read from socket 

		while (true) {
  
			try {
				
				xmlReader reader = new xmlReader("http://ttrex.eu/groups.xml");
				
				System.out.print("Connecting...");
				raspSocket = new Socket("192.168.1.91",5999); //IP , port number
				str = new ObjectInputStream(raspSocket.getInputStream());
				strOut = new ObjectOutputStream(raspSocket.getOutputStream());
			
				Thread thread = new Thread(new SerialWriter(event,strOut));
				thread.start();
				
				System.out.println("OK. We are connected now");		
				
				
				
				while (true){
					if (raspSocket.isConnected()){		
						try {	
							
							//TODO Add information to the context
							
							
							TrackEvent object = (TrackEvent) str.readObject();
						
							System.out.println(object.getID()+":"+object.getRunnerId()+":"+object.getPercentage());
							
							ArrayList runnerOne = new ArrayList(); 
			    		
			        		runnerOne.add("runnerFrame"); //FrameType
			        		runnerOne.add(object.getRunnerId()); //ID
			        		runnerOne.add(object.getPercentage()); //Position
			        		runnerOne.add(object.getLatitude());
			        		runnerOne.add(object.getLongitude());
			        		runnerOne.add(object.getRounds());
			        		runnerOne.add(object.getSpeed());
			        		
			        		readQueue.add(runnerOne);
			        		
			        		//add to storage class
			        		tmpStorage.addRunner(object.getRunnerId(), object.getPercentage(), object.getLatitude(), object.getLatitude(), object.getRounds(), object.getSpeed());
			        		tmpStorage.addRunner(object.getRunnerId()+1, object.getPercentage(), object.getLatitude(), object.getLatitude(), object.getRounds()+4, object.getSpeed());
			        		tmpStorage.addRunner(object.getRunnerId()+2, object.getPercentage(), object.getLatitude(), object.getLatitude(), object.getRounds()+10, object.getSpeed());
			        		
						} catch (EOFException e){
							System.out.println("Rasp Pi disconnected.");
							
							break;
						} catch(Exception e){
							System.out.println("Something weird happened!");
							e.printStackTrace();
							break;
						}
					} else if (raspSocket.isClosed()){
						System.out.println("Socket closed. Trying to reconnect...");
						break;
					} else {
						System.out.println("Socket closed! Trying to reconnect...");
						break;
					}
				}
			} catch(UnknownHostException e){
				System.out.println("Unknown Host!");
				
			} catch (ConnectException e) {
				System.out.println("Unable to connect!");
				
			} catch (IOException e){
				System.out.println("IOException");
				e.printStackTrace();
			
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			try {
				Thread.sleep(5000);
				System.out.println("Trying to reconnect in 5s");
				
				ArrayList message = new ArrayList(); 
	    		
        		message.add("systemMessage"); //FrameType
        		message.add("raspi offline");
        		readQueue.add(message);
        		
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}
	
}
