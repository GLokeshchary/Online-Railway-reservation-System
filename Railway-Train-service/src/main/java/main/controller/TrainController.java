package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.exception.InvalidTrainNoException;
import main.exception.NoTrainExistException;
import main.exception.StationNotExistException;
import main.models.Seat;
import main.models.Train;
import main.models.Trains;
import main.models.Values;
import main.service.TrainService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trains")
public class TrainController {
	
	
	
	@Autowired
	private TrainService trainService;
	
	
	
	// SAVE A TRAIN IN DATABASE
	
	@PostMapping("/public/addTrain")
	public Train addTrain(@RequestBody Train train) {
		
		
		return trainService.addTrain(train);
		
		
	}
	
	// GET ALL THE TRAINS FROM DATABASE
	
	@GetMapping("/public/getAllTrains")
	public List<Train> getAllTrains() throws NoTrainExistException{
		
		
		return trainService.getAllTrains();
	}
	
	// METHOD USED FOR MICRO SERVICE COMMUNICATON
	
	@GetMapping("/public/findAllTrains")
	public Trains findAllTrains() throws NoTrainExistException {
		List<Train> trainList=trainService.getAllTrains();
		Trains trains=new Trains();
		trains.setList(trainList);
		return trains;
	}
	
	// GET LIST OF TRAINS BETWEEN ORIGIN AND DESTINATION
	
	@GetMapping("/public/getTrainBetween/{origin}:{destination}")
	public List<Train> getTrainBetweenTwoStations(@PathVariable String origin,@PathVariable String destination) throws StationNotExistException{
		
		 return trainService.getTrainBetweenTwoStations(origin, destination);
	}
	
	// GET TRAIN BY TRAIN NO IN DATABASE
	
	@GetMapping("/public/getTrainByTrainNo/{trainNo}")
	public Train getTrainByTrainNo(@PathVariable String trainNo) {
		
		return trainService.getTrainByTrainNo(trainNo);
	}
	
	// DELETE TRAIN BY TRAIN NO IN THE DATABASE
	
	@DeleteMapping("/public/deleteTrainByTrainNo/{trainNo}")
	public String deleteTrain(@PathVariable String trainNo) throws InvalidTrainNoException {
		
		trainService.deleteTrainByTrainNo(trainNo);
		return "Deleted SuccessFully";
	}
	
	//UPDATE A TRAIN IN DATABASE
	
	@PutMapping("/public/updateTrainByTrainNo/{trainNo}")
	public Train updateTrainByTrainNo(@PathVariable String trainNo,@RequestBody Train train) {
		 return trainService.updateTrain(train,trainNo);
	}
	
	// PASSING VALUES TO ANGULAR
	
	@GetMapping("/public/getPriceByTrainNo/{trainNo}/{coach}")
	public Values getPriceByTrainNo(@PathVariable String trainNo,@PathVariable String coach) {
		return trainService.getPriceByTrainNo(trainNo,coach);
		
	}
	
	
	
}
