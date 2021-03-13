const formChangePass = document.getElementById("formPass");
const contrasena1 = document.getElementById("password");
const contrasena2 = document.getElementById("password2");
const entradoBien = false;
const formularioEmail = document.getElementById("formEmail");
const emailForm = document.getElementById("email");
var emailBueno = null;
formularioEmail.addEventListener("submit",function(e){
    e.preventDefault();
    fetch("http://localhost:8000/api/isUsuario/?apiKey=ClaveDeNacho&email="+emailForm.value)
    .then(res=>{
        return res.json();
    }).then(r=>{
        console.log(r);
        if(r.Existe=="SI"){
            emailBueno = emailForm.value;
            document.getElementById("GranContenedor2").style.visibility='hidden';
            document.getElementById("GranContenedor").style.visibility='visible';
        }else{

        }
    })
})



formChangePass.addEventListener("submit",function(e){
    e.preventDefault();
    fetch("http://localhost:8000/api/actualizarContrasena/conEmail/?apiKey=ClaveDeNacho&email="+emailBueno+"&contrasena="+contrasena1.value+"&contrasenaBis="+contrasena2.value,{
        method:"PUT"
    } ).then(r=>{
        document.getElementById("GranContenedor").style.visibility='hidden';
        document.getElementById("GranContenedor3").style.visibility='visible';
    })
})

/*
window.onload=function(){
    const link = window.location.href;
    var encontradoLink = parseURLParams(link);

    try{
        nombreUsuarioJS = encontradoLink.nombreUsuario[0];
        entradoBien = true;
    }catch(error){

    }
}
*/
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
