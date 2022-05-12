package main.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BOOKED_TICKETS")
public class BookedTicket {
	@Id
	private String bookId;
	private Long pnr;
    private String train_no;
    private String train_name;
    private String start;
    private String destination;
    private String class_name;
    private LocalTime departure_time;
    private LocalTime arrival_time;
    private List<Passenger> passengers;
    private String quota;
    private Long transactional_id;
    private Long account_no;
    private String email_address;
    private String status;
    private LocalDateTime booking_time;
    private double amount;
}
