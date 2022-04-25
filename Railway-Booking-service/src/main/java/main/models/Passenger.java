package main.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Passenger {
	@Id
	private String passengerId;
	private long pnr;
    private String passenger_name;
    private int age;
    private long contactNumber;
    private String gender;
    private String email;
    private LocalDate dateOfJourney;
    private int seat_no; 
}
