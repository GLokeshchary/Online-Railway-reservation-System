package main.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import main.exception.InvalidTrainNoException;
import main.exception.NoTrainExistException;
import main.models.Train;
import main.repository.TrainRepository;

class TrainServiceTest {
	
	private TrainRepository trainRepository;
	
	private TrainService trainService;

	@BeforeEach
	void setUp() throws Exception {
		trainRepository=mock(TrainRepository.class);
		trainService=new TrainService(trainRepository);
	}

	@Test
	void testAddTrain() {
		Train train= new Train();
		train.setDepatureStation("Hyderabad");
		train.setArrivalStation("Delhi");
		train.setTrainName("Sampark krathi");
		
		Train actualresponse = trainService.addTrain(train);
		Train exceptedresponse= new Train();
		exceptedresponse.setDepatureStation("Hyderabad");
		exceptedresponse.setArrivalStation("Delhi");
		exceptedresponse.setTrainName("Sampark krathi");
		
		assertThat(exceptedresponse).isEqualTo(actualresponse);
		
	}
	@Test
	void listOfAllTrainsInCaseOfEmptyResult() {
		when(trainRepository.findAll()).thenReturn(Collections.emptyList());
		assertThrows(NoTrainExistException.class, ()->trainService.getAllTrains());
	}
	@Test
	void listAllTrains() throws NoTrainExistException {
		when(trainRepository.findAll()).thenReturn(List.of(new Train(),new Train()));
		List<Train> trains = trainService.getAllTrains();
		
		assertThat(trains).isNotNull();
		assertThat(2).isEqualTo(trains.size());
		
	}
	
	@Test
	void getTrainByTrainNoException() {
		assertThrows(IndexOutOfBoundsException.class,()->trainService.getTrainByTrainNo("id"));	
	}
	@Test
	@Disabled
	void trainByTrainNoTest() throws NoTrainExistException {
		
		Train train= new Train();
		train.setQuota("GENERAL");
		train.setDepatureTime(LocalTime.of(7, 30, 00));
		
		when(trainRepository.findAll().stream().filter(data->data.getTrainNo().equals(toString())).collect(Collectors.toList()).get(0)).thenReturn(train);
		Train actualtrain=trainService.getTrainByTrainNo("id");
		assertThat(train).isEqualTo(actualtrain);
	}
	
	@Test
	void updateTrainTest() {
		Train train= new Train();
		train.setQuota("GENERAL");
		train.setDepatureTime(LocalTime.of(7, 30, 00));
		
		Train actualTrain=trainService.updateTrain(train);
		Train excpetedTrain=new Train();
		excpetedTrain.setQuota("GENERAL");
		excpetedTrain.setDepatureTime(LocalTime.of(7, 30, 00));
		
		assertThat(excpetedTrain).isEqualTo(actualTrain);
	}
	
	@Test
	@Disabled
	void deleteTrainExceptionTest() {
		List<Train> trains = trainRepository.findAll();
		when(trains.stream().noneMatch(data->data.getTrainNo().equals("id"))).thenThrow(InvalidTrainNoException.class);
		assertThrows(InvalidTrainNoException.class, ()->trainService.deleteTrainByTrainNo("id"));
	}
	
	@Test
	void deleteTrainTest() {
		Train train =new Train();
	}
	
	

}
