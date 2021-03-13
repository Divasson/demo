package com.example.demo.Ticker;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class TickerService {

    @Autowired
    private RestTemplate restTemplate;




	public ResponseEntity<Ticker> obtenerTicker(String ticker,String intervalo) {
                String twApiResourceUrl = "https://api.twelvedata.com/time_series";
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(twApiResourceUrl)
                        .queryParam("apikey","a2662782ffe54111b35a2e0c9569dbdf")
                        .queryParam("symbol",ticker)
                        .queryParam("interval",intervalo);


                HttpEntity<?> entity = new HttpEntity<>(headers);

                HttpEntity<Ticker> response = restTemplate.exchange(
                        builder.toUriString(), 
                        HttpMethod.GET, 
                        entity, 
                        Ticker.class);
                System.out.println(response.getBody().getMeta());
                System.out.println(response.getBody().getValues());
		return new ResponseEntity<Ticker>(response.getBody(),HttpStatus.ACCEPTED);
	}
        public ResponseEntity<MuchosTickers> muchosTickers(String Ticker,String intervalo){
                String twApiResourceUrl = "https://api.twelvedata.com/time_series";
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(twApiResourceUrl)
                        .queryParam("apikey","a2662782ffe54111b35a2e0c9569dbdf")
                        .queryParam("symbol",Ticker)
                        .queryParam("interval",intervalo);


                HttpEntity<?> entity = new HttpEntity<>(headers);

                HttpEntity<MuchosTickers> response = restTemplate.exchange(
                        builder.toUriString(), 
                        HttpMethod.GET, 
                        entity, 
                        MuchosTickers.class);

		return new ResponseEntity<MuchosTickers>(response.getBody(),HttpStatus.ACCEPTED);
        }

}
