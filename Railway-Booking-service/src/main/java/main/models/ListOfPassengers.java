package main.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonFormat(shape = JsonFormat.Shape.ARRAY)
//@JsonPropertyOrder({"passengers"})
public class ListOfPassengers {
	private List<Passenger> passengers;
}
