package org.atmosphere.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RunnerBean {
	private int runner_id;
	private double percentage;
	
	private double longitude;
	private double latitude;
	private int rounds = 0;
	private double speed =0;
	
	public RunnerBean() {} // JAXB needs this
	
	public RunnerBean(int id, double percentage,double lon,double lat,int rounds,double speed){
		this.runner_id = id;
		this.percentage = percentage;
		this.longitude = lon;
		this.latitude = latitude;
		this.rounds = rounds;
		this.speed = speed;
	}
	
}
