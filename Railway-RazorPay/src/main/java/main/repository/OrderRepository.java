package main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.models.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
	Order findByRazorpayOrderId(String orderId);

}
