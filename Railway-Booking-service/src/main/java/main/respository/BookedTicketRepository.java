package main.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import main.models.BookedTicket;

public interface BookedTicketRepository extends MongoRepository<BookedTicket, String> {

}
