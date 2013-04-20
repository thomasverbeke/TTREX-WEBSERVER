/** 
 * @author Thomas Verbeke
 * This class will start a new thread containing the serial reader.
 * Serial reader reads and decodes incoming MK frames. It puts the frames in a queue which is saved inside the servlet
 * context attribute.The servlet context attribute is notified (trough it's listener) when new data is available.
 * 
 * **/

package org.atmosphere.ttrex;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.*;

public class SerialComInit implements ServletContextListener {
	private ExecutorService executor;
	public int test;

	public void contextDestroyed(ServletContextEvent event) {
		executor.shutdown();
		
	}

	public void contextInitialized(final ServletContextEvent event) {	
		// start task
		
		try {
			Thread thread = new Thread(new SerialReader(event));
			thread.start();
		} catch (Exception e1) {
			System.out.println("Problem in SerialComInit");
			e1.printStackTrace();
		}
    }
}



