package cz.cvut.fit.niam1.wsclient;

import task_4.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import task_4.GetPaymentsResponse;
import task_4.Payment;

import java.util.stream.Collectors;

@SpringBootApplication
public class WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(WebServiceClient wsClient) {
        return args -> {
            System.out.println("\n*****************************");
            GetPaymentsResponse response = wsClient.getPayments();
            System.out.println(response.getPayment().stream().map(
                    payment -> payment.getOrderId() +"("+ payment.getCardOwner() +
                            ", " + payment.getCardNumber() +")" ).collect( Collectors.joining( "; " ) ));

            Payment p1 = new Payment();
            p1.setOrderId("1");
            p1.setCardOwner("CardOwner");
            p1.setCardNumber("1234-1234-1234-1234");
            wsClient.addPayment(p1);

            GetPaymentsResponse response2 = wsClient.getPayments();
            System.out.println(response2.getPayment().stream().map(
                    payment -> payment.getOrderId() +"("+ payment.getCardOwner() +
                            ", " + payment.getCardNumber() +")" ).collect( Collectors.joining( "; " ) ));
            System.out.println("*****************************\n");
        };
    }
}