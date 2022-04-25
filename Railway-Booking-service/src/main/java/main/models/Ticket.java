package main.models;


import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Ticket {

	@Id
	private String ticketId;
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
    private String status;
    private Long transactional_id;

}
