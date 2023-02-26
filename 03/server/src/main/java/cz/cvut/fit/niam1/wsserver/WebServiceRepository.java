package cz.cvut.fit.niam1.wsserver;

import task_3.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<Booking> bookings = new ArrayList<>();

    @PostConstruct
    public void initRepo() throws DatatypeConfigurationException {
        Booking b1 = new Booking();
        b1.setPassengerName("Jan Stejskal");
        b1.setDepartureDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-04-04T11:15:00"));
        b1.setArrivalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-04-04T15:15:00"));
        b1.setArrivalAirport(Airport.PRAGUE);
        b1.setDepartureAirport(Airport.VIENNA);
        bookings.add(b1);
    }

    public void addBooking(Booking b){
        bookings.add(b);
    }

    public List<Booking> getBookings(){
        return bookings;
    }

    public void deleteBooking(String passengerName){
        bookings.removeIf(b -> b.getPassengerName().equals(passengerName));
    }

    public void updateBooking(Booking b){
        int index = 0;
        for (int i = 0; i < bookings.size(); ++i ){
            if (bookings.get(i).getPassengerName().equals(b.getPassengerName()))
            {
                index = i;
                break;
            }
        }
        bookings.set(index, b);
    }


}
