var WBin;
var random = Math.floor((Math.random() * 100000) + 1);
var signalcarrier = "" + random;



//assegurarse que solo hay una conexion abierta al mismo tiempo
function openWBin(signalcarrier){
    console.log("Ejecutando openSoocketWBin()");  
    
    //alert("ejecutando openWBin");
    //direccion puerto websocket
    var direccion = "wss://erp.dafnube.com:48643/wstest/WBinStart/" + signalcarrier;
    WBin = new WebSocket(direccion);
    
    console.log("websocket info: " + WBin.toString());
     sendUserLogin(signalcarrier);
    WBin.onopen = function(event){
        if(event.data === undefined){
            return;
            console.log("event.data undefined");
        }
            
       
        //alert("ejecutando onOpen");
        write(event.data);
    }
    WBin.onmessage = function(event){
        console.log("mensaje recibido")
        var message = JSON.parse(event.data);
        processMessage(message);
        write("Mensaje recibido" + event.data);
        //gestionar mensaje (procesar mensaje)
    }
    WBin.onclose = function(event){
        write("connexion closed. " + event.data);
    }
        
}

function processMessage(message){
    if(message.type === "WsMsgAccept"){
        alert("Mensaje confirmacion recibido");
        window.location.replace("https://erp.dafnube.com:48643/Loggin/events.html");
    }
}


function sendUserLogin(){
    console.log("Dentro de sendUserLogin()");
    console.log("User: " + username);
   
    
   
    waitForSocketConnection(WBin, function(){
        //WBin.send("hola!!");
         WBin.send(JSON.stringify({type: "WsMsgRequest", object: {signalcarrier: signalcarrier, username: username}}));
         console.log("Mensaje Enviado");
    });
}


function waitForSocketConnection(socket, callback){
    setTimeout(
            function(){
                if(socket.readyState === 1){
                    console.log("Connection is made");
                    if(callback != null){
                        callback();
                    }
                    return;
                }else{
                    console.log("Wait for connection...");
                    waitForSocketConnection(socket, callback);
                }
            }
            
            
            ,5 );
}

function write(text){
    console.log("Text: " + text);
}