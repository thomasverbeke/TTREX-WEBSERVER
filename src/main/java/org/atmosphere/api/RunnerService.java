package org.atmosphere.api;

import org.atmosphere.annotation.Suspend;
import org.atmosphere.annotation.Broadcast;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/runner/{id}")
public class RunnerService {
	//TODO GET RUNNER trough ID
	//TODO GET STATS
	
	//Need to identify the type of request
	@PathParam("request") String request;
	 
    @GET @Produces("application/json")
    public RunnerBean broadcast() {
    	return new RunnerBean(0,0.32530120,50.87416333,4.70799666,3,6);
    }

}