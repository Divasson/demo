package com.example.demo.Ticker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class TickerController {

    private final String twelveDataApiKey = "&apikey=a2662782ffe54111b35a2e0c9569dbdf";


    @Autowired
    private final TickerService tickerService;

    @Autowired
    public TickerController(TickerService tickerService){
        this.tickerService=tickerService;
    }

    //Lo que se pide

    @GetMapping(
        path = "/detalle/tickerPreferido/"
        //produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<Ticker> obtenerTicker(@RequestParam("ticker") String ticker,@RequestParam("interval") String interval){
        ResponseEntity<String> res = new ResponseEntity<String>("",HttpStatus.BAD_REQUEST);
        System.out.println("Dentro de primera funcion");
        ResponseEntity<Ticker> rese = this.tickerService.obtenerTicker(ticker,interval);
        System.out.println(rese);
        return rese;
    }
    @GetMapping(
        path = "/detalle/MuchosTickers/"
        //produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<MuchosTickers> muchosTickers(@RequestParam("ticker") String ticker,@RequestParam("interval") String interval){
        ResponseEntity<String> res = new ResponseEntity<String>("",HttpStatus.BAD_REQUEST);
        System.out.println("Dentro de primera funcion");
        ResponseEntity<MuchosTickers> rese = this.tickerService.muchosTickers(ticker,interval);
        System.out.println(rese);
        return rese;
    }

}
