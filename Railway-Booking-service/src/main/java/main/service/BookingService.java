package main.service;

//import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import main.exception.InvalidCoachNameException;
import main.exception.InvalidPNRException;
import main.exception.NoSuchBookingsException;
import main.exception.TicketNotFoundException;
import main.models.BookedTicket;
import main.models.Seat;
import main.models.Ticket;
import main.models.Train;
import main.respository.BookedTicketRepository;

@Service
public class BookingService {

	@Autowired
	private BookedTicketRepository bookedTicketRepository;

	@Autowired
	private RestTemplate restTemplate;

	// FIND ALL THE BOOKINGS

	public List<BookedTicket> findAllBookings() throws NoSuchBookingsException {
		List<BookedTicket> bookings = bookedTicketRepository.findAll();
		if (bookings.isEmpty()) {
			throw new NoSuchBookingsException("NO BOOKINGS FOUND");
		}
		return bookings;
	}

	// GENERATE A RANDOM PNR NUMBER

	public long generatePNR() {

		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		long lo = Long.parseLong(String.format("%06d", number));
		return lo;
	}

	// BOOKING A TICKET

	public BookedTicket bookTicketByTrainNo(String trainNo, BookedTicket bookedTicket, String coachName)
			throws InvalidCoachNameException {
		Train train = restTemplate.getForObject("https://TRAIN-SERVICE/trains/public/getTrainByTrainNo/" + trainNo,
				Train.class);

		bookedTicket.getTicket().setPnr(generatePNR());
		bookedTicket.getTicket().setTrain_no(trainNo);
		bookedTicket.getTicket().setTrain_name(train.getTrainName());
		bookedTicket.getTicket().setStart(train.getDepatureStation());
		bookedTicket.getTicket().setDestination(train.getArrivalStation());
		bookedTicket.getTicket().setClass_name(coachName);
		bookedTicket.getTicket().setDeparture_time(train.getDepatureTime());
		bookedTicket.getTicket().setArrival_time(train.getArrivalTime());
		bookedTicket.getTicket().setQuota(train.getQuota());

		if (!train.getClasses().containsKey(coachName)) {
			throw new InvalidCoachNameException("COACH DOES NOT EXIST");
		}
		Seat seat = train.getClasses().get(coachName);
		double amountPerSeat = seat.getPrice();

		// from payment
		/*
		 * bookedTicket.setTransactional_id(04L); bookedTicket.setAccount_no(14L);
		 * bookedTicket.setEmail_address("lokesh@gmail.com");
		 * bookedTicket.setStatus("confirmed");
		 * bookedTicket.setBooking_time(LocalDateTime.now());
		 */

		double size = bookedTicket.getTicket().getPassengers().size();
		bookedTicket.setAmount(amountPerSeat * size);
		bookedTicketRepository.save(bookedTicket);
		return bookedTicket;
	}

	// GET ALL PASEENGERS TICKETS

	public List<Ticket> getAllPassengersTicket() throws TicketNotFoundException {
		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();

		List<Ticket> tickets = bookedTickets.stream().map(data -> data.getTicket()).collect(Collectors.toList());

		if (tickets.isEmpty()) {
			throw new TicketNotFoundException("TICKET NOT FOUND");
		}
		return tickets;
	}

	// GET PASSNGERS TICKET BY PNR

	public Ticket getPassengersTicketByPNR(long pnr) throws InvalidPNRException {
		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();

		List<Ticket> tickets = bookedTickets.stream().map(data -> data.getTicket()).collect(Collectors.toList());

		if (tickets.stream().noneMatch(data -> data.getPnr().equals(pnr))) {
			throw new InvalidPNRException();
		}
		return tickets.stream().filter(data -> data.getPnr().equals(pnr)).collect(Collectors.toList()).get(0);

	}

	// GET BOOKING TICKETS WITH BOOKING ID

	public BookedTicket bookedTicketByBookId(String bookId) throws NoSuchBookingsException {
		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();

		if (bookedTickets.stream().noneMatch(data -> data.getBookId().equals(bookId))) {
			throw new NoSuchBookingsException("BOOKING WITH" + bookId + "DOES NOT EXIST");
		}
		return bookedTickets.stream().filter(data -> data.getBookId().equals(bookId)).collect(Collectors.toList())
				.get(0);
	}

	// UPDATE BOOKING WITH BOOK ID (FOR PAYTM MICROSERVICE)

	public BookedTicket updateBookedTicketByBookId(String bookId, BookedTicket bookedTicket) throws NoSuchBookingsException {

		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();

		if (bookedTickets.stream().noneMatch(data -> data.getBookId().equals(bookId))) {
			throw new NoSuchBookingsException("BOOKING WITH" + bookId + "DOES NOT EXIST");
		}
		bookedTicket= bookedTickets.stream().filter(data -> data.getBookId().equals(bookId)).collect(Collectors.toList())
				.get(0);
		
		return bookedTicket;
	}
	
	// DELETE BOOKING WITH BOOK ID (FOR PAYTM MICROSERVICE)

	public String deleteBookedTicketByBookId(String bookId) throws NoSuchBookingsException {
		
		List<BookedTicket> bookedTickets = bookedTicketRepository.findAll();
		
		if (bookedTickets.stream().noneMatch(data -> data.getBookId().equals(bookId))) {
			throw new NoSuchBookingsException("BOOKING WITH" + bookId + "DOES NOT EXIST");
		}
	    bookedTicketRepository.delete(bookedTickets.stream().filter(data -> data.getBookId().equals(bookId)).collect(Collectors.toList())
				.get(0));
		
		return "DELETED BOOKING WITH"+bookId+"SUCCESSFULLY";
	}

}
