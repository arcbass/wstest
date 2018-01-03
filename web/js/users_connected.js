/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function createTab(user) {
    var nextTab = user;
    // create the tab
    $('<li id="tab_' + nextTab + '"><a href="#' + nextTab + '" data-toggle="pill">' + nextTab + '</a></li>').appendTo('.nav-pills');

    // create the tab content
    $('<div class="tab-pane" id="' + nextTab + '">' + nextTab + ' content</div>').appendTo('.tab-content');   
}
;

function removeTab(user) {
    var userId = '#tab_' + user;
    $(userId).remove();
    //$('#tab_arnau').remove();
}

function listOfUsers(users){
    for(var i = 0; i < users.length; i++){
        createTab(users[i]);
    }
}

function insertMessage(msg, sender) {
    $('<p>' + msg + '</p></br>').appendTo("#" + sender)
}



