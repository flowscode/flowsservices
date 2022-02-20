package com.flowcode.customer;

import com.flowcode.amqp.RabbitMQMessageProducer;
import com.flowcode.clients.fraud.FraudCheckResponse;
import com.flowcode.clients.fraud.FraudClient;
import com.flowcode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service //creates Bean, so I can inject into controller
@AllArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer producer;

    public void registerCustomer(CustomerRegistrationRequest request) throws IllegalStateException {
        Customer customer = Customer.builder() //@Builder is from Lombok, added to the model class.
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer); // store customer in db
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        System.out.println(fraudCheckResponse);

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()){
            Integer customerId = customer.getId();
            customerRepository.deleteById(customer.getId());
            customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("Fraudster"));
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to flowcode...", customer.getFirstName())
        );

        producer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
