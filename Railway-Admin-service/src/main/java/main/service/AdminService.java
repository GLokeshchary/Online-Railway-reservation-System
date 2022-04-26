package main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import main.models.Train;
import main.models.Trains;

@Service
public class AdminService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	// GET ALL THE TRAINS FROM TRAIN SERVICE USING REST TEMPLATE 
	
	public List<Train> findAllTrains() {
			Trains trains = restTemplate.getForObject("https://TRAIN-SERVICE/trains/public/findAllTrains", Trains.class);	
		return trains.getList();
	}


	// SAVE TRAIN IN THE TRAIN SERVICE USING REST TEMPLATE IN ADMIN SERVICE
	
	public Train saveTrain(Train train) {
		return restTemplate.postForObject("https://TRAIN-SERVICE/trains/public/addTrain", train, Train.class);
		
	}


	// UPDATE TRAIN IN THE TRAIN SERVICE USING REST TEMPLATE IN ADMIN SERVICE
	
	public String updateTrain(Train train, String trainNo) {
		
		 restTemplate.put("https://TRAIN-SERVICE/trains/public/updateTrainByTrainNo/"+trainNo, train);
		 return "Updated Succesfully";
	}


	// DELETE TRAIN WITH TRAIN NO IN TRAIN SERVICE USING REST TEMPLATE
	
	public String deleteTrainByTrainNo(String trainNo) {
		restTemplate.delete("https://TRAIN-SERVICE/trains/public/deleteTrainByTrainNo/"+trainNo);
		return "DELETED SUCCESSFULLY";
	}

}
