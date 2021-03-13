package com.example.demo.Usuario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UsuarioConfig {

    @Bean
    CommandLineRunner commandLineRunner(UsuarioRepository repository){
        return args -> {
            Usuario nacho=new Usuario(
                    "nacho",
                    "nacho@divasson.com",
                    "Contrasena",
                    20
            );
            Usuario alex=new Usuario(
                    "alex",
                    "alex@divasson.com",
                    "Contrasena",
                    21
            );
            //repository.saveAll(List.of(nacho,alex));
        };
    }

}
