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

import main.exception.InvalidCoachNameException;
import main.exception.InvalidPNRException;
import main.exception.NoSuchBookingsException;
import main.exception.PassengersNotFoundException;
import main.exception.TicketNotFoundException;
import main.models.BookedTicket;
import main.models.Bookings;
import main.models.Passenger;
import main.models.ListOfPassengers;
import main.models.PassengersList;
import main.models.Ticket;
import main.models.bookTicket;
import main.service.BookingService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	// GET ALL BOOKINGS
	
	@GetMapping("/getAllBooking")
	public List<BookedTicket> getAllBookedTickets() throws NoSuchBookingsException{
		return bookingService.findAllBookings();
	}
	
	// GET ALL BOOKINGS USEFUL IN ADMIN SERVICE
	
	@GetMapping("/findAllBookings")
	public Bookings findAllBookings() throws NoSuchBookingsException {
		List<BookedTicket> list = bookingService.findAllBookings();
		Bookings bookings =new Bookings();
		bookings.setBookedTickets(list);
		return bookings;
	}
	/*
	// GET ALL PASSENGERS TICKETS
	
	@GetMapping("/getAllPassengersTicket")
	public List<Ticket> getAllPassengersTicket() throws TicketNotFoundException{
		return bookingService.getAllPassengersTicket();
	}
	
	// GET ALL PASSENGERS TICKETS FOR ADMIN SERVICE
	
	@GetMapping("/findAllPassengersTiket")
	public PassengersList findAllPassengersTiket() throws TicketNotFoundException {
		List<Ticket> list = bookingService.getAllPassengersTicket();
		PassengersList passengersList=new PassengersList();
		passengersList.setTickets(list);
		return passengersList;
	}
	
	// GET PASSENGERS TICKETS BY PNR
	
	@GetMapping("/getPassengersTicketByPNR/{pnr}")
	public Ticket getPassengersTicketByPNR(@PathVariable long pnr) throws InvalidPNRException {
		return bookingService.getPassengersTicketByPNR(pnr);
	}
	*/
	// BOOKING A TRAIN WITH PARTCULAR TRAIN NO AND CLASS (SL,AC1,AC2...)
	
	@PostMapping("/bookTicketByTrainNo/{trainNo}/{coachName}")
	public BookedTicket bookTicketByTrainNo(@PathVariable String trainNo,@RequestBody BookedTicket bookedTicket,@PathVariable String coachName) throws InvalidCoachNameException, PassengersNotFoundException {
		
		return bookingService.bookTicketByTrainNo(trainNo,bookedTicket,coachName);
		
	}
	
	// FOR ANGULAR CONNECTION
	
	@PostMapping("/bookTicketByTrainNo2/{trainNo}/{coachName}")
	public bookTicket AngularBookTicketByTrainNo(@PathVariable String trainNo,@RequestBody bookTicket bookTicket, BookedTicket bookedTicket,@PathVariable String coachName) throws InvalidCoachNameException, PassengersNotFoundException {
		
		 bookingService.bookTicketByTrainNo(trainNo,bookedTicket,coachName);
		 return bookTicket;
		
	}
	
	// GET BOOKING TICKETS WITH BOOK ID
	
	@GetMapping("/getbookedTicketByBookId/{bookId}")
	public BookedTicket getbookedTicketByBookId(@PathVariable String bookId) throws NoSuchBookingsException {
		
		return bookingService.bookedTicketByBookId(bookId);
	}
	
	// UPDATE BOOKING WITH BOOK ID (FOR PAYTM MICROSERVICE)
	
	@PutMapping("/updateBookedTicketByBookId/{bookId}")
	public BookedTicket updateBookedTicketByBookId(@PathVariable String bookId,@RequestBody BookedTicket bookedTicket) throws NoSuchBookingsException {
		
		return bookingService.updateBookedTicketByBookId(bookId,bookedTicket);
	}
	
	// DELETE BOOKING WITH BOOK ID (FOR PAYTM MICROSERVICE)
	
	@DeleteMapping("/deleteBookedTicketByBookId/{bookId}")
	public String deleteBookedTicketByBookId(@PathVariable String bookId) throws NoSuchBookingsException {
		
		return bookingService.deleteBookedTicketByBookId(bookId);
	}
	
	// POST PASSENGER IN DATABASE
	
	@PostMapping("/savePassenger")
	public Passenger savePassenger(@RequestBody Passenger passenger) {
		return bookingService.savePassenger(passenger);
	}
	
	// GET ALL PASSENGER LIST
	
	@GetMapping("/getAllPassengers")
	public List<Passenger> getAllPassengers() throws PassengersNotFoundException{
		return bookingService.getAllPassengers();
	}
	
	// FOR RESTTEMPLATE PURPOSE
	
	@GetMapping("/findAllPassengers")
	public ListOfPassengers findAllPassengers() throws PassengersNotFoundException {
		List<Passenger> passengerslist = bookingService.getAllPassengers();
		ListOfPassengers dto=new ListOfPassengers();
		dto.setPassengers(passengerslist);
		return dto;
	}
	
	// DELETE ALL THE PASSENGERS LIST
	
	@DeleteMapping("/deleteAllPassengerList")
	public String deleteAllPassengerList() {
		return bookingService.deleteAllPassengerList();
	}

}
