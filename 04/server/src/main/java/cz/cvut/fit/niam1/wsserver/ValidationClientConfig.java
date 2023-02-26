package cz.cvut.fit.niam1.wsserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ValidationClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("task_4");
        return marshaller;
    }

    @Bean
    public ValidationClient wsClient(Jaxb2Marshaller marshaller) {
        ValidationClient valClient = new ValidationClient();
        valClient.setDefaultUri("http://147.32.233.18:8888/NI-AM1-CardValidation/ws");
        valClient.setMarshaller(marshaller);
        valClient.setUnmarshaller(marshaller);
        return valClient;
    }
}


