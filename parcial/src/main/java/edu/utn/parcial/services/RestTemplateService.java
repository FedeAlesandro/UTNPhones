package edu.utn.parcial.services;

import edu.utn.parcial.models.BillsForUsers;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class RestTemplateService {

    RestTemplate restTemplate;

    @PostConstruct
    private void init(){
        restTemplate = new RestTemplateBuilder()
                .build();
    }

    public ResponseEntity<BillsForUsers[]> getBills(String token, Date date1, Date date2){
        System.out.println(date1);
        System.out.println(date2);
        String url = "http://localhost:8081/api/bills?date1={date1}&date2={date2}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", token);

        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, BillsForUsers[].class, date1, date2);
    }

}
