package ws;


import wsmessages.WsMsgLoggin;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/WBinStart/{signalcarrier}/{username}")
public class WBinStart {

    WBinDriver driver = WBinDriver.getInstance();
    String signalcarrier;
    String user;

    @OnOpen
    public void onOpen(Session session,
            @PathParam("signalcarrier") String signalcarrier,
            @PathParam("username") String user) {

        System.out.println("onOpen: " + session + "---" + signalcarrier + "---" + user);
        this.signalcarrier = signalcarrier;
        this.user = user;
        driver.addConnection(signalcarrier, session, user);

       // Collection<WBin> allConnections = new ArrayList<>(driver.getConnections().values());
    }

    @OnMessage
    public void echoTextMessage(Session session, String message) {

        if (session.isOpen()) {

            System.out.println("onMessage: " + session + " Message: " + message);           
           
                JsonParser parser = new JsonParser();
                JsonElement jse = parser.parse(message);
                if (jse.isJsonObject()) {
                    processMessage(message, session);
                }
                else{
                    System.out.println("is not a json");
                }
           

        }
    }
    @OnError
public void onError(Session session, Throwable error) {
    System.out.println("Error: " + error.getMessage());
}
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
        driver.closeConnection(this.signalcarrier);

    }

    public void processMessage(String message, Session session) {
        System.out.println("PROCESS MESSAGE");
        
        //comprovar que sea un json
        JsonParser parser = new JsonParser();
        JsonElement jse = parser.parse(message);
        if (!jse.isJsonObject()) {
            throw new RuntimeException("Mensaje recibido no es un JsonObject");
        }
        
        JsonObject jso = jse.getAsJsonObject();
        String typeOfMessage = jso.get("type").getAsString();
        
        Gson gson = new Gson();
        JsonElement content;
        
        switch(typeOfMessage){
            case "WsMsgRequest":
                System.out.println("WsMsgRequest");
                content = jso.get("object");
                WsMsgLoggin request = gson.fromJson(content, wsmessages.WsMsgLoggin.class);
                try{
                    driver.sendMessage(request, typeOfMessage);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
                case "WsMsgAccept":
                System.out.println("WsMsgRequest");
                content = jso.get("object");
                WsMsgLoggin accept = gson.fromJson(content, WsMsgLoggin.class);
                /*
                try{
                    driver.sendMessage(accept, typeOfMessage);
                }catch (Exception e){
                    e.printStackTrace();
                }*/               
                break;
                
                
        }
    }
}
