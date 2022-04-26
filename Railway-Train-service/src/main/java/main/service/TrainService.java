package main.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.exception.InvalidTrainNoException;
import main.exception.NoTrainExistException;
import main.models.Train;
import main.repository.TrainRepository;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TrainService {
	
	@Autowired
	TrainRepository trainRepository;
	
	// SAVE A TRAIN IN DATABASE

	public Train addTrain(Train train) {
		trainRepository.save(train);
		return train;
		
	}
	
	// GET ALL THE TRAINS FROM DATABASE

	public List<Train> getAllTrains() throws NoTrainExistException {
		
		List<Train> trains = trainRepository.findAll();
		if(trains.isEmpty()) {
			throw new NoTrainExistException("No Trains Found");
		}
		return trains;
	}

	// DELETE TRAIN BY TRAIN NO IN THE DATABASE
	
	public void deleteTrainByTrainNo(String trainNo) throws InvalidTrainNoException {
		
		List<Train> trains=trainRepository.findAll();
		try {
			if(trains.stream().noneMatch(p->p.getTrainNo().equals(trainNo))) {
				throw new InvalidTrainNoException("TRAIN WITH TRAIN NO DOESNOT EXIST");
			}
		} catch (InvalidTrainNoException e) {
			e.printStackTrace();
		}
		Train train = trains.stream().filter(data->data.getTrainNo().equals(trainNo)).collect(Collectors.toList()).get(0);
		trainRepository.delete(train);
		
	}

	//UPDATE A TRAIN IN DATABASE
	
	public Train updateTrain(Train train) {
		
	    trainRepository.save(train);
		return train;
	}
	
	// GET LIST OF TRAINS BETWEEN ORIGIN AND DESTINATION
	
	public List<Train> getTrainBetweenTwoStations(String origin,String destination){
		List<Train> list=trainRepository.findAll();
		return list.stream().filter(data->data.getDepatureStation().equals(origin) && data.getArrivalStation().equals(destination)).collect(Collectors.toList());
	}
	
	// GET TRAIN BY TRAIN NO IN DATABASE
	
	public Train getTrainByTrainNo(String trainNo) {
		List<Train> trainlist=trainRepository.findAll();
		
		try {
			if(trainlist.stream().filter(data->data.getTrainNo().equals(trainNo)).collect(Collectors.toList()).isEmpty())
			{
				throw new NoTrainExistException("TRAIN NOT FOUND");
			}
		} catch (NoTrainExistException e) {
			e.printStackTrace();
		}
		
		return trainlist.stream().filter(data->data.getTrainNo().equals(trainNo)).collect(Collectors.toList()).get(0);
	}
	
	
	
}
