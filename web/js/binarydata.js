/* 
 * Functions to upload files, send and recive them in binary
 */

//gets the file to from the input and sends it.
function inputFile() {
    var x = document.getElementById("myFile");
    var reciver = $("ul.nav-pills>li.active").text();
    
    if (x.files.length > 0 && reciver !== "") {
        for (var i = 0; i < x.files.length; i++) {
            var file = x.files[i];
            console.log(file.size);
            var reader = new FileReader();
            reader.onload = function () {
                /**                 * 
                 * create 2 arraybuffers: one with the name of the reciver 
                 * and another with the file, then append and send them.
                 */
                var arrayBuffer = reader.result;                
                var reciverBuffer = binaryMsgAttribute(reciver);
                var bufferComplete = _appendBuffer(reciverBuffer, arrayBuffer);
                console.log(arrayBuffer.byteLength);
                console.log(bufferComplete.byteLength);
                
                sendBinary(bufferComplete);

            };
            reader.readAsArrayBuffer(file);
        }
    }
}


/**
 * append the name of the sender and its length. This way the server knows to
 * which user has to be sent.
 * @param {type} msg
 * @returns {ArrayBuffer} the arraybuffer with the msg.lenght and the msg
 */
var binaryMsgAttribute = function (msg) {
    var buffer = new ArrayBuffer(msg.length + 1);
    var bytes = new Uint8Array(buffer);
    for (let i = 0; i < bytes.length; i++) {
        if (i === 0)
            bytes[i] = msg.length;
        else
            bytes[i] = msg.charCodeAt(i - 1);
    }
    return bytes.buffer;
};


function drawImageBinary(ArrayBuffer) {

//console.log("drawImageBinary (bytes.length): " + bytes.length);
//get the length of the name
    var length = new Uint8Array(ArrayBuffer, 0, 1);
    
    //get the name of the message sender 
    var senderArr = new Uint8Array(ArrayBuffer, 1, length);
    var sender = new TextDecoder("utf-8").decode(senderArr);
    //get the image
    var byteStartImage = parseInt(length) + 1;
    
    var bytes = new Uint8Array(ArrayBuffer, byteStartImage);
    //download file
    //saveByteArray([bytes], 'example.jpg');

    var blob = new Blob([bytes], {type: "image/jpeg"});
    var urlCreator = window.URL || window.webkitURL;
    var imageUrl = urlCreator.createObjectURL(blob);
    
    var senderId = '#' + sender + '>ul';
    var img = $('<img width="150" height="150">');
    img.attr('src', imageUrl);
    $(senderId).append('<li><a href="#"><span class="tab">Message Center</span></a></li>');
    img.appendTo(senderId);
}


/**
 * Creates a new Uint8Array based on two different ArrayBuffers
 *
 * @private
 * @param {ArrayBuffers} buffer1 The first buffer.
 * @param {ArrayBuffers} buffer2 The second buffer.
 * @return {ArrayBuffers} The new ArrayBuffer created out of the two.
 */
var _appendBuffer = function (buffer1, buffer2) {
    var tmp = new Uint8Array(buffer1.byteLength + buffer2.byteLength);
    tmp.set(new Uint8Array(buffer1), 0);
    tmp.set(new Uint8Array(buffer2), buffer1.byteLength);
    return tmp.buffer;
};



/**
 * to download a binary file (arraybuffer) from the client
 * @type Function
 */
var saveByteArray = (function () {
    var a = document.createElement("a");
    document.body.appendChild(a);
    a.style = "display: none";
    return function (data, name) {
        var blob = new Blob(data, {type: "octet/stream"}),
                url = window.URL.createObjectURL(blob);
        a.href = url;
        a.download = name;
        a.click();
        window.URL.revokeObjectURL(url);
    };
}());



