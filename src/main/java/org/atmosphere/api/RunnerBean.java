package org.atmosphere.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RunnerBean {
	
	public int runner_id;
	public double percentage;
      
	public double longitude;
	public double latitude;
	public int rounds = 0;
	public double speed =0;
	
	public RunnerBean() {} // JAXB needs this
 
	public RunnerBean(int runner_id, double percentage,double latitude, double longitude, int rounds, double speed) {
		this.runner_id = runner_id;
		this.percentage = percentage;
		this.longitude = longitude;
		this.latitude = latitude;
		this.rounds = rounds;
		this.speed = speed;
   }
	
}
