/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import wsmessages.WsMessage;
import wsmessages.WsMsg;
import wsmessages.WsMsgLogin;
import wsmessages.WsMsgLogout;
import wsmessages.WsMsgMessage;

/**
 *
 * @author Arnau
 */
public class MessageTextDecoder implements Decoder.Text<WsMessage> {

    @Override
    public WsMessage decode(String message) throws DecodeException {
        //parse the message
        JsonParser parser = new JsonParser();
        JsonElement jse = parser.parse(message);
        //create a json object to obtain the information of the message
        JsonObject jso = jse.getAsJsonObject();
        //get the type of message
        String messageType = jso.get("type").getAsString();

        //gson to create objects from json
        Gson gson = new Gson();
        //create a Json element to contain the object of the json 
        JsonElement content;
        //process the messages by the type of message

        WsMsg messageContent;
        switch (messageType) {
            case "WsMsgLogin":
                System.out.println("WsMsgLogin");
                //create an object in function of the type of message
                content = jso.get("object");
                messageContent = gson.fromJson(content, WsMsgLogin.class);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "WsMsgMessage":
                System.out.println("WsMsgLogin");
                //create an object in function of the type of message
                content = jso.get("object");
                messageContent = gson.fromJson(content, WsMsgMessage.class);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "WsMsgLogout":
                System.out.println("WsMsgLogout");
                content = jso.get("object");
                messageContent = gson.fromJson(content, WsMsgLogout.class);

                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new RuntimeException("Tipo de mensaje desconocido");            
        }
        return new WsMessage(messageType, messageContent);
    }

    @Override
    public boolean willDecode(String message) {
        //verify that the message is a JSON
        try {
            JsonParser parser = new JsonParser();
            JsonElement jse = parser.parse(message);
            if (!jse.isJsonObject()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }

}
