package com.example.demo.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public ResponseEntity<String> anadirNuevoUser(Usuario user) throws UsuarioExcepcion {
        System.out.println(user);
        Optional<Usuario> usuarioEmail=usuarioRepository
                .findUsuarioByEmail(user.getEmail());
        ResponseEntity<String> res = new ResponseEntity<String>("{\"result\" : \"EMAIL_TAKEN\"}", HttpStatus.ACCEPTED);
        if(usuarioEmail.isPresent()){
            throw new UsuarioExcepcion("email taken");

        }else{
            System.out.println("Dentro dentro");
            usuarioRepository.save(user);
            System.out.println(user);
            res = new ResponseEntity<String>("{\"result\" : \"OK\"}", HttpStatus.ACCEPTED);
        }
        return res;

    }

    public void deleteUsuario(Long id) {
        if(!usuarioRepository.existsById(id)){
            throw new IllegalStateException("ese id no existe");
        }else{
            usuarioRepository.deleteById(id);
        }

    }

    public Usuario devolverUsuario(String email){
        Usuario u = null;
        if(usuarioRepository.findUsuarioByEmail(email).isPresent()){
            u=usuarioRepository.findUsuarioByEmail(email).get();
        }
        return u;
    }

    @Transactional
    public void actualizarUser(Long id, String nombre, String email, String password) {
        if(!usuarioRepository.existsById(id)){
            throw new IllegalStateException("ese id ("+id+") no existe");
        }else{
            Usuario u =usuarioRepository.findById(id).get();
            if(!usuarioRepository.findUsuarioByEmail(email).isPresent() && !Objects.isNull(email)) {
                u.setEmail(email);
            }
            if(!Objects.isNull(nombre)) {
                u.setNombre(nombre);
            }
            if(!Objects.isNull(password)) {
                u.setPassword(password);
            }
        }
    }

    @Transactional
    public void actualizarUserConEmail(String email, String nombre, Integer edad) {
        if(!Objects.isNull(usuarioRepository.findUsuarioByEmail(email).get())){
            throw new IllegalStateException("ese email ("+email+") no existe");
        }else{
            Usuario u =usuarioRepository.findUsuarioByEmail(email).get();
            if(!Objects.isNull(nombre)) {
                u.setNombre(nombre);
            }
            if(!Objects.isNull(edad)) {
                u.setEdad(edad);
            }
        }
    }

    public ResponseEntity<String> comprobarUser(String email, String password) {
        String comprobar="No_Encontrado";
        HttpStatus statusHTTP = HttpStatus.NOT_ACCEPTABLE;
        System.out.println("dentro Funcion");
        System.out.println(email);
        System.out.println(password);
        if(usuarioRepository.findUsuarioByEmail(email).isPresent()){
            comprobar=usuarioRepository.findUsuarioByEmail(email).get().toString();
            if(usuarioRepository.findUsuarioByEmail(email).get().getPassword().equals(password)){
                comprobar="Encontrado";
                statusHTTP=HttpStatus.ACCEPTED;
            }
        }
        return new ResponseEntity<>(String.format("{\"result\" : \"%s\"}",comprobar),statusHTTP);
    }

    public ResponseEntity<String> getNombreUsuario(String email) {
        ResponseEntity<String> respuesta = null;
        if(usuarioRepository.findUsuarioByEmail(email).isPresent()){
            System.out.println("Estamos dentro");
            respuesta = new ResponseEntity<String>(String.format("{\"nombre\" : \"%s\"}",usuarioRepository.findUsuarioByEmail(email).get().getNombre()),HttpStatus.ACCEPTED);
        }else{
            respuesta = new ResponseEntity<String>("{\"nombre\" : \"null\"}",HttpStatus.NOT_ACCEPTABLE);
        }
        return respuesta;
    }

    @Transactional
	public void actualizarContrasena(String email, String contrasena) {

        if(Objects.isNull(usuarioRepository.findUsuarioByEmail(email).get())){
            throw new IllegalStateException("ese email ("+email+") no existe");
        }else{
            Usuario u =usuarioRepository.findUsuarioByEmail(email).get();
            if(!Objects.isNull(contrasena)) {
                u.setPassword(contrasena);
            }
            
        }

	}

	public ResponseEntity<String> isUsuario(String email) {
        ResponseEntity<String> respuesta = null;
        if(usuarioRepository.findUsuarioByEmail(email).isPresent()){
            respuesta = new ResponseEntity<String>("{\"Existe\" : \"SI\"}",HttpStatus.ACCEPTED);
        }else{
            respuesta = new ResponseEntity<String>("{\"Existe\" : \"NO\"}",HttpStatus.NOT_ACCEPTABLE);
        }
        return respuesta;
	}

	public Usuario devolverUsuarioHash(Integer claveHash) {
		Usuario u = null;
        if(usuarioRepository.findUsuarioByCodigoUsuario(claveHash).isPresent()){
            u=usuarioRepository.findUsuarioByCodigoUsuario(claveHash).get();
        }

        return u;
	}

    public ResponseEntity<String> getTickerPreferidoUsuario(Integer claveHash) {
        ResponseEntity<String> res = new ResponseEntity<>("{\"response\" : \"KO\",\"ticker\" : \"null\"}", HttpStatus.BAD_REQUEST);
        if(usuarioRepository.findUsuarioByCodigoUsuario(claveHash).isPresent()){
            res=new ResponseEntity<>("{\"response\" : \"OK\",\"ticker\" : "+usuarioRepository.findUsuarioByCodigoUsuario(claveHash).get().getTickerPreferido() +"}", HttpStatus.ACCEPTED);
        }
        return res;
    }

    @Transactional
    public ResponseEntity<String> anadirTickerPreferido(Integer id, String ticker) {
        ResponseEntity<String> res = new ResponseEntity<>("{\"result\":\"KO\"}",HttpStatus.BAD_REQUEST );
        if(Objects.isNull(usuarioRepository.findUsuarioByCodigoUsuario(id).get())){
            System.out.println("No encontrado");
            throw new IllegalStateException("ese id ("+id+") no existe");
        }else{
            Usuario u = usuarioRepository.findUsuarioByCodigoUsuario(id).get();
            u.setTickerPreferido(ticker);
            res = new ResponseEntity<>("{\"result\":\"OK\"}", HttpStatus.ACCEPTED);
        }
        return res;
    }

    public ResponseEntity<String> obtenerTickerPreferido(Integer id) {
        ResponseEntity<String> res = new ResponseEntity<>("{\"result\":\"KO\"}",HttpStatus.BAD_REQUEST );
        if(Objects.isNull(usuarioRepository.findUsuarioByCodigoUsuario(id).get())){
            System.out.println("No encontrado");
            throw new IllegalStateException("ese id ("+id+") no existe");
        }else{
            if(Objects.isNull(usuarioRepository.findUsuarioByCodigoUsuario(id).get().getTickerPreferido())){
                res = new ResponseEntity<>("{\"result\":\"OK\",\"valorTicker\":\"NULL\"}", HttpStatus.ACCEPTED);
            }else{
                res = new ResponseEntity<>("{\"result\":\"OK\",\"valorTicker\":\""+usuarioRepository.findUsuarioByCodigoUsuario(id).get().getTickerPreferido() + "\"}", HttpStatus.ACCEPTED);
            }
        }
        return res;
    }

    @Transactional
    public ResponseEntity<String> borrarTickerPreferido(Integer id) {
        ResponseEntity<String> res = new ResponseEntity<>("{\"result\":\"KO\"}",HttpStatus.BAD_REQUEST );
        if(Objects.isNull(usuarioRepository.findUsuarioByCodigoUsuario(id).get())){
            System.out.println("No encontrado");
            throw new IllegalStateException("ese id ("+id+") no existe");
        }else{
            Usuario u = usuarioRepository.findUsuarioByCodigoUsuario(id).get();
            u.borrarTickerPreferido();
            res = new ResponseEntity<>("{\"result\":\"OK\"}", HttpStatus.ACCEPTED);
        }
        return res;
    }
}
