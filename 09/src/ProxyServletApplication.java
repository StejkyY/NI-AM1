package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@EnableAutoConfiguration
public class ProxyServletApplication {

    int index = 0;
    List<String> URLs;
    List<Integer> unhealthyServicesIndexes;

    private final Logger logger = LoggerFactory.getLogger(ProxyServletApplication.class);

    public ProxyServletApplication ( ) {
        unhealthyServicesIndexes = new ArrayList<>();
        URLs = new ArrayList<>();
        URLs.add("http://147.32.233.18:8888/MI-MDW-LastMinute1/list");
        URLs.add("http://147.32.233.18:8888/MI-MDW-LastMinute2/list");
        URLs.add("http://147.32.233.18:8888/MI-MDW-LastMinute3/list");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                ResponseEntity responseEntity;
                List<Integer> unhealthyIndexesToRemove = new ArrayList<>();

                for ( Integer i : unhealthyServicesIndexes) {
                    try {
                         responseEntity = restTemplate.exchange(
                                new URI(URLs.get(i)), HttpMethod.GET, null, String.class);
                         if (responseEntity.getStatusCode().value() == 200) {
                             unhealthyIndexesToRemove.add(i);
                         }
                    } catch (Exception e) {

                    }
                }
                unhealthyServicesIndexes.removeAll(unhealthyIndexesToRemove);
            }
        }, 0, 1000);
    }

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping(value = "/test")
    public void test(HttpServletRequest request) throws Exception {
        // copy headers
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(head -> headers.add(head, request.getHeader(head)));
        // create request entity
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        // HTTP
        ResponseEntity responseEntity;

        while (true) {
            try {
                responseEntity = restTemplate.exchange(
                        new URI(URLs.get(index)), HttpMethod.GET, requestEntity, String.class);

                if (responseEntity.getStatusCode().value() == 200) {
                    logger.info("MI-MDW-LastMinute{} status code: 200", index + 1);
                } else if (responseEntity.getStatusCode().value() == 500) {
                    logger.info("MI-MDW-LastMinute{} status code: 500", index + 1);
                    unhealthyServicesIndexes.add(index);
                }
            } catch (Exception e) {
                logger.info("MI-MDW-LastMinute{} error", index + 1);
                unhealthyServicesIndexes.add(index);
            } finally {
                index = (index + 1) % URLs.size();
            }
        }
    }

    public static void main (String[] args){
        SpringApplication.run(ProxyServletApplication.class, args);
    }

}
