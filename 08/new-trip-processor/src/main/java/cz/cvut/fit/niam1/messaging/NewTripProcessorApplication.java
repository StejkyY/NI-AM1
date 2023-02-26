package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
@EnableJms
public class NewTripProcessorApplication {

    private final Logger logger = LoggerFactory.getLogger(NewTripProcessorApplication.class);

    @JmsListener(destination = "newTripQueue")
    public void readMessage(String message) {
        String[] split = message.split(",");
        logger.info("Processing new trip [id: {}, name: {}]", split[0], split[2]);
    }

    public static void main(String[] args) {
        SpringApplication.run(NewTripProcessorApplication.class, args);
    }

}
