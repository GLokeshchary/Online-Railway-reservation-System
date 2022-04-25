package main.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import main.models.Train;
import main.models.Trains;
import main.service.TrainService;

@RestController
@RequestMapping("/trains")
public class TrainController {
	
	@Autowired
	private TrainService trainService;
	//save train
	@PostMapping("/public/addTrain")
	public Train addTrain(@RequestBody Train train) {
		return trainService.addTrain(train);
		
	}
	//GET all trains
	@GetMapping("/public/getAllTrains")
	public List<Train> getAllTrains() throws NoTrainExistException{
		return trainService.getAllTrains();
	}
	//CAlling get all trains through micro service communication
	@GetMapping("/public/findAllTrains")
	public Trains findAllTrains() throws NoTrainExistException {
		List<Train> trainList=trainService.getAllTrains();
		Trains trains=new Trains();
		trains.setList(trainList);
		return trains;
	}
	//get all the trains between two stations
	@GetMapping("/public/getTrainBetween/{origin}:{destination}")
	public List<Train> getTrainBetweenTwoStations(@PathVariable String origin,@PathVariable String destination){
		
		 return trainService.getTrainBetweenTwoStations(origin, destination);
	}
	
	//get train by train no
	@GetMapping("/public/getTrainByTrainNo/{trainNo}")
	public Train getTrainByTrainNo(@PathVariable String trainNo) {
		
		return trainService.getTrainByTrainNo(trainNo);
	}
	
	//Delete Train
	@DeleteMapping("/public/deleteTrainByTrainNo/{trainNo}")
	public String deleteTrain(@PathVariable String trainNo) throws InvalidTrainNoException {
		
		trainService.deleteTrainByTrainNo(trainNo);
		return "Deleted SuccessFully";
	}
	//Update Train
	@PutMapping("/public/updateTrainByTrainNo/{trainNo}")
	public Train updateTrainByTrainNo(@PathVariable String trainNo,@RequestBody Train train) {
		 return trainService.updateTrain(train);
	}
	
}
