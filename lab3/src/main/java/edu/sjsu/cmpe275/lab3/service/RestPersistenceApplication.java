package edu.sjsu.cmpe275.lab3.service;

import edu.sjsu.cmpe275.lab3.util.CORSResponseFilter;
import edu.sjsu.cmpe275.lab3.util.LoggingResponseFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Registers the components to be used by the JAX-RS application  
 * 
 * @author ama
 *
 */
public class RestPersistenceApplication extends ResourceConfig {

    /**
	* Register JAX-RS application components.
	*/	
	public RestPersistenceApplication(){
		register(RequestContextFilter.class);
		register(PlayerRestService.class);
		register(SponsorRestService.class);
		register(JacksonFeature.class);	
		register(LoggingResponseFilter.class);
		register(CORSResponseFilter.class);
	}
}
