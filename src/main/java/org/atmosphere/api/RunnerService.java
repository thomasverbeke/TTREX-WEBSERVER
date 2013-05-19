package org.atmosphere.api;

import org.atmosphere.ttrex.Storage;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;




@Path("/runner/{id}")
public class RunnerService {
	
	private Storage tmpStorage;
	@PathParam("id") int runner_id;
	private RunnerBean runner;
	
	//Inject context
	public RunnerService(@Context ServletContext context){
		System.out.println("Webservice: Responding to <RunnerService> request");
		tmpStorage = (Storage) context.getAttribute("storageClass");	
		runner = tmpStorage.getRunner(runner_id);	//get runner from the storage class
		
	}	
	
    @GET @Produces("application/json")
    public RunnerBean broadcast() {
    	if (runner == null){
    		//TODO better handling when user does not exist
    		return new RunnerBean(0,0,0,0,0,0);
    	} else {
    		return runner;
    	}
    }

}