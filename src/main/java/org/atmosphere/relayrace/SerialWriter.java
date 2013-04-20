package org.atmosphere.coptermotion;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.servlet.ServletContextEvent;
import org.atmosphere.cpr.MetaBroadcaster;
import org.atmosphere.datatypes.Waypoint_t;
import org.atmosphere.datatypes.u16;
import org.atmosphere.datatypes.u8;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SerialWriter implements Runnable {

	Encode encoder;
	BlockingQueue<ArrayList> writeQueue = new LinkedBlockingQueue<ArrayList>();
	boolean testmode = false;
	public SerialWriter (OutputStream writer,ServletContextEvent event){
		writeQueue = (BlockingQueue<ArrayList>) event.getServletContext().getAttribute("writeQueue");
		//writer could be null in testmode
		if(writer!=null){
			encoder = new Encode(writer);
		} else {
			testmode = true;
		}
		
	}
	public void run() {
		//blocking writer thread
		ArrayList item;
		while(true){
			try {	
				item = writeQueue.take();	
				
				//ignore init
				if (!item.get(0).equals("init")){
					//System.out.println("Spilling the guts");
				  
				    String type = item.get(0).toString();
				    String data = item.get(1).toString();
					
				    switch (type) {
					    case "sendWP":
					    	Object obj = JSONValue.parse(data);
					    	JSONObject jsonObject = (JSONObject) obj;
					    	Object position = jsonObject.get("Position");
					    		
					    	
					    	JSONObject posObj = (JSONObject) position;
					    	Object Longitude = posObj.get("Longitude");
					    	Object Latitude = posObj.get("Latitude");
					    	Object Altitude = posObj.get("Altitude");
					    	Object Status = posObj.get("Status");
					    	
					    	Object Heading = jsonObject.get("Heading");
					    	Object ToleranceRadius = jsonObject.get("ToleranceRadius");
					    	Object HoldTime = jsonObject.get("HoldTime");
					    	Object Event_Flag = jsonObject.get("Event_Flag");
					    	
					    	Object Index = jsonObject.get("Index");
					    	Object Type = jsonObject.get("Type");
					    	
					    	Object WP_EventChannelValue = jsonObject.get("WP_EventChannelValue");
					    	Object AltitudeRate = jsonObject.get("AltitudeRate");
					    	
					    	Object Speed = jsonObject.get("Speed");
					    	Object CameraAngle = jsonObject.get("CameraAngle");
					    	
					    	//TODO Print all data first to check if all comes trough well!
					    	System.out.println();
					    	
					    	System.out.println("<SW>sendWP");
					    	if (testmode==false){
					    		/** (NAVI) SEND WAYPOINT **/
					    		
					    		Waypoint_t newWP = new Waypoint_t("new WP");
					    		newWP.Position.Longitude.value = 36827906;
					    		newWP.Position.Latitude.value = 510481055;
					    		newWP.Position.Altitude.value = 5038;
					    		newWP.Position.Status.value = 1;
					    		newWP.Heading.value = 0;
					    		newWP.ToleranceRadius.value = 10;
					    		newWP.HoldTime.value = 2;
					    		newWP.Event_Flag.value = 1;
					    		newWP.Index.value = 1;
					    		newWP.Type.value = 0;
					    		newWP.WP_EventChannelValue.value=100;
					    		newWP.AltitudeRate.value = 30;
					    		newWP.Speed.value = 30;
					    		newWP.CameraAngle.value = 0;
					    		encoder.send_command(2,'w',newWP.getAsInt());
					    	}

					    	break;
					    case "reqWP":
					    	System.out.println("<SW>reqWP");
					    	
					    	if (testmode==false){
					    		/** (NAVI)REQUEST WP **/
					    		u8 WP = new u8("New WP");
					    		//TODO TEST
					    		WP.value = Long.valueOf(data).longValue();				    		
					    		encoder.send_command(2,'x',WP.getAsInt());
					    	}
					    	break;
					    case "serialTest":
					    	System.out.println("<SW>serialTest");
					    	if (testmode==false){
					    		/** (NAVI) SERIAL LINK TEST **/
					    		
					    		u16 echo = new u16("echo");
					    		echo.value=Long.valueOf(data).longValue();
					    		encoder.send_command(2,'z',echo.getAsInt());
					    	}
					    	break;
					    case "3DDataInterval":
					    	System.out.println("<SW>3DDataInterval");
					    	if (testmode==false){
					    		/** (NAVI) SET 3D DATA INTERVAL**/
					    		u8 interval = new u8("interval");
					    		
					    		interval.value = Long.valueOf(data).longValue(); //PASSING 0 STOPS THE STREAM
					    		encoder.send_command(2,'c',interval.getAsInt());
					    	}
					    	break;
					    case "OSDDataInterval":
					    	System.out.println("<SW>OSDDataInterval");
					    	if (testmode==false){
					    		//TODO not tested
					    		/** (NAVI) SET 3D DATA INTERVAL**/
					    		u8 interval = new u8("interval");
					    		interval.value = Long.valueOf(data).longValue();
					    		encoder.send_command(2,'o',interval.getAsInt());
					    	}
					    	break;
					    case "EngineTest":
					    	System.out.println("<SW>EngineTest");
					    	if (testmode==false){
					    		//TODO Switch to flightctrl
					    		/** (FLIGHT) Engine Test **/
					    		int val = 0;
					    		
					    		if (Integer.parseInt(data) > 10 || Integer.parseInt(data) < 0 ){
					    			val = 10;
					    		} else {
					    			val = Integer.parseInt(data);
					    		}
					    		
					    		int motor[] = new int[16];
					    		motor[0] = val;
					    		motor[1] = val;
					    		motor[2] = val;
					    		motor[3] = val;
					    		motor[4] = val;
					    		motor[5] = val;
					    		encoder.send_command(1,'t',motor);
					    	}
					    	break;
					    case "DebugReqInterval":
					    	System.out.println("<SW>DebugReqInterval");
					    		if (testmode==false){
					    			//TODO Check if correct command or not
					    			/** Labels of Analog values request (for all 32 channels) **/
					    			encoder.send_command(0,'a',0);
					    		}
					    	break;
				    }
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		}		
	}
	
	public void commands(){	
		//first redirect UART
	    
        //Thread.sleep(1000L);
        //encoder.send_magic_packet();
        //Thread.sleep(1000L);
             
        
    	//--WORKS--    
 

        /** (NAVI) ERROR TEXT REQUEST**/
        //encoder.send_command(2,'e');
        
        /** (COMMON) Version Request **/ //DATA OK?
        //encoder.send_command(0,'v');
        
        /** (COMMON) Request Display h **/
        //encoder.send_command(0,'h',1000);
        
        /** (COMMON) l REQUEST DIPLAY **/
        //u8 menuItem = new u8("menuItem");
        //menuItem.value = 1; //from 1 to 19!!
        //encoder.send_command(0,'l',menuItem.getAsInt());
        
        
        /** (NAVI) BL CTRL Status: BLDataStruct for every motor**/
        //u8 interval = new u8("interval");
        //interval.value = 3000; 
        //encoder.send_command(2,'k',interval.getAsInt());
	}
}
