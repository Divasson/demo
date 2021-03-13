package com.example.demo.Ticker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExcepcionTicker extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private static String razon;

    public ExcepcionTicker(String error) {
        super(error);
    }
    
}
