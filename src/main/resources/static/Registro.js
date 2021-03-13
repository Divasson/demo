const container = document.getElementById('container');
const inputEmailSignIn = document.getElementById("emailSignIn");
const inputPassSignIn = document.getElementById("passwordSignIn");
const formSignIn = document.getElementById("formSignIn");
const formSignUp = document.getElementById("formSignUp");

var resultado = [];
var resultadoFetchComprobar=[];

const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});



formSignUp.addEventListener("submit",function(e){
    e.preventDefault();
    if(validateEmail(document.getElementById("emailSignUp").value) && checkPassword(document.getElementById("passwordSignUp").value) && parseInt(document.getElementById("edadSignUp").value)>9 && validateNombre(document.getElementById("nombreSignUp").value) ){
        const data = { nombre: document.getElementById("nombreSignUp").value,email: document.getElementById("emailSignUp").value,password: document.getElementById("passwordSignUp").value ,edad: document.getElementById("edadSignUp").value };
        console.log(data);
        fetch("http://localhost:8000/api/signUp/",{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
            }).then(res=>{
            console.log("Response here");
            return res.json();
            }).then(r=>{
                console.log("Respuesta2");
                console.log(r);
                if(r.result=="OK"){
                    alert("Usuario Añadido");
                    container.classList.remove("right-panel-active");
                    
                }else{
                    
                    alert("Alguien con ese email ya forma parte de la plataforma");
                }
        })
    }else{
        if(validateEmail(document.getElementById("emailSignUp").value)){
            if(checkPassword(document.getElementById("passwordSignUp").value)){
                if(parseInt(document.getElementById("edadSignUp").value)>9){
                    alert("El formato del nombre no es válido");
                }else{
                    alert("La edad no es válida. Debes tener 10 años o más");
                }
            }else{
                alert("La contraseña no es válida.Debe tener como mínimo 6 dígitos, Mayúsculas, minúsculas y al menos 1 dígito")
            }
        }else{
            alert("El email no tiene el formato válido");
        }
        
    }
    
    return false;
})

async function fetchParEmailPass(email,password){
    var resultado = null;
    await fetch("http://localhost:8000/api/comprobarUser/?email="+email+"&password="+password)
    .then(res=>{
        console.log("respuesta");
        return res.json();
    })
    .then(r=>{
        console.log(r);
        resultado = r;
        console.log(r.result);
        if(resultado.result=="Encontrado"){
            console.log("encontrado");
            resultado = fetchUsuario(email);
        }else{

        }
    })
    return resultado;
}

async function fetchUsuario(email){
    var resultado = null;
    await fetch("http://localhost:8000/api/verUsuario/?apiKey=ClaveDeNacho&email="+email)
    .then(res=>{
        console.log("respuesta 2");
        return res.json();
    }).then(r=>{
        resultado = r.codigoUsuario;
        return r.codigoUsuario;
    }).then(r=>{

    })
    return resultado;
}

function validateEmail(email) {
    console.log(email);
    
    const re = /^[A-Za-z0-9._%+-]+@(?:[a-zA-Z0-9-]+)+\.([a-zA-Z\.]){2,6}[^.]$/;
    console.log(re.test(email));
    return re.test(email);
}
function checkPassword(str)
 {
    // at least one number, one lowercase and one uppercase letter
    // at least six characters
    var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
    console.log(re.test(str));
    console.log(str);
    return re.test(str);
}
function validateNombre(nombre){
    var re = /([A-z])+/
    return re.test(nombre)
}

formSignIn.addEventListener("submit", function(e){
    e.preventDefault();
    if(validateEmail(document.getElementById("emailSignIn").value) && checkPassword(document.getElementById("passwordSignIn").value)){
        fetch("http://localhost:8000/api/comprobarUser/?email="+document.getElementById("emailSignIn").value+"&password="+document.getElementById("passwordSignIn").value)
        .then(res=>{
            console.log("respuesta");
            return res.json();
        })
        .then(r=>{
            console.log(r);
            resultado = r;
            console.log(r.result);
            if(resultado.result=="Encontrado"){
                console.log("encontrado");
                var resultado = null;
                fetch("http://localhost:8000/api/verUsuario/?apiKey=ClaveDeNacho&email="+document.getElementById("emailSignIn").value)
                .then(res=>{
                    console.log("respuesta 2");
                    return res.json();
                }).then(r=>{
                    resultado = r.codigoUsuario;
                    console.log(resultado);
                    window.location.href="Dashboard.html?codUsuario="+resultado;
                    return r.codigoUsuario;
                })
                //window.location.href="Dashboard.html?codUsuario="+document.getElementById("emailSignIn").value;
                //resultado = fetchUsuario(email);

            }else{
                alert("El par (email contraseña) no existe");
                container.classList.add("right-panel-active");
            }
        })
    }else{
        alert("El email y contraseña no son válidos")
    }
    


    return false;
})


  function parseURLParams(url) {
    var queryStart = url.indexOf("?") + 1,
        queryEnd   = url.indexOf("#") + 1 || url.length + 1,
        query = url.slice(queryStart, queryEnd - 1),
        pairs = query.replace(/\+/g, " ").split("&"),
        parms = {}, i, n, v, nv;
  
    if (query === url || query === "") return 0;
  
    for (i = 0; i < pairs.length; i++) {
        nv = pairs[i].split("=", 2);
        n = decodeURIComponent(nv[0]);
        v = decodeURIComponent(nv[1]);
  
        if (!parms.hasOwnProperty(n)) parms[n] = [];
        parms[n].push(nv.length === 2 ? v : null);
    }
    return parms;
  }

  window.onload=function(){
    const link = window.location.href;
    var encontradoLink = parseURLParams(link);
    console.log(link);
    try{
        //console.log(encontradoLink);
        if(encontradoLink.encontrado[0]=="false"){
            let html = "";
            html += "<h2>"+
                "El usuario y contraseña no coinciden con los de la base de datos"+
                "</h2>";
            document.getElementById("contenedorInformativo").innerHTML = html;
        }else{

        }
    }catch(error){

    }
  }