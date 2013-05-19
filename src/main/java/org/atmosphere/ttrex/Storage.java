package org.atmosphere.ttrex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.atmosphere.api.RunnerBean;
import org.atmosphere.api.StatsBean;

/* This class will store all incoming data 
 * and generate stats like ranking for the webservices & API
 * 
 * */
public class Storage {

	private ArrayList<RunnerBean> runnerList = new ArrayList<RunnerBean>();
	
	/* 	Default constructor */
	public Storage(){
		
	}
	
	/* 	Add a runner
	 *  @param	runner_id 	the id of the runner
	 *  @param 	percentage	progress in % of the runner on the track
	 *  @param	latitude	gps latitude coordinate of the runner
	 *  @param 	longitude	gps longitude coordinate of the runner
	 *  @param	rounds		number of rounds the runner has completed
	 *  @param	speed		current gps speed of the runner
	*/
	public void addRunner(int runner_id, double percentage,double latitude, double longitude, int rounds, double speed){
		RunnerBean runner = new RunnerBean(runner_id,percentage,latitude,longitude,rounds,speed);	
		runnerList.add(runner);
		
	}
	
	/* 	Get data for a runner
	 * 
	 *  @param	runner_id 	the id of the runner
	*/
	public RunnerBean getRunner(int runner_id){
		//loop over the runnerList
		for (int i=0; i<runnerList.size(); i++){
			RunnerBean runner = (RunnerBean) runnerList.get(i);
			if (runner.runner_id == runner_id){
				return runner;
			}		
		}
		return null;
	}
	
	/* 	Edit data for a runner
	 * 
	 *  @param	runner_id 	the id of the runner that's going to be updated
	 *  @param 	percentage	progress in % of the runner on the track
	 *  @param	latitude	gps latitude coordinate of the runner
	 *  @param 	longitude	gps longitude coordinate of the runner
	 *  @param	rounds		number of rounds the runner has completed
	 *  @param	speed		current gps speed of the runner
	*/
	public void editRunner(int runner_id, double percentage,double latitude, double longitude, int rounds, double speed){
		for (int i=0; i<runnerList.size(); i++){
			RunnerBean runner = (RunnerBean) runnerList.get(i);
			if (runner.runner_id == runner_id){
				runnerList.set(i, new RunnerBean(runner_id, percentage,latitude,longitude,rounds,speed));
			}		
		}
	}
	
	/* 	Clear all data  */
	public void reset(){
		runnerList.clear();
	}
	
	public StatsBean getRanking(){
		//2 teams are already hardcoded inside the StatsBean
		StatsBean stats = new StatsBean(); 
	
		//sort the list
		Collections.sort(runnerList, new CompareRounds());
		
		//compare information from xml files (we will hardcode teamData for now).
		
		//loop sorted list
		for (int i=0; i<runnerList.size(); i++){
			RunnerBean runner = (RunnerBean) runnerList.get(i);	
			stats.addTeam(runner.runner_id, "TEAM"+i, runner.rounds);
		}
		
		//return bean
		return stats;
	} 
	
	public void loadInTeams(){
		//TODO Acces xml file on server and setup teams
	}
	
	public class CompareRounds implements Comparator<RunnerBean> {
		@Override
		public int compare(RunnerBean o1, RunnerBean o2) {
			Integer obj1 = new Integer(o1.rounds);
			Integer obj2 = new Integer(o2.rounds);
			return obj1.compareTo(obj2);
		}
	}
	
}


