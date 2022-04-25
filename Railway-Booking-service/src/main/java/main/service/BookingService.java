package main.service;

import java.time.LocalDateTime;
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
import main.respository.TicketRepository;

@Service
public class BookingService {

	@Autowired
	private BookedTicketRepository bookedTicketRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private RestTemplate restTemplate;

	public List<BookedTicket> findAllBookings() throws NoSuchBookingsException {
		List<BookedTicket> bookings = bookedTicketRepository.findAll();
		if (bookings.isEmpty()) {
			throw new NoSuchBookingsException("NO BOOKINGS FOUND");
		}
		return bookings;
	}

	public BookedTicket getBookingByPNR(Long pnr) throws InvalidPNRException {
		List<BookedTicket> bookings = bookedTicketRepository.findAll();
		if (bookings.stream().noneMatch(data -> data.getPnr().equals(pnr))) {
			throw new InvalidPNRException("NO TICKET WITH" + pnr + "EXISTS");
		}
		return bookings.stream().filter(data -> data.getPnr().equals(pnr)).collect(Collectors.toList()).get(0);
	}

	// generate random pnr
	public long generatePNR() {

		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		long lo = Long.parseLong(String.format("%06d", number));
		return lo;
	}

	public List<Ticket> getAllPassengersTicket() throws TicketNotFoundException {
		List<Ticket> tickets = ticketRepository.findAll();

		if (tickets.isEmpty()) {
			throw new TicketNotFoundException("TICKET NOT FOUND");
		}
		return tickets;
	}

	public Ticket getPassengersTicketByPNR(long pnr) throws InvalidPNRException {
		List<Ticket> tickets = ticketRepository.findAll();
		if (tickets.stream().noneMatch(data -> data.getPnr().equals(pnr))) {
			throw new InvalidPNRException("NO TICKET WITH" + pnr + "EXISTS");
		}
		return tickets.stream().filter(data -> data.getPnr().equals(pnr)).collect(Collectors.toList()).get(0);
	}

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

		bookedTicket.setPnr(generatePNR());
		//from payment microservice
		bookedTicket.setTransactional_id(04L);
		bookedTicket.setAccount_no(14L);
		bookedTicket.setEmail_address("lokesh@gmail.com");
		bookedTicket.setStatus("confirmed");
		bookedTicket.setBooking_time(LocalDateTime.now());
		
		double size=bookedTicket.getTicket().getPassengers().size();
		bookedTicket.setAmount(amountPerSeat*size);
		bookedTicketRepository.save(bookedTicket);
		return bookedTicket;
	}

}
