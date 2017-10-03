package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${message:Hello default message}")
    private String message;

    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
//
//    @RequestMapping("/error")
//    String OnError() {
//        return "Uh -Oh!!!";
//    }
}

@RestController
class WhatCanIDo {

    @GetMapping("/hmm")
    String hmm(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/refresh";
        String requestJson = "{}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String answer = restTemplate.postForObject(url, entity, String.class);
        System.out.println(answer);
        return answer;
    }
}