package com.mycompany.notification;

import com.mycompany.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {
        "com.mycompany.notification",
        "com.mycompany.amqp"
})
//@EnableEurekaClient
//@EnableFeignClients(
//        basePackages = "com.mycompany.clients"
//)
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(
//            RabbitMQMessageProducer producer,
//            NotificationConfig notificationConfig){
//        return args -> {
//            producer.publish(new Person("Milos", 25),
//                    notificationConfig.getInternalExchange(),
//                    notificationConfig.getInternalNotificationRoutingKey());
//        };
//    }
//
//    record Person(String name, int age){}
}
