package org.atmosphere.api;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatsBean {
	
	public ArrayList<runnerObj> statList = new ArrayList <runnerObj>();
	
	public StatsBean() {
	
		//Hardcode some teams
		addTeam(0,"Industria",0);
		addTeam(1,"KLA",0);

	} // JAXB needs this
 
	public void addTeam(int id, String title, int rounds){
		runnerObj team = new runnerObj(id,title,rounds);
		statList.add(team);
	}
	
	static class runnerObj {
		public int id;
		public String title;
		public int rounds;
		
		runnerObj(int id, String title, int rounds){
			this.id = id;
			this.title = title;
			this.rounds = rounds;
		}
		
		runnerObj(){
			// JAXB needs this
		}
		
	}

}
