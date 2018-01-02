

var WBin;
var username;
var signalcarrier;

//assegurarse que solo hay una conexion abierta al mismo tiempo
function openWBin() {

    //direccion puerto websocket
    //var direccion = "wss://erp.dafnube.com:48643/wstest/WBinStart/" + signalcarrier;

    var direccion = "ws://localhost:8080/wstest/WBinStart/" + signalcarrier + "/" + username;
    WBin = new WebSocket(direccion);
    WBin.binaryType = "arraybuffer";

    console.log("websocket info: " + WBin.toString());

    WBin.onopen = function (event) {
        if (event.data === undefined) {
            return;
            console.log("event.data undefined");
        }
        //alert("ejecutando onOpen");
        write(event.data);
    };

    WBin.onmessage = function (event) {
        console.log("mensaje recibido");
        if (typeof event.data == "string") {
            var message = JSON.parse(event.data);
            processMessage(message);
        } else {
            drawImageBinary(event.data);
        }

        

        write("Mensaje recibido" + event.data);
        //gestionar mensaje (procesar mensaje)
    };

    WBin.onerror = function (event) {
        write("ERROR: " + event.data);
    }

    WBin.onclose = function (event) {
        console.log("websocket info: " + WBin.toString());
        write("connexion closed. " + event.data);
    };

}

function processMessage(message) {
    if (message.type === "WsMsgLogin") {
        alert("Usuario logeado");
    }
    if (message.type === "WsMsgLogout") {
        alert("Usuario desconectado");
    }
}

function sendBinary(bytes) {
    console.log("sending binary: " + Object.prototype.toString.call(bytes));
    WBin.send(bytes);
}

function sendUserLogin(user, signal) {
    console.log("Dentro de sendUserLogin()");
    console.log("User: " + username);

    username = user;
    signalcarrier = signal;

    openWBin();

    waitForSocketConnection(WBin, function () {
        //WBin.send("hola!!");
        WBin.send(JSON.stringify({type: "WsMsgLogin", object: {username: username}}));
        console.log("Mensaje Enviado");
    });
    console.log("websocket info: " + WBin.toString());
}

function sendMessage() {
    //var msg = document.getElementById("comment").value;
    var msg = $("#comment").val();
    var reciver = $("li.active").text();

    WBin.send(JSON.stringify({type: "WsMsgMessage", object: {username: username, reciver: reciver, message: msg}}));
    console.log("Mensaje Enviado");
}

function Logout() {
    WBin.send(JSON.stringify({type: "WsMsgLogout", object: {username: username}}));
}


function waitForSocketConnection(socket, callback) {
    setTimeout(
            function () {
                if (socket.readyState === 1) {
                    console.log("Connection is made");
                    if (callback != null) {
                        callback();
                    }
                    return;
                } else {
                    console.log("Wait for connection...");
                    waitForSocketConnection(socket, callback);
                }
            }


    , 5);
}

function write(text) {
    console.log("Text: " + text);
}