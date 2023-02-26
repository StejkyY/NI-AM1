package cz.cvut.fit.niam1.wsclient;

import task_4.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class WebServiceClient extends WebServiceGatewaySupport {

    public GetPaymentsResponse getPayments() {
        GetPaymentsRequest request = new GetPaymentsRequest();
        return  (GetPaymentsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

    public AddPaymentResponse addPayment( Payment p ) {
        AddPaymentRequest request = new AddPaymentRequest();
        request.setPayment(p);
        return  (AddPaymentResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

}
