package main.service;

import java.io.InputStream;
import java.time.LocalDateTime;
//import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.extern.slf4j.Slf4j;
import main.exception.InvalidCoachNameException;
import main.exception.InvalidPNRException;
import main.exception.NoSuchBookingsException;
import main.exception.PassengersNotFoundException;
import main.exception.TicketNotFoundException;
import main.models.BookedTicket;
import main.models.Passenger;
import main.models.ListOfPassengers;
import main.models.Seat;
import main.models.Ticket;
import main.models.Train;
import main.models.bookTicket;
import main.respository.BookedTicketRepository;
import main.respository.PassengerRepository;

@Service
@Slf4j
public class BookingService {

	@Autowired
	private BookedTicketRepository bookedTicketRepository;
	
	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EmailService emailService;

	// FIND ALL THE BOOKINGS

	public List<BookedTicket> findAllBookings() throws NoSuchBookingsException {
		
		List<BookedTicket> bookings = bookedTicketRepository.findAll();
		log.info("checking if list of booked tickets is empty or not");
		if (bookings.isEmpty()) {
			throw new NoSuchBookingsException("NO BOOKINGS FOUND");
		}
		log.info("getting list of booked tickets");
		return bookings;
	}

	// GENERATE A RANDOM PNR NUMBER

	public long generatePNR() {
        
		log.info("generating a random pnr");
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		long lo = Long.parseLong(String.format("%06d", number));
		return lo;
	}
	

	// BOOKING A TICKET

	public BookedTicket bookTicketByTrainNo(String trainNo, BookedTicket bookedTicket, String coachName,String email)
			throws InvalidCoachNameException, PassengersNotFoundException {
		log.info("getting train with"+trainNo+"with help of train service");
		Train train = restTemplate.getForObject("https://TRAIN-SERVICE/trains/public/getTrainByTrainNo/" + trainNo,
				Train.class);
		
		bookedTicket.setPnr(generatePNR());
		bookedTicket.setTrain_no(trainNo);
		bookedTicket.setTrain_name(train.getTrainName());
		bookedTicket.setStart(train.getDepatureStation());
		bookedTicket.setDestination(train.getArrivalStation());
		bookedTicket.setClass_name(coachName);
		bookedTicket.setDeparture_time(train.getDepatureTime());
		bookedTicket.setArrival_time(train.getArrivalTime());
		bookedTicket.setQuota(train.getQuota());
		bookedTicket.setEmail(email);
		List<Passenger>passengers=getAllPassengers();
		bookedTicket.setPassengers(passengers);
		if (!train.getClasses().containsKey(coachName)) {
			throw new InvalidCoachNameException("COACH DOES NOT EXIST");
		}
		Seat seat = train.getClasses().get(coachName);
		double amountPerSeat = seat.getPrice();

		 
		 bookedTicket.setBooking_time(LocalDateTime.now());
		

		double size = bookedTicket.getPassengers().size();
		bookedTicket.setAmount((amountPerSeat * size)+35.40+25.39);
		bookedTicketRepository.save(bookedTicket);
		log.info("tickets booked");
		this.deleteAllPassengerList();
		log.info("Sending email");
		emailService.sendEmail(bookedTicket,email);
		log.info("Email sended");
		log.info("Saving booked ticket");
		return bookedTicket;
	}

	// GET BOOKING TICKETS WITH PNR

	public BookedTicket bookedTicketByPNR(Long pnr) throws NoSuchBookingsException {
		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();

		log.info("checking if its matches with "+ pnr+"or not");
		if (bookedTickets.stream().noneMatch(data -> data.getPnr().equals(pnr))) {
			throw new NoSuchBookingsException("BOOKING WITH" + pnr + "DOES NOT EXIST");
		}
		log.info("return list of bookedtickets");
		return bookedTickets.stream().filter(data -> data.getPnr().equals(pnr)).collect(Collectors.toList())
				.get(0);
	}

	// UPDATE BOOKING WITH BOOK ID (FOR PAYTM MICROSERVICE)

	public BookedTicket updateBookedTicketByPNR(Long pnr, BookedTicket bookedTicket) throws NoSuchBookingsException {

		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();

		log.info("checking if its matches with "+ pnr+"or not");
		if (bookedTickets.stream().noneMatch(data -> data.getPnr().equals(pnr))) {
			throw new NoSuchBookingsException("BOOKING WITH" + pnr + "DOES NOT EXIST");
		}
		bookedTicket= bookedTickets.stream().filter(data -> data.getPnr().equals(pnr)).collect(Collectors.toList())
				.get(0);
		
		log.info("updating booking with ",pnr);
		return bookedTicket;
	}
	
	// DELETE BOOKING WITH BOOK ID (FOR PAYTM MICROSERVICE)

	public String deleteBookedTicketByBookId(String bookId) throws NoSuchBookingsException {
		
		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();
		
		log.info("checking if its matches with "+ bookId+"or not");
		if (bookedTickets.stream().noneMatch(data -> data.getBookId().equals(bookId))) {
			throw new NoSuchBookingsException("BOOKING WITH" + bookId + "DOES NOT EXIST");
		}
	    bookedTicketRepository.delete(bookedTickets.stream().filter(data -> data.getBookId().equals(bookId)).collect(Collectors.toList())
				.get(0));
		
	    log.info("deleteing booking with",bookId);
		return "DELETED BOOKING WITH"+bookId+"SUCCESSFULLY";
	}
	
	// SAVE PASSENGERS IN DATABASE

	public Passenger savePassenger(Passenger passenger) {
		log.info("Adding passengers to database");
		passengerRepository.save(passenger);
		log.info("Added to database");
		
		return passenger;
	}

	// GET ALL PASSENGERS LIST
	
	public List<Passenger> getAllPassengers() throws PassengersNotFoundException {
		
		List<Passenger> passengers = passengerRepository.findAll();
		log.info("checking if list of passengers is empty or not");
		if (passengers.isEmpty()) {
			throw new PassengersNotFoundException("NO Passengers FOUND");
		}
		log.info("getting list of passengers tickets");
		return passengers;
	}
	
	// DELETE ALL PASSENGERS LIST

	public String deleteAllPassengerList() {
		
		log.info("Deleting all passengers");
		passengerRepository.deleteAll();
		log.info("Passengers Deleted");
		return "DELETED ALL PASSENEGRS";
	}

	public void AngularBookTicketByTrainNo(String trainNo, bookTicket bookTicket, BookedTicket bookedTicket,
			String coachName) {
		restTemplate.postForObject(null, coachName, null);
		
	}
	
	// UPDATE PASSENGER BY ID

	public Passenger updatePassengerById(String passengerid,Passenger passenger) throws PassengersNotFoundException {
		log.info("getting Passenger by",passengerid);
		passenger=passengerRepository.findById(passengerid).get();
		log.info("checking if its null or not");
		if(passenger==null) {
			throw new PassengersNotFoundException("Passenger with passenger with"+passengerid+"not found");
		}
		log.info("Not null and saving in db");
		passengerRepository.save(passenger);
		log.info("edited");
		return passenger;
	}
	
	// DELETE  THE PASSENGER BY ID

	public String deletePassengerById(String passengerid) {
		log.info("deleting passenger by ",passengerid);
		passengerRepository.deleteById(passengerid);
		return "DELETED SUCCESSFULLY";
	}
	
	// GET PASSENGER BY ID

	public Passenger getPassengerById(String passengerid) {
		try {
			log.info("Getting passenger by",passengerid);
			passengerRepository.findById(passengerid);
			
			throw new PassengersNotFoundException("Passenger Not found");
		}
		catch (PassengersNotFoundException e) {
			log.error("Passenger Not Found ",e.getMessage());
		}
		return null;
	}

	public boolean checkPnrExistsOrNot(Long pnr) {
		List<BookedTicket> tickets = bookedTicketRepository.findAll();
		if(tickets.stream().anyMatch(data->data.getPnr().equals(pnr))) {
			return true;
		}
		return false;
	}
	
	//CANCEL THE TICKET BY PNR

	public BookedTicket cancelBookedTicketByBookId(Long pnr, String email, BookedTicket bookedTicket) {
		log.info("Checking if pnr exists or not");
		bookedTicket=bookedTicketRepository.findAll().stream().filter(data->data.getPnr().equals(pnr)).collect(Collectors.toList()).get(0);
		log.info("updating booked ticket");
		bookedTicket.setStatus("Cancelled");
		log.info("sending mail to ",email);
		emailService.bookingCancelled(bookedTicket, email);
		bookedTicketRepository.save(bookedTicket);
		log.info("updated the ticket");
		return bookedTicket;
	}

}
