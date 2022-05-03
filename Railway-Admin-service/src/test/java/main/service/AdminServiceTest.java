package main.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.models.Train;
import main.models.Trains;


class AdminServiceTest {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AdminService adminService;
	private MockRestServiceServer mockServer;
	private ObjectMapper mapper=new ObjectMapper();
	
	@BeforeEach
	public void init() {
		mockServer=MockRestServiceServer.createServer(restTemplate);
	}
	

	@Test
	void testFindAllTrains() throws JsonProcessingException, URISyntaxException {
		Trains trains=new Trains(Arrays.asList(new Train(),new Train()));
		mockServer.expect(requestTo(new URI("https://TRAIN-SERVICE/trains/public/findAllTrains")))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withStatus(HttpStatus.OK)
		.contentType(MediaType.APPLICATION_JSON)
		.body(mapper.writeValueAsString(trains)));
		
		List<Train> list=adminService.findAllTrains();
		assertEquals(2,list.size());
	}

	@Test
	void testSaveTrain() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateTrain() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteTrainByTrainNo() {
		fail("Not yet implemented");
	}

	@Test
	void testFindAllBookings() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllPassengersTicket() {
		fail("Not yet implemented");
	}

}
