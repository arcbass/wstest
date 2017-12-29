package ws;

import wsmessages.WsMsgLogin;
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
import wsmessages.WsMsg;
import wsmessages.WsMsgLogout;

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
            
            processMessage(message, session);           

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

        //verify that the message is a JSON
        JsonParser parser = new JsonParser();
        JsonElement jse = parser.parse(message);
        if (!jse.isJsonObject()) {
            throw new RuntimeException("Mensaje recibido no es un JsonObject");
        }
        
        //create a json object to obtain the information of the message
        JsonObject jso = jse.getAsJsonObject();
        //get the type of message
        String typeOfMessage = jso.get("type").getAsString();
        
        //gson to create objects from json
        Gson gson = new Gson();
        //create a Json element to contain the object of the json 
        JsonElement content;
        
        //process the messages by the type of message
        switch (typeOfMessage) {
            case "WsMsgLogin":
                System.out.println("WsMsgLogin");
                //create an object in function of the type of message
                content = jso.get("object");                
                WsMsg msgLogin = gson.fromJson(content, wsmessages.WsMsgLogin.class);
                try {
                    driver.sendMessageToAll(msgLogin, typeOfMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "WsMsgLogout":
                System.out.println("WsMsgLogout");
                content = jso.get("object");
                WsMsg msgLogout = gson.fromJson(content, WsMsgLogout.class);
                
                try{
                    driver.sendMessageToAll(msgLogout, typeOfMessage);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }
}
