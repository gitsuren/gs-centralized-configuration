package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConfigSecondClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigSecondClientApplication.class, args);
    }
}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${meaning:Hello default meaning}")
    private String meaning;

    @RequestMapping("/meaning")
    String getMeaning() {
        return this.meaning;
    }

}

@RestController
class WhatCanIDo {

    @GetMapping("/hmm")
    String hmm(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8082/refresh";
        String requestJson = "{}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String answer = restTemplate.postForObject(url, entity, String.class);
        System.out.println(answer);
        return answer;
    }
}