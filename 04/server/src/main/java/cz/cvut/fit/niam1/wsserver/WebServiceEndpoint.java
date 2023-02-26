package cz.cvut.fit.niam1.wsserver;

import task_4.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    @Autowired
    private ValidationClient validationClient;

    @PayloadRoot(namespace = "task_4", localPart = "getPaymentsRequest")
    @ResponsePayload
    public GetPaymentsResponse getPayments(@RequestPayload GetPaymentsRequest request) {
        GetPaymentsResponse response = new GetPaymentsResponse();
        response.getPayment().addAll(repository.getPayments());
        return response;
    }

    @PayloadRoot(namespace = "task_4", localPart = "addPaymentRequest")
    @ResponsePayload
    public AddPaymentResponse addPayment(@RequestPayload AddPaymentRequest request) {
        AddPaymentResponse response = new AddPaymentResponse();
        Payment payment = request.getPayment();
        ValidateCardResponse valResponse = validationClient.validateCard(payment.getCardNumber(), payment.getCardOwner());
        if (valResponse.isResult()) repository.addPayment(payment);
        return response;
    }
}
