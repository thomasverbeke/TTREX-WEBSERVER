package org.atmosphere.ttrex;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.servlet.ServletContextEvent;
import org.atmosphere.cpr.MetaBroadcaster;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import be.ttrax.raspi.frames.TrackUpdate;
import be.ttrax.raspi.utilities.TtrexPosition;

public class SerialWriter implements Runnable {


	BlockingQueue<ArrayList> writeQueue = new LinkedBlockingQueue<ArrayList>();
	ObjectOutputStream strOut;
	
	public SerialWriter (ServletContextEvent event,ObjectOutputStream str){
		writeQueue = (BlockingQueue<ArrayList>) event.getServletContext().getAttribute("writeQueue");
		strOut = str;
		//writer could be null in testmode

	}
	public void run() {
		//blocking writer thread
		ArrayList item;
		while(true){
			//decode message
			try {	
				item = writeQueue.take();
				if (!item.get(0).equals("init")){
					 String type = item.get(0).toString();
					 String data = item.get(1).toString();
						
					 TrackUpdate list = new TrackUpdate();
					 
					    switch (type) {
					    	case "wpArray":
					    		Object jsonObj = JSONValue.parse(data);
						    	JSONArray wplist = (JSONArray) jsonObj;
						   
						    	JSONObject wp;
						    	
						    	for (int wpID=0; wpID<wplist.size();wpID++){
						    		wp = (JSONObject) wplist.get(wpID);
						    		Object Latitude = wp.get("Latitude");
						    		Object Longitude = wp.get("Longitude");
						    		
						    		list.addPosition((double)Longitude ,(double) Latitude);    	
						    	}
						    	
						    	try {
						    		strOut.writeObject(list);
						    		strOut.flush();
						    	} catch (IOException e){
						    		e.printStackTrace();
						    	}
					    }
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		}		
	}
}
