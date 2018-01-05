
var WBin;
var username;
var signalcarrier;

function openWBin() {

    //direccion puerto websocket   

    var direccion = "ws://localhost:8080/wstest/Chat/" + signalcarrier + "/" + username;
    WBin = new WebSocket(direccion);
    WBin.binaryType = "arraybuffer";

    console.log("websocket info: " + WBin.toString());

    WBin.onopen = function (event) {
        if (event.data === undefined) {
            return;
            console.log("event.data undefined");
        }
        console.log("[onOpen]: " + event.data);
    };

    WBin.onmessage = function (event) {
        console.log("[onMessage]:" + event.data);
        if (typeof event.data === "string") {
            var message = JSON.parse(event.data);
            processMessage(message);
        } else {

            drawImageBinary(event.data);
        }        
    };

    WBin.onerror = function (event) {
        console.log("[onError]: " + event.data);
    };

    WBin.onclose = function (event) {
        console.log("[onClose]: " + event.data);
    };

}

function processMessage(message) {
    if (message.type === "WsMsgLogin") {
        var userLoged = message.message.username;
        if (userLoged !== username) {
            createTab(userLoged);
        }

    }
    if (message.type === "WsMsgLogout") {
        removeTab(message.message.username);
    }

    if (message.type === "WsMsgMessage") {
        var sender = message.message.username;
        var msg = message.message.message;

        insertMessage(msg, sender, sender);
    }

    if (message instanceof Array) {
        console.log("Users connected: " + message);
        var index = message.indexOf(username);
        message.splice(index, 1);
        listOfUsers(message);
    }
}

function sendBinary(bytes) {
    console.log("sending binary: " + Object.prototype.toString.call(bytes));
    console.log("data length: " + bytes.byteLength);
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
        console.log("Mensaje Login Enviado");
    });
}

/**
 * Sends a text message
 * @returns {undefined}
 */
function sendMessage() {
    
    var msg = $("#comment").val();
    var reciver = $("ul.nav-pills>li.active").text();
        
    if (msg !== "" && reciver !== "") {
        $("#comment").val("");        
        
        WBin.send(JSON.stringify({type: "WsMsgMessage", object: {username: username, reciver: reciver, message: msg}}));
        insertMessage(msg, reciver, "me");
        console.log("Mensaje Texto Enviado");
    }
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

