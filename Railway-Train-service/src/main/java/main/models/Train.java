package main.models;

import java.time.LocalTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Trains")
public class Train {

	@Id
	private String id;
	private String trainNo;
	private String trainName;
	private String depatureStation;
	private String arrivalStation;
	private LocalTime depatureTime;
	private LocalTime arrivalTime;
	private String[] run_days;
	private Map<String,Seat> classes;
	private boolean isFare;
	private boolean active;
	private String quota;
}
