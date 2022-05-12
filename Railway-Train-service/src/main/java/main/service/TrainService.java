package main.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import main.exception.InvalidTrainNoException;
import main.exception.NoTrainExistException;
import main.exception.StationNotExistException;
import main.models.Seat;
import main.models.Train;
import main.models.Values;
import main.repository.TrainRepository;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TrainService {
	
	
	@Autowired
	TrainRepository trainRepository;
	
	// SAVE A TRAIN IN DATABASE

	public Train addTrain(Train train) {
		
		log.info("saving train data in DB");
		trainRepository.save(train);
		log.info("Saved in db");
		return train;
		
		
	}
	
	// GET ALL THE TRAINS FROM DATABASE

	public List<Train> getAllTrains() throws NoTrainExistException {
		
				
		List<Train> trains = trainRepository.findAll();
		log.info("checking if its empty or not");
		if(trains.isEmpty()) {
			throw new NoTrainExistException("No Trains Found");
		}
		log.info("Not empty and return list of trains");
		return trains;
	}

	// DELETE TRAIN BY TRAIN NO IN THE DATABASE
	
	public void deleteTrainByTrainNo(String trainNo) throws InvalidTrainNoException {
		
		List<Train> trains=trainRepository.findAll();
		log.info("checking if"+trainNo+"exists or not");
		try {
			if(trains.stream().noneMatch(p->p.getTrainNo().equals(trainNo))) {
				throw new InvalidTrainNoException("TRAIN WITH TRAIN NO DOESNOT EXIST");
			}
		} catch (InvalidTrainNoException e) {
			e.printStackTrace();
		}
		Train train = trains.stream().filter(data->data.getTrainNo().equals(trainNo)).collect(Collectors.toList()).get(0);
		log.info("deleted train by ",trainNo);
		trainRepository.delete(train);
		
	}

	//UPDATE A TRAIN IN DATABASE
	
	public Train updateTrain(Train train,String trainNo) {
		log.info("updating train with",trainNo);
		train=trainRepository.findAll().stream().filter(data->data.getTrainNo().equals(trainNo)).collect(Collectors.toList()).get(0);
	    trainRepository.save(train);
	    log.info("updated train with",trainNo);
		return train;
	}
	
	// GET LIST OF TRAINS BETWEEN ORIGIN AND DESTINATION
	
	public List<Train> getTrainBetweenTwoStations(String origin,String destination) throws StationNotExistException{
		List<Train> list=trainRepository.findAll();
		log.info("checking trains between "+origin+" and "+destination);
		if(list.stream().noneMatch(data->data.getDepatureStation().equals(origin) && data.getArrivalStation().equals(destination))) {
			throw new StationNotExistException("STATION DOESNOT EXISTS");
		}
		
		log.info("getting list of trains between "+origin+" and "+destination);
		return list.stream().filter(data->data.getDepatureStation().equals(origin) && data.getArrivalStation().equals(destination)).collect(Collectors.toList());
	}
	
	// GET TRAIN BY TRAIN NO IN DATABASE
	
	public Train getTrainByTrainNo(String trainNo) {
		List<Train> trainlist=trainRepository.findAll();
		log.info("checking train with",trainNo);
		try {
			if(trainlist.stream().filter(data->data.getTrainNo().equals(trainNo)).collect(Collectors.toList()).isEmpty())
			{
				throw new NoTrainExistException("TRAIN NOT FOUND");
			}
		} catch (NoTrainExistException e) {
			e.printStackTrace();
		}
		
		log.info("get train with",trainNo);
		
		return trainlist.stream().filter(data->data.getTrainNo().equals(trainNo)).collect(Collectors.toList()).get(0);
	}
	
	// PASSING VALUES TO ANGULAR

	public Values getPriceByTrainNo(String trainNo, String coach) {
		Train train = this.getTrainByTrainNo(trainNo);
		Seat seat = train.getClasses().get(coach);
		System.out.println(seat);
		Values values=new Values();
		values.setPrice(seat.getPrice());
		return values;
	}
	
	
	
}
