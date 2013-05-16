package org.atmosphere.api;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatsBean {
	
	public ArrayList<runnerObj> list = new ArrayList <runnerObj>();
	
	public StatsBean() {
	
		runnerObj firstTeam = new runnerObj(0,"Industria");
		runnerObj secondTeam = new runnerObj(1,"KLA");
		
		list.add(firstTeam);
		list.add(secondTeam);
		
	} // JAXB needs this
 
	
	static class runnerObj {
		public int id;
		public String title;
		
		runnerObj(int id, String title){
			this.id = id;
			this.title = title;
		}
		
		runnerObj(){
			
		}
		
	}

}
