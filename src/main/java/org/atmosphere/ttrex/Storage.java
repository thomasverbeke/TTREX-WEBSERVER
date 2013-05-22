package org.atmosphere.ttrex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.atmosphere.api.RunnerBean;
import org.atmosphere.api.StatsBean;
import org.atmosphere.ext.xmlReader;
import org.atmosphere.ext.GroupBean;
import org.xml.sax.SAXException;

/* This class will store all incoming data 
 * and generate stats like ranking for the webservices & API
 * 
 * */
public class Storage {

	EntityManagerFactory emf;
	EntityManager em;
	
	private ArrayList<RunnerBean> runnerList = new ArrayList<RunnerBean>();
	private ArrayList<GroupBean> teamList;
	
	/* 	Default constructor */
	public Storage(){

		emf = Persistence.createEntityManagerFactory("runner.odb");
		em = emf.createEntityManager();

		RunnerBean runner = new RunnerBean(1,0,0,0,2,0);	
		
		System.out.println("commiting");
		em.getTransaction().begin();
	
	
		TypedQuery <RunnerBean> runnerQ = em.createQuery("SELECT r FROM Runners r",RunnerBean.class);
       
		if (runnerQ.getResultList() != null){
			System.out.println("Data in memory; polling the database.");
			runnerList = (ArrayList<RunnerBean>) runnerQ.getResultList();
		}
		  
        em.getTransaction().commit();
        
        //load in teams from teams from xml file
        teamList = loadInTeams();
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
		//check if runner is already present?
		RunnerBean runner = new RunnerBean(runner_id,percentage,latitude,longitude,rounds,speed);	
		
		if (editRunner(runner_id,percentage,latitude,longitude,rounds,speed)){
		
			em.getTransaction().begin();
			em.merge(runner);
			em.getTransaction().commit();
		} else {
			//add runner
			
			runnerList.add(runner);
			
			//persist runner
			em.getTransaction().begin();
			em.persist(runner);
			em.getTransaction().commit();
		}
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
	public boolean editRunner(int runner_id, double percentage,double latitude, double longitude, int rounds, double speed){
		for (int i=0; i<runnerList.size(); i++){
			RunnerBean runner = (RunnerBean) runnerList.get(i);
			if (runner.runner_id == runner_id){
				runnerList.set(i, new RunnerBean(runner_id, percentage,latitude,longitude,rounds,speed));
				return true;
			}		
		}
		return false;
	}
	
	/* 	Clear all data  */
	public void reset(){
		runnerList.clear();
		teamList.clear();
		em.clear();
	}
	
	public StatsBean getRanking(){
		//2 teams are already hardcoded inside the StatsBean
		StatsBean stats = new StatsBean(); 
	
		//sort the list
		Collections.sort(runnerList, new CompareRounds());
		
		//loop sorted list
		for (int i=0; i<runnerList.size(); i++){
			RunnerBean runner = (RunnerBean) runnerList.get(i);	
			String teamName = "default";
			
			//loop group list for team name
			for (int j=0; j<teamList.size(); j++){
				if (teamList.get(j).getID() == i){
					teamName = teamList.get(j).getGroupName();
				}
			}
			stats.addTeam(runner.runner_id, teamName, runner.rounds); //hardcoded teamname for now
		}
		
		//return bean
		return stats;
	} 
	
	public ArrayList<GroupBean> loadInTeams(){
		try {
			xmlReader reader = new xmlReader("http://ttrex.eu/groups.xml");
			ArrayList<GroupBean> list = reader.getList();
			return list ;
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
		
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


