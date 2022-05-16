package main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import main.models.Bookings;
import main.models.PassengersList;
import main.models.Train;
import main.models.Trains;

@Service
@Slf4j
public class AdminService {

	@Autowired
	private RestTemplate restTemplate;

	// GET ALL THE TRAINS FROM TRAIN SERVICE USING REST TEMPLATE

	public List<Train> findAllTrains() {
		log.info("getting list of trains from train service");
		Trains trains = restTemplate.getForObject("https://TRAIN-SERVICE/trains/public/findAllTrains", Trains.class);
		return trains.getList();
	}

	// SAVE TRAIN IN THE TRAIN SERVICE USING REST TEMPLATE IN ADMIN SERVICE

	public Train saveTrain(Train train) {
		log.info("saving a train through admin service");
		return restTemplate.postForObject("https://TRAIN-SERVICE/trains/public/addTrain", train, Train.class);

	}

	// UPDATE TRAIN IN THE TRAIN SERVICE USING REST TEMPLATE IN ADMIN SERVICE

	public Train updateTrain(Train train, String trainNo) {

		log.info("updating a train with"+trainNo+" through admin service");
		restTemplate.put("https://TRAIN-SERVICE/trains/public/updateTrainByTrainNo/" + trainNo, train);
		return train;
	}

	// DELETE TRAIN WITH TRAIN NO IN TRAIN SERVICE USING REST TEMPLATE

	public String deleteTrainByTrainNo(String trainNo) {
		log.info("deleting a train with"+trainNo+" through admin service");
		restTemplate.delete("https://TRAIN-SERVICE/trains/public/deleteTrainByTrainNo/" + trainNo);
		return "DELETED SUCCESSFULLY";
	}
	
	// FIND ALL BOOKINGS FROM BOOKING MICROSERVICE

	public Bookings findAllBookings() {
		Bookings bookings=restTemplate.getForObject("https://BOOKING-SERVICE/customer/findAllBookings", Bookings.class);
		return bookings;
	}
	
	// GET ALL PASSENERS TICKET FROM BOOKING SERVICE
	
	public PassengersList getAllPassengersTicket() {
		PassengersList list=restTemplate.getForObject("https://BOOKING-SERVICE/customer/findAllPassengersTiket", PassengersList.class);
		return list;
	}

}
