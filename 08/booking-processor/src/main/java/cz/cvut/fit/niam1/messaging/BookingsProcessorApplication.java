package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
@EnableJms
public class BookingsProcessorApplication {

    private final Logger logger = LoggerFactory.getLogger(BookingsProcessorApplication.class);

    @JmsListener(destination = "bookingQueue")
    public void readMessage(String message) {
        String[] split = message.split(",");
        logger.info("Processing booking [id: {}, location: {}]", split[0], split[2]);
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingsProcessorApplication.class, args);
    }

}
