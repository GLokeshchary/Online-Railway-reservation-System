package main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.models.Seat;

@Repository
public interface SeatRepository extends MongoRepository<Seat, String> {

}
