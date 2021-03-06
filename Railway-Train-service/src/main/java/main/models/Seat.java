package main.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Seats")
public class Seat {
	@Id
	private String id;
	private String trainNo;
	private String coach;
	private int totalSeats;
	private int waitingList;
	private double price;
	private double fare;

}
