package cz.cvut.fit.niam1.wsclient;

import task_3.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.datatype.DatatypeFactory;
import java.util.stream.Collectors;

@SpringBootApplication
public class WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(WebServiceClient wsClient) {
        return args -> {
            System.out.println("\n************************************");
            GetBookingsResponse response = wsClient.getBookings();
            System.out.println(response.getBooking().stream().map
                    ( booking -> booking.getPassengerName()+"("+ booking.getDepartureDate()
                            + ", " + booking.getArrivalDate() + ", " + booking.getArrivalAirport()
                                + ", " + booking.getDepartureAirport() +")" ).collect( Collectors.joining( "; " ) ));

            Booking b1 = new Booking();

            b1.setPassengerName("Pepa Novak");
            b1.setDepartureDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-08-14T15:00:00"));
            b1.setArrivalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-08-15T00:30:00"));
            b1.setArrivalAirport(Airport.BERLIN);
            b1.setDepartureAirport(Airport.BRATISLAVA);

            wsClient.addBooking(b1);

            GetBookingsResponse response2 = wsClient.getBookings();
            System.out.println(response2.getBooking().stream().map
                    ( booking -> booking.getPassengerName()+"("+ booking.getDepartureDate()
                            + ", " + booking.getArrivalDate() + ", " + booking.getArrivalAirport()
                            + ", " + booking.getDepartureAirport() +")" ).collect( Collectors.joining( "; " ) ));

            b1.setPassengerName("Pepa Novak");
            b1.setDepartureDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-08-14T20:00:00"));
            b1.setArrivalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-08-15T02:00:00"));
            b1.setArrivalAirport(Airport.BERLIN);
            b1.setDepartureAirport(Airport.WARSAW);

            wsClient.updateBooking(b1);

            GetBookingsResponse response3 = wsClient.getBookings();
            System.out.println(response3.getBooking().stream().map
                    ( booking -> booking.getPassengerName()+"("+ booking.getDepartureDate()
                            + ", " + booking.getArrivalDate() + ", " + booking.getArrivalAirport()
                            + ", " + booking.getDepartureAirport() +")" ).collect( Collectors.joining( "; " ) ));

            wsClient.deleteBooking("Pepa Novak");

            GetBookingsResponse response4 = wsClient.getBookings();
            System.out.println(response4.getBooking().stream().map
                    ( booking -> booking.getPassengerName()+"("+ booking.getDepartureDate()
                            + ", " + booking.getArrivalDate() + ", " + booking.getArrivalAirport()
                            + ", " + booking.getDepartureAirport() +")" ).collect( Collectors.joining( "; " ) ));
            System.out.println("************************************\n");

        };
    }
}