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
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableJms
public class OrderClientApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(OrderClientApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("orderQueue");
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int id = 0;
        while (true) {
            Thread.sleep(5000);
            int randomSelection = new Random().nextInt(2);
            int random = new Random().nextInt(3);

            LocalDateTime time = LocalDateTime.now();
            logger.info("Sending order to {} at {}", queue.getQueueName(), time);

            String[] locations = {"Location 1", "Location 2", "Location 3"};
            String[] tripNames = {"Trip name 1", "Trip name 2", "Trip name 3"};

            if (randomSelection == 1){
                jmsTemplate.convertAndSend(queue,id + ", Booking, " + locations[random] );
            } else {
                jmsTemplate.convertAndSend(queue, id + ", New trip, " + tripNames[random]);
            }
            id++;
        }
    }

}
