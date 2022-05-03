package main.models;

import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookedTicket {
	
	private String bookId;
    private Ticket ticket;
    private Long transactional_id;
    private Long account_no;
    private String email_address;
    private String status;
    private LocalDateTime booking_time;
    private double amount;
}
