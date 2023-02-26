package cz.cvut.fit.niam1.wsserver;

import task_4.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class ValidationClient extends WebServiceGatewaySupport {

    public ValidateCardResponse validateCard( String cardNumber, String cardOwner) {

        ValidateCardRequest request = new ValidateCardRequest();
        request.setCardNumber(cardNumber);
        request.setCardOwner(cardOwner);
        return  (ValidateCardResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

}
