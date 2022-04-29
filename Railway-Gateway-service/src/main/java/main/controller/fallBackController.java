package main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class fallBackController {

	@GetMapping("/trainServiceFallBack")
	public String trainServiceFallBack() {
		return "Train service is taking longer than Excpeted. " + " Please Try Again Later";
	}

	@GetMapping("/adminServiceFallBack")
	public String adminServiceFallBack() {

		return "Admin service is taking longer than excpected " + " Please Try Again Later";
	}
	
	@GetMapping("/bookingServiceFallBack")
	public String bookingServiceFallBack() {
		return "Booking service is taking longer than excpected " + " Please Try Again Later";
	}
}
