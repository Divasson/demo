package com.example.demo.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(path="/api")
public class UsuarioController {

    private final String apiKey="ClaveDeNacho";
    

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    //*************************************************** */
    //****************GET******************************** */
    //*************************************************** */
    @GetMapping(
            path = "/verTodosUsuarios"
    )
    public List<Usuario> verContenido(@RequestParam("apiKey") String apiKey) {
        List<Usuario> devolver = null;
        if(apiKey==this.apiKey){
            devolver = usuarioService.getUsuarios();
        }
        return devolver;
    }


    @GetMapping(
            path = "/obtenerUsuarioHash"
    )
    @CrossOrigin(origins = "*")
    public Usuario devolverUsuarioHash(@RequestParam("apiKey") String apiKey,@RequestParam("claveHash") Integer claveHash) {
        Usuario devolver = null;
        if(apiKey.equals(this.apiKey)){
            devolver = usuarioService.devolverUsuarioHash(claveHash);
        }
        return devolver;
    }

    @GetMapping(
            path = "/verUsuario"
    )
    @CrossOrigin(origins = "*")
    public Usuario devolverusuario(@RequestParam("apiKey") String apiKey,@RequestParam("email") String email) {
        Usuario devolver = null;
        if(apiKey.equals(this.apiKey)){
            System.out.println("apiIgual");
            devolver = usuarioService.devolverUsuario(email);
        }
        System.out.println(devolver);
        return devolver;
    }

    @GetMapping(
            path = "/isUsuario"
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> isUsuario(@RequestParam("apiKey") String api,@RequestParam("email") String email) {
        ResponseEntity<String> res = null;
        if(api.equals(this.apiKey)){
            
            res = usuarioService.isUsuario(email);
            System.out.println(res);
        }else{
            res = new ResponseEntity<String>("{\"Existe\" : \"NO_API_KEY\"}",HttpStatus.NOT_ACCEPTABLE);
        }
        return res;
    }


    @GetMapping(
            path = "/comprobarUser/",
            produces=MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> comprobarExistenciaUsuario(
            @RequestParam("email") String email,
            @RequestParam("password") String password
            ){
        System.out.println(email);
        System.out.println(password);
        System.out.println(usuarioService.comprobarUser(email,password));
        return usuarioService.comprobarUser(email,password);
    }

    @GetMapping(
            path = "/getNombre",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> getNombreUsuario(@RequestParam("email") String email){
        System.out.println(email);
        System.out.println(usuarioService.getNombreUsuario(email));
        return usuarioService.getNombreUsuario(email);
    }

    @GetMapping(
            path="/detalle/itemx"
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> verDetalle(){
        return new ResponseEntity<>("{\"result\" : \"OK\"}", HttpStatus.ACCEPTED);
    }

    @GetMapping(
        path="/detalle/TickerPreferidoPorUsuario/"
    )
    public ResponseEntity<String> getTickerPreferido(@RequestParam("claveHash") Integer claveHash){
        return usuarioService.getTickerPreferidoUsuario(claveHash);
    }



    @GetMapping(
            path = "/api/contact"
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> contacto(){
        return new ResponseEntity<>("{\"result\" : \"OK\"}", HttpStatus.ACCEPTED);
    }

    
    
    @GetMapping(
        path="detalle/obtenerTickerPreferido"
    )
    @CrossOrigin(origins="*")
    public ResponseEntity<String> obtenerTickerPreferido(@RequestParam("claveHash") Integer id) {
        return usuarioService.obtenerTickerPreferido(id);
        
    } 

    //*************************************************** */
    //*******************POST**************************** */
    //*************************************************** */

    @PostMapping(
            path="/signUp",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario user)throws UsuarioExcepcion{ //mapear el body en un usuario
        System.out.println("Estamos dentro");
        System.out.println(user);
        System.out.println(usuarioOk(user));
        if(usuarioOk(user)){
            System.out.println("Dentro Dentro");
            user.setCodigoUsuario(user.getEmail().hashCode());
            System.out.println(user);
            return usuarioService.anadirNuevoUser(user);
        }else{
            System.out.println("Pass: "+passOK(user.getPassword()));
            System.out.println("Email: "+emailOK(user.getEmail()));
            System.out.println("Nombre: "+nombreOK(user.getNombre()));
            System.out.println("Edad: "+edadOK(user.getEdad()));
            return new ResponseEntity<>("{\"result\":\"KO\"}",HttpStatus.BAD_REQUEST);
        }
        
    }

    //*************************************************** */
    //**********************PUT************************** */
    //*************************************************** */

    //Actualizar Usuario

    @PutMapping(path="actualizar/{idUsuario}")
    @CrossOrigin(origins = "*")
    public void actualizarUsuario(
            @PathVariable(required = true,name="idUsuario") Long id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password
    ){
        if(this.emailOK(email)&&this.nombreOK(nombre)&&this.passOK(password))
            usuarioService.actualizarUser(id,nombre,email,password);
    }

    @PutMapping(path="actualizar/conEmail/")
    @CrossOrigin(origins = "*")
    public void actualizarUsuarioConEmail(
        @PathVariable(required = true,name="apiKey") String key,
        @PathVariable(required = true,name="email") String email,
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) Integer edad
    ){
        if(this.edadOK(edad)&&this.emailOK(email)&&this.nombreOK(nombre))
            if(key.equals(this.apiKey))
                usuarioService.actualizarUserConEmail(email, nombre, edad);   
    }

    @PutMapping(path="actualizarContrasena/conEmail/")
    @CrossOrigin(origins = "*")
    public void actualizarUsuarioConEmail(
        @RequestParam(required = true,name="apiKey") String key,
        @RequestParam(required = true,name="email") String email,
        @RequestParam(required = true) String contrasena,
        @RequestParam(required = true) String contrasenaBis
    ){
        if(this.emailOK(email)&&this.passOK(contrasena))
            if(key.equals(this.apiKey))
                if(contrasena.equals(contrasenaBis))
                    usuarioService.actualizarContrasena(email,contrasena);      
    }

    @PutMapping(value="detalle/anadirTickerPreferido/")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> anadirTickerPreferido(@RequestParam("claveHash") Integer id, @RequestParam("ticker") String ticker) {
        if(this.tickerOK(ticker)){
            return usuarioService.anadirTickerPreferido(id,ticker);
        }else{
            return new ResponseEntity<>("{\"result\" : \"OK\"}", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(value="detalle/borrarTickerPreferido/")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> borrarTickerPreferido(@RequestParam("claveHash") Integer id) {
        return usuarioService.borrarTickerPreferido(id);
    }

    //*************************************************** */
    //********************DELETE************************* */
    //*************************************************** */
    
    @DeleteMapping(path="borrar/{idUsuario}")
    @CrossOrigin(origins = "*")
    public void borrarUsuario(@PathVariable("idUsuario") Long id){
        usuarioService.deleteUsuario(id);
    }

    


    //********************************************************* */
    //****************Funciones Propias************************ */
    //********************************************************* */
    private boolean usuarioOk(Usuario u){
        //https://www.regexplanet.com/advanced/java/index.html
        if(this.edadOK(u.getEdad())&&this.emailOK(u.getEmail())&&this.nombreOK(u.getNombre())&& this.passOK(u.getPassword())){
            System.out.println("Usuario Comprobado");
            return true;   
        }else{
            System.out.println("Usuario NO Comprobado");
            return false;
        }
    }
    private boolean emailOK(String email){
        Pattern pEmail = Pattern.compile("^[A-Za-z0-9._%+-]+@(?:[a-zA-Z0-9-]+)+\\.([a-zA-Z\\.]){2,6}[^.]$");
        Matcher mEmail = pEmail.matcher(email);
        return mEmail.find();
    }
    private boolean passOK(String pass){
        Pattern pPass = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");
        Matcher mPass = pPass.matcher(pass);
        return mPass.find();
    }
    private boolean nombreOK(String nombre){
        Pattern pNombre = Pattern.compile("([A-z])+");
        Matcher mNombre = pNombre.matcher(nombre);
        return mNombre.find();
    }
    private boolean edadOK(Integer edad){
        if(edad>9){
            System.out.println("Edad correcta");
            return true;
        }else{
            System.out.println("Edad NO correcta");
            return false;
        }
    }
    private boolean tickerOK(String ticker){
        Pattern pTicker = Pattern.compile("\\S+");
        Matcher mTicker = pTicker.matcher(ticker);
        return mTicker.find();
    }
}




