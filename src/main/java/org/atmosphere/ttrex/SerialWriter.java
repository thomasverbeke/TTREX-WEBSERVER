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
		ArrayList item;
		while(true){
			//decode message
			try {	
				item = writeQueue.take();
				if (!item.get(0).equals("init")){
					 String type = item.get(0).toString();
					 String data = item.get(1).toString();
						
					    switch (type) {
					    	case "wpArray":
					    		Object jsonObj = JSONValue.parse(data);
						    	JSONArray wplist = (JSONArray) jsonObj;
						    	//loop trough list
						    	JSONObject wp;
						    	break;
					    }
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		}		
	}
}
