package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import main.models.BookedTicket;

@Service
public class EmailService {
	
	@Autowired
	JavaMailSender javaMailSender;

	public BookedTicket sendEmail(BookedTicket bookedTicket,String mailId) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("lokeshchary648@gmail.com");
		
		message.setTo(mailId);
		message.setSubject("Booking Confirmation Mail");
		message.setText("Successfull Booking With PNR Number: "+bookedTicket.getPnr()+","
				+ "  Passegers: "+bookedTicket.getPassengers().size()+
				",  Departue Details: "+bookedTicket.getTrain_no()+','+ bookedTicket.getTrain_name()+" "+bookedTicket.getDeparture_time()+" "+bookedTicket.getStart()+" "+bookedTicket.getArrival_time()+" "+bookedTicket.getDestination()+" "+bookedTicket.getAmount() );
		
		javaMailSender.send(message);
		return bookedTicket;
		
	}


	public BookedTicket bookingCancelled(BookedTicket booking,String mailId) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("rudransh3067@gmail.com");
		
		message.setTo(mailId);
		message.setSubject("Booking Cancelled");
		message.setText("Booking Cancelled for PNR Number: "+booking.getPnr()+" If Paid Respective Refund will be initiated shortly (Terms & Conditions Applied)  ");
		
		javaMailSender.send(message);
		return booking;
	}
}
