package cz.cvut.fit.niam1.messaging;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableJms
public class OrderProcessorApplication {

    private final Logger logger = LoggerFactory.getLogger(OrderProcessorApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue bookingQueue;

    @Bean
    public Queue bookingQueue() {
        return new ActiveMQQueue("bookingQueue");
    }

    @Autowired
    private Queue newTripQueue;

    @Bean
    public Queue newTripQueue() {
        return new ActiveMQQueue("newTripQueue");
    }

    @JmsListener(destination = "orderQueue")
    public void readMessage(String message) {
        logger.info("Received order: {}", message);

        String[] split = message.split(", ");

        if (split[1].equals("Booking")){
            jmsTemplate.convertAndSend(bookingQueue, message);
            logger.info ("Sending to booking processor.");
        } else if (split[1].equals("New trip")){
            jmsTemplate.convertAndSend(newTripQueue, message);
            logger.info ("Sending to new trip processor.");
        } else {
            logger.info ("Order name error. {}");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderProcessorApplication.class, args);
    }

}
