package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.exception.InvalidCoachNameException;
import main.exception.InvalidPNRException;
import main.exception.NoSuchBookingsException;
import main.exception.TicketNotFoundException;
import main.models.BookedTicket;
import main.models.Ticket;
import main.service.BookingService;

@RestController
@RequestMapping("/customer")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping("/getAllBooking")
	public List<BookedTicket> getAllBookedTickets() throws NoSuchBookingsException{
		return bookingService.findAllBookings();
	}
	
	@GetMapping("/getBookingByPNR/{pnr}")
	public BookedTicket getBookingByPNR(@PathVariable Long pnr) throws InvalidPNRException {
		return bookingService.getBookingByPNR(pnr);
	}
	
	@GetMapping("/getAllPassengersTicket")
	public List<Ticket> getAllPassengersTicket() throws TicketNotFoundException{
		return bookingService.getAllPassengersTicket();
	}
	
	@GetMapping("/getPassengersTicketByPNR/{pnr}")
	public Ticket getPassengersTicketByPNR(@PathVariable long pnr) throws InvalidPNRException {
		return bookingService.getPassengersTicketByPNR(pnr);
	}
	
	@PostMapping("/bookTicketByTrainNo/{trainNo}/{coachName}")
	public BookedTicket bookTicketByTrainNo(@PathVariable String trainNo,@RequestBody BookedTicket bookedTicket,@PathVariable String coachName) throws InvalidCoachNameException {
		
		return bookingService.bookTicketByTrainNo(trainNo,bookedTicket,coachName);
		
	}
	

}
