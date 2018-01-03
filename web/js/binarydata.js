/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var canvas = document.getElementById("myCanvas");
var context = canvas.getContext("2d");


function getImage() {
    var oReq = new XMLHttpRequest();
    oReq.open("GET", "img/dti.jpg", true);
    oReq.responseType = "arraybuffer";
    var reciver = $("ul.nav-pills>li.active").text();
    
    oReq.onload = function (onEvent) {
        var imageBuffer = oReq.response;        
        var reciverBuffer = binaryMsg(reciver);
        var bufferComplete = _appendBuffer(reciverBuffer, imageBuffer);
        console.log(imageBuffer.byteLength);
        console.log(bufferComplete.byteLength);
        
        sendBinary(bufferComplete);
    };
    oReq.send(null);
}

function binaryMsg(msg) {
    var buffer = new ArrayBuffer(msg.length + 1);
    var bytes = new Uint8Array(buffer);
    for (var i = 0; i < bytes.length; i++) {
        if(i == 0) bytes[i] = msg.length;
        else bytes[i] = msg.charCodeAt(i - 1);
        
    }
    return bytes.buffer;
}

function defineImageBinary() {
    var image = new Image(300, 300);
    image.src = 'img/dti.jpg';
    //var buffer = new ArrayBuffer(image.data.length);
    var buffer = getImage();

    var bytes = new Uint8Array(buffer);
    for (var i = 0; i < bytes.length; i++) {
        bytes[i] = image.data[i];
    }
    sendBinary(buffer);
}

function drawImageBinary(ArrayBuffer) {
    
    //console.log("drawImageBinary (bytes.length): " + bytes.length);
    var length = new Uint8Array(ArrayBuffer, 0, 1);
    alert(length);
    var senderArr = new Uint8Array(ArrayBuffer, 1, length);
    var sender = "";
    for(var i = 0; i <= length; i++) {
        sender += String.fromCharCode(senderArr[i]);
    }
    alert(sender);
    var byteStartImage = parseInt(length) + 1;
    alert(byteStartImage);
    var bytes = new Uint8Array(ArrayBuffer, byteStartImage);
    //alert(bytes.byteLength);
    
    var blob = new Blob([bytes], {type: "image/jpeg"});
    var urlCreator = window.URL || window.webkitURL;
    var imageUrl = urlCreator.createObjectURL(blob);    

    var myImage = new Image(150, 150);
    myImage.src = imageUrl;
     var senderId = '#' + sender.toString() + '>ul';
     alert(senderId);
    $(senderId).append(myImage);
    
    
}
/**
 * Creates a new Uint8Array based on two different ArrayBuffers
 *
 * @private
 * @param {ArrayBuffers} buffer1 The first buffer.
 * @param {ArrayBuffers} buffer2 The second buffer.
 * @return {ArrayBuffers} The new ArrayBuffer created out of the two.
 */
var _appendBuffer = function(buffer1, buffer2) {
  var tmp = new Uint8Array(buffer1.byteLength + buffer2.byteLength);
  tmp.set(new Uint8Array(buffer1), 0);
  tmp.set(new Uint8Array(buffer2), buffer1.byteLength);
  return tmp.buffer;
};
