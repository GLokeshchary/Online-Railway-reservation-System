package main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.models.Train;

@Repository
public interface TrainRepository extends MongoRepository<Train, String>{

}
