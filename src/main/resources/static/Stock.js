
const Escala = document.getElementById("formTiempo");
const radiosBut = document.getElementsByTagName("inlineRadioOptions");
const form = document.getElementById("form");
const ticker = document.getElementById("ticker");
var results =[];
const ejeX = [];
const ejeY = [];

var usuarioEntrado = [];

function getCheckedValue( groupName ) {
  var radios = document.getElementsByName( groupName );
  for( i = 0; i < radios.length; i++ ) {
      if( radios[i].checked ) {
          return radios[i].value;
      }
  }
  return null;
}

form.addEventListener("submit", function(e){
  e.preventDefault();
  //console.log("http://localhost:8000/api/detalle/tickerPreferido?ticker="+ticker.value+"&interval="+getCheckedValue("inlineRadioOptions"));
  fetch("http://localhost:8000/api/detalle/tickerPreferido/?ticker="+ticker.value+"&interval="+getCheckedValue("inlineRadioOptions"))
  .then(res=>{
    console.log("Response here");
    console.log(res);
    return res.json()
  }).then(r=>{
    results=r;
    console.log(r);
    sacarEjes();
    pintar();
  }).catch(e=>{
    alert(e);
    console.error("error "+e)
  })
  return false;
});

function sacarEjes(){
  ejeX.length=0;
  ejeY.length=0;
  console.log(results.values)
  results.values.forEach(valor=>{
    ejeX.push(valor.datetime);
    ejeY.push(valor.close)
  })
  ejeX.reverse();
  ejeY.reverse();
}




function pintar(){
  const ctx = document.getElementById('myDiv').getContext('2d');
  //Chart.defaults.global.defaultFontColor='white';
  const myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ejeX,
        datasets: [{
            label: 'Precio de '+ticker.value.toUpperCase()+"\t tipo:"+results.meta.type.toUpperCase(),
            data: ejeY,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)'
            ],
            borderWidth: 2,
            pointBorderColor:  'rgba(255, 99, 132, 1)',
            pointHoverRadius:7,
            pointHoverBorderColor: 'rgb(255,0,0,1)'
        }]
    },
    options: {
        legend: {
            labels: {
                fontColor: 'white'
            }
        },
        scales: {
            yAxes: [{
                
                ticks: {
                    beginAtZero: false,
                    fontColor: 'white'
                }
            }],
            xAxes:[{
                ticks:{
                    fontColor: 'white'
                }
            }]
        }
    }
});
  
}


function cambiarEscala(nuevaEscala){
  //fetch('https://api.twelvedata.com/time_series?symbol='+ ticker.value+'&interval='+ nuevaEscala+'&apikey=a2662782ffe54111b35a2e0c9569dbdf')
  fetch("http://localhost:8000/api/detalle/tickerPreferido/?ticker="+ticker.value+"&interval="+nuevaEscala)
  .then(res => {
    console.log("Response here")
    return res.json()
  })
  .then(r => {
    results= r;
    console.log(r);
    sacarEjes();
    pintar();
  })
}

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

window.onload = function(){
  const link = window.location.href;
  var variables = parseURLParams(link);
  var usuarioTot = null;
  if(variables==0){
    document.getElementById('granContenedor').style.display = 'none'; //hide
    document.getElementById('granContenedor').style.visibility = 'hidden';      // hide
    alert("Te estás queriendo colar!");
  }else{
    console.log(variables.codUsuario[0]);

    fetch("http://localhost:8000/api/obtenerUsuarioHash/?apiKey=ClaveDeNacho&claveHash="+variables.codUsuario[0])
    .then(res=>{
      return res.json();
    })
    .then(r=>{
      console.log(r);
      document.getElementById("Etiqueta_con_Nombre").innerHTML = "Dashboard Financiero de "+r.nombre;
    });    
  }
}

function cambiarAMuchasGraficas(){
  const link = window.location.href;
  var variables = parseURLParams(link);
  try{
    //console.log(variables.nombre[0]);
    window.location.href="MuchasGraficas.html?codUsuario="+variables.codUsuario[0];
  }catch(error){
    window.location.href="MuchasGraficas.html";
  }
}

function cambiarATickerPref(){
  const link = window.location.href;
  var variables = parseURLParams(link);
  try{
    //console.log(variables.nombre[0]);
    window.location.href="TickerPreferido.html?codUsuario="+variables.codUsuario[0];
  }catch(error){
    window.location.href="TickerPreferido.html";
  }
}

function obtenerUsuario(hashcode){
  var us = null;
  fetch("http://localhost:8000/api/obtenerUsuarioHash/?apiKey=ClaveDeNacho&claveHash="+hashcode)
  .then(res=>{
    return res.json();
  })
  .then(r=>{
    console.log(r);
    us = r;
    console.log(us);
  });
  console.log("caracola");

  return us;
}