package main.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import main.models.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String> {

}
