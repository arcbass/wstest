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
    private Object object;

    public WsMessage(String type, Object object) {
        this.type = type;
        this.object = object;
    }   
    
}
