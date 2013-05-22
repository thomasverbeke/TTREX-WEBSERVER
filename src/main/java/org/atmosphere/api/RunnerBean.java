package org.atmosphere.api;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="Runners")
@XmlRootElement
public class RunnerBean implements Serializable {

	//TODO Getters and setters needed?
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "runner_id")
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
