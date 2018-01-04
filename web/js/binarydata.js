/* 
 * Functions to upload files, send and recive them in binary
 */

//gets the file to from the input and sends it.
function inputFile() {
    var x = document.getElementById("myFile");

    for (var i = 0; i < x.files.length; i++) {
        var file = x.files[i];
        console.log(file.size);

        var reader = new FileReader();
        reader.onload = function () {
            var arrayBuffer = reader.result;            
            
            var reciver = $("ul.nav-pills>li.active").text();
            var reciverBuffer = binaryMsgAttribute(reciver);
            var bufferComplete = _appendBuffer(reciverBuffer, arrayBuffer);
            
            console.log(arrayBuffer.byteLength);
            console.log(bufferComplete.byteLength);
            
            sendBinary(bufferComplete);
        };
        reader.readAsArrayBuffer(file);
    }
}

//append the name of the sender and its length. This way the server knows to
//which user has to be sent.
function binaryMsgAttribute(msg) {
    var buffer = new ArrayBuffer(msg.length + 1);
    var bytes = new Uint8Array(buffer);
    for (var i = 0; i < bytes.length; i++) {
        if (i === 0)
            bytes[i] = msg.length;
        else
            bytes[i] = msg.charCodeAt(i - 1);

    }
    return bytes.buffer;
}

function drawImageBinary(ArrayBuffer) {

    //console.log("drawImageBinary (bytes.length): " + bytes.length);
    //get the length of the name
    var length = new Uint8Array(ArrayBuffer, 0, 1);
    alert(length);
    
    //get the name of the message sender 
    var senderArr = new Uint8Array(ArrayBuffer, 1, length);
    var sender = "";
    for (var i = 0; i <= length; i++) {
        sender += String.fromCharCode(senderArr[i]);
    }
    alert(sender);

    //get the image
    var byteStartImage = parseInt(length) + 1;
    alert(byteStartImage);
    var bytes = new Uint8Array(ArrayBuffer, byteStartImage);

    //download file
    saveByteArray([bytes], 'example.pdf');
    
    var blob = new Blob([bytes], {type: "image/jpeg"});
    var urlCreator = window.URL || window.webkitURL;
    var imageUrl = urlCreator.createObjectURL(blob);

    

    var senderId = '#' + sender.toString() + '>ul';
    //var senderId = '#arnau>ul';
    alert(senderId);

    var img = $('<img width="150" height="150">');
    img.attr('src', imageUrl);
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



//to download a binary file (arraybuffer) from the client
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



