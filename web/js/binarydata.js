/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var canvas = document.getElementById("myCanvas");
var context = canvas.getContext("2d");


function getImage(){
    var oReq = new XMLHttpRequest();
    oReq.open("GET", "img/dti.jpg", true);
    oReq.responseType = "arraybuffer";
    
    var arrayBuffer; 
    oReq.onload = function (onEvent) {
        arrayBuffer = oReq.response;
        /*if(arrayBuffer) {
            var byteArray = new Uint8Array(arrayBuffer);
            for(var i = 0; i < byteArray.byteLength; i++) {
                byteArray[i] = data[i];
            }
        }*/
    };
    oReq.send(null);
    return arrayBuffer;
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

function drawImageBinary(blob) {
    var bytes = new Uint8Array(blob);
    console.log("drawImageBinary (bytes.length): " + bytes.length);
    
    var imageData = context.createImageData(canvas.width, canvas.height);
    
    for (var i = 8; i < imageData.data.length; i++) {
        imageData.data[i] = bytes[i];
    }
    context.putImageData(imageData, 0, 0);
    
    var img = document.createElement('img');
    img.height = canvas.height;
    img.width = canvas.width;
    img.src = canvas.toDataURL();
}