package cz.cvut.fit.niam1.wsclient;

import task_3.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class WebServiceClient extends WebServiceGatewaySupport {

    public GetBookingsResponse getBookings() {

        GetBookingsRequest request = new GetBookingsRequest();
        return  (GetBookingsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

    public AddBookingResponse addBooking( Booking b ) {

        AddBookingRequest request = new AddBookingRequest();
        request.setBooking(b);
        return  (AddBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

    public DeleteBookingResponse deleteBooking(String passengerName) {

        DeleteBookingRequest request = new DeleteBookingRequest();
        request.setName(passengerName);
        return  (DeleteBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

    public UpdateBookingResponse updateBooking( Booking b ) {

        UpdateBookingRequest request = new UpdateBookingRequest();
        request.setBooking(b);
        return  (UpdateBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

}
