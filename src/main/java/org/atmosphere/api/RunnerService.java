package org.atmosphere.api;

import org.atmosphere.ttrex.Storage;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;




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
    public RunnerBean broadcast() throws Exception {
    	if (runner == null) {
    		throw new BadRequestException("ID does not exist on webserver");
    	} else {
    		return runner;
    	}
    }
    
    public class BadRequestException extends WebApplicationException {
        public BadRequestException(String message) {
            super(Response.status(Response.Status.BAD_REQUEST)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
        }
   }

}