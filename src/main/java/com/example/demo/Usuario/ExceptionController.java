package com.example.demo.Usuario;

import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    @GetMapping("/citiesex")
    public List<String> getCitiesException() throws UsuarioExcepcion {
        throw new UsuarioExcepcion("Error");
    }

    @ExceptionHandler(UsuarioExcepcion.class)
    public String handleError(UsuarioExcepcion e) {
        return "Sorry";
    }

    @GetMapping("/citiesex2")
    public List<String> getCitiesException2() {
        throw new RuntimeException("Error");
    }

    @GetMapping("/citiesex3")
    public List<String> getCitiesException3() throws UsuarioExcepcion {
        throw new UsuarioExcepcion("Error");
    }
}
