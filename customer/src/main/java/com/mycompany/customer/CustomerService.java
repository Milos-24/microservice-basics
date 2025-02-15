package com.mycompany.customer;

import com.mycompany.amqp.RabbitMQMessageProducer;
import com.mycompany.clients.fraud.FraudClient;
import com.mycompany.clients.notification.NotificationClient;
import com.mycompany.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient, RabbitMQMessageProducer rabbitMQMessageProducer) {

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        customerRepository.saveAndFlush(customer);

//          Instead of this we can use OpenFeign from Spring Cloud
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );
//          Now we don't need FraudCheckResponse within customer package
//          You only need to import the clients module into this modules pom file
        com.mycompany.clients.fraud.FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(Objects.requireNonNull(fraudCheckResponse).isFraudster()) {
            throw new IllegalStateException("FRAUD");
        }
        else {

            //Ovo je sinhroni nacin slanja
//            notificationClient.sendNotification(new NotificationRequest(
//                    customer.getId(),
//                    customer.getEmail(),
//                    "Welcome "+
//                    customer.getFirstName()
//            ) );

            rabbitMQMessageProducer.publish(
                    new NotificationRequest(
                            customer.getId(),
                            customer.getEmail(),
                            "Welcome "+
                                    customer.getFirstName()
                    ),
                    "internal.exchange",
                    "internal.notification.routing-key"
            );
        }

    }
}
