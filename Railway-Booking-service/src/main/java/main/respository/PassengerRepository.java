package main.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import main.models.Passenger;

public interface PassengerRepository extends MongoRepository<Passenger, String> {

}
