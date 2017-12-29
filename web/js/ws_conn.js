

 var WBin;
 var username;
 var signalcarrier;

//assegurarse que solo hay una conexion abierta al mismo tiempo
function openWBin(){      
    
    //direccion puerto websocket
    //var direccion = "wss://erp.dafnube.com:48643/wstest/WBinStart/" + signalcarrier;
    
    var direccion = "ws://localhost:8080/wstest/WBinStart/" + signalcarrier + "/" + username;
    WBin = new WebSocket(direccion);
    
    console.log("websocket info: " + WBin.toString());    
     
    WBin.onopen = function(event){
        if(event.data === undefined){
            return;
            console.log("event.data undefined");
        }      
        //alert("ejecutando onOpen");
        write(event.data);
    };
    
    WBin.onmessage = function(event){
        console.log("mensaje recibido");
        
        var message = JSON.parse(event.data);
        processMessage(message);
        
        write("Mensaje recibido" + event.data);
        //gestionar mensaje (procesar mensaje)
    };
    
    WBin.onerror = function(event){
        write("ERROR: " + event.data);
    }
    
    WBin.onclose = function(event){
        console.log("websocket info: " + WBin.toString());
        write("connexion closed. " + event.data);
    };
        
}

function processMessage(message){
    if(message.type === "WsMsgRequest"){
        alert("Mensaje confirmacion recibido");
       
    }
}


function sendUserLogin(user, signal){
    console.log("Dentro de sendUserLogin()");
    console.log("User: " + username);
    
    username = user;
    signalcarrier = signal;
   
    openWBin();
   
    waitForSocketConnection(WBin, function(){
        //WBin.send("hola!!");
         WBin.send(JSON.stringify({type: "WsMsgRequest", object: {signalcarrier: signalcarrier, username: username}}));
         console.log("Mensaje Enviado");
    });
    console.log("websocket info: " + WBin.toString());    
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