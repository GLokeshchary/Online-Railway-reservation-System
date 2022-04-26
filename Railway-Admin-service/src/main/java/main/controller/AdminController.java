package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import main.models.Train;
import main.service.AdminService;

@RestController
public class AdminController {
	
	
	
	@Autowired
	private AdminService adminService;
	
	// GET ALL THE TRAINS FROM TRAIN SERVICE USING REST TEMPLATE 
	
	@GetMapping("/admin/findAllTrains")
	public List<Train> findAllTrains(){
		return adminService.findAllTrains();
	}
	
	// SAVE TRAIN IN THE TRAIN SERVICE USING REST TEMPLATE IN ADMIN SERVICE
	
	@PostMapping(value="/admin/saveTrain",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Train saveTrain(@RequestBody Train train) {
		 adminService.saveTrain(train);
		 return train;
	}
	
	// UPDATE TRAIN IN THE TRAIN SERVICE USING REST TEMPLATE IN ADMIN SERVICE
	
	@PutMapping(value="/admin/updateTrainByTrainNo/{trainNo}")
	public String updateTrainByTrainNo(@PathVariable String trainNo,@RequestBody Train train) {
		return adminService.updateTrain(train,trainNo);
	}
	
	// DELETE TRAIN WITH TRAIN NO IN TRAIN SERVICE USING REST TEMPLATE
	
	@DeleteMapping("/admin/deleteTrainByTrainNo/{trainNo}")
	public String deleteTrainByTrainNo(@PathVariable String trainNo) {
		return adminService.deleteTrainByTrainNo(trainNo);
	}

}