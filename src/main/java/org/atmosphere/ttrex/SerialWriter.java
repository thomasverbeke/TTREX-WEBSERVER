package org.atmosphere.ttrex;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.servlet.ServletContextEvent;
import org.atmosphere.cpr.MetaBroadcaster;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SerialWriter implements Runnable {


	BlockingQueue<ArrayList> writeQueue = new LinkedBlockingQueue<ArrayList>();
	
	public SerialWriter (ServletContextEvent event){
		writeQueue = (BlockingQueue<ArrayList>) event.getServletContext().getAttribute("writeQueue");
		//writer could be null in testmode

	}
	public void run() {
		//blocking writer thread
		while(true){
			//decode message
			
		    
		    
		}		
	}
}
