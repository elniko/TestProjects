package client;

import entities.ProcessEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by stagiaire on 08/12/2014.
 */
public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        ProcessEntity process = restTemplate.getForObject("http://localhost:8080/TestService/ProcessAdd/xct", ProcessEntity.class);
        System.out.println(process.getId());
    }
}
