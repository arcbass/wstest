/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsmessages;

/**
 *
 * @author juan
 */
public class WsMessage {
    private String type;
    private WsMsg message;

    public WsMessage(String type, WsMsg message) {
        this.type = type;
        this.message = message;
    } 

    public String getType() {
        return type;
    }

    public WsMsg getMessage() {
        return message;
    }    
    
    
}
