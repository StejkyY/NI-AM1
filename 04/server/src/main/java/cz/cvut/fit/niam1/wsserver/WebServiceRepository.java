package cz.cvut.fit.niam1.wsserver;

import task_4.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<Payment> payments = new ArrayList<>();

    @PostConstruct
    public void initRepo(){
        Payment p1 = new Payment();
        p1.setOrderId("0");
        p1.setCardOwner("CardOwner");
        p1.setCardNumber("1234-1234-1234-1234");
        addPayment(p1);
    }

    public void addPayment(Payment p){
        payments.add(p);
    }

    public List<Payment> getPayments(){
        return payments;
    }


}
