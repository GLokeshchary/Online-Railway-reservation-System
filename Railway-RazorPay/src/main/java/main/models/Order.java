package main.models;
import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ORDER")
public class Order {
	@Id
    private String userId;
 
    private String razorpayPaymentId;
 
    private String razorpayOrderId;
 
    private String razorpaySignature;

}
