package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.PathItem.HttpMethod;
import main.models.Train;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class RailwayAdminServiceApplicationTests {

	@LocalServerPort
	int randomServerPort;
	@Test
	public void testGetTrainListSuccess() throws URISyntaxException{
		RestTemplate restTemplate=new RestTemplate();
		
		final String baseUrl="http://localhost:"+randomServerPort+"/admin/findAllTrains";
		URI uri=new URI(baseUrl);
		ResponseEntity<String> result=restTemplate.getForEntity(uri, String.class);
		
		//verify request succeed
		assertEquals(200, result.getStatusCodeValue());
		//assertEquals(true, result.getBody().contains("trainlist"));
	}
	@Test
	public void testAddTrainSuccess() throws URISyntaxException{
		RestTemplate restTemplate=new RestTemplate();
		
		final String baseUrl="http://localhost:"+randomServerPort+"/admin/saveTrain";
		URI uri=new URI(baseUrl);
		Train train=new Train(null, null, "hyderabad", null, null, null, null, null, null, false, false, null);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Train> request = new HttpEntity<>(train, headers);
		ResponseEntity<String> result=restTemplate.postForEntity(uri, request, String.class);
		
		//verify request succeed
		assertEquals(200, result.getStatusCodeValue());
		//assertEquals(true, result.getBody().contains("trainlist"));
	}
	@Test
	public void testAddTrainMissingHeader() throws URISyntaxException{
		RestTemplate restTemplate=new RestTemplate();
		
		final String baseUrl="http://localhost:"+randomServerPort+"/admin/saveTrain";
		URI uri=new URI(baseUrl);
		Train train=new Train(null, null, "hyderabad", null, null, null, null, null, null, false, false, null);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Train> request = new HttpEntity<>(train, headers);
		try {
			restTemplate.postForEntity(uri, request, String.class);
		} catch (HttpClientErrorException e) {
			
			assertEquals(400, e.getRawStatusCode());
		}
		
	}
	 @Test
	    public void testGetEmployeeListSuccessWithHeaders() throws URISyntaxException 
	    {
	        RestTemplate restTemplate = new RestTemplate();
	        
	        final String baseUrl = "http://localhost:"+randomServerPort+"/admin/findAllTrains";
	        URI uri = new URI(baseUrl);
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("X-COM-LOCATION", "USA");

	        HttpEntity<Train> requestEntity = new HttpEntity<>(null, headers);

	        try 
	        {
	            restTemplate.getForEntity(uri,String.class );
	        }
	        catch(HttpClientErrorException ex) 
	        {
	            //Verify bad request and missing header
	            assertEquals(400, ex.getRawStatusCode());
	            assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
	        }
	    }

}
