package com.example.demo.Usuario;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table
@NoArgsConstructor
public class Usuario {

    @NotNull
    private String nombre;

    @NotNull
    private String email;

    @NotNull
    private String password;
    @NotNull
    private Integer edad;
    private Integer codigoUsuario;
    private String tickerPreferido;
    @SequenceGenerator(
            name = "generador_usuario_secuencia",
            sequenceName = "generador_usuario_secuencia",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "generador_usuario_secuencia"
    )
    @Id
    private Long id;

    public Usuario(String nombre, String email, String password,Integer edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.password = password;
        this.codigoUsuario=email.hashCode();
    }
    public Usuario(String nombre, String email, String password,Integer edad,String tickerPreferido) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.password = password;
        this.codigoUsuario=email.hashCode();
        this.tickerPreferido = tickerPreferido;
    }
    public void borrarTickerPreferido() {
        this.tickerPreferido=null;
    }

}
