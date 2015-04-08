package edu.sjsu.cmpe275.lab3.service;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class TestPlayerRestServiceWithJerseyTestFramework extends JerseyTest{
	
//	public static final String MOCK_SPRING_APPLICATIONCONTEXT = "classpath:spring/applicationContext.xml";	
//    
//	private PodcastRestService podcastRestService;
//	
//	private PodcastDao podcastDao;
//	
//	
//	@Override
//    protected Application configure() {
//        ResourceConfig resourceConfig = new MyDemoApplication();
//        enable(TestProperties.LOG_TRAFFIC);
//        enable(TestProperties.DUMP_ENTITY);
//        resourceConfig.property("contextConfigLocation", MOCK_SPRING_APPLICATIONCONTEXT); // Set which application context to use
//        return resourceConfig;
//    }
//	
//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//        podcastRestService = (PodcastRestService) getSpringApplicationContext().getBean("podcastRestService");
//        podcastDao = (PodcastDao) getSpringApplicationContext().getBean("podcastDao");
//        Assert.assertNotNull(podcastRestService);
//    }
//
//    @After
//    public void after() throws Exception {
//        super.tearDown();
//    }
	
    @Override
    protected Application configure() {
        // Enable logging.
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        
        return new RestPersistenceApplication()
        			.property("contextConfigLocation", "classpath:test-applicationContext.xml");
    }
 	

    @Ignore @Test
    public void testJerseyResource() {
        // Make a better test method than simply outputting the result.
        System.out.println(target("spring-hello").request().get(String.class));
    }
    
    @Ignore @Test
    public void testSmth(){
    	Response response = target("RESTwithPersistence/players/2").request().get(Response.class);
    	Assert.assertEquals(200, response.getStatus());
    }
	
}
