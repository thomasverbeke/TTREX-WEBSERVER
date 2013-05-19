package org.atmosphere.api;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.atmosphere.ttrex.Storage;

@Path("/stats")
public class StatsService {
	private Storage tmpStorage;
	private StatsBean stats;
	
	//Inject context
	public StatsService(@Context ServletContext context){
		System.out.println("Webservice: Responding to <RunnerService> request");
		tmpStorage = (Storage) context.getAttribute("storageClass");	
		stats = (StatsBean) tmpStorage.getRanking();
		
	}	
	 
    @GET
    @Produces("application/json")
    public StatsBean broadcast() {
    	return new StatsBean();
    }
}
