package ws;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import wsmessages.WsBinaryMessage;
import wsmessages.WsMessage;

@ServerEndpoint(
        value = "/Chat/{signalcarrier}/{username}",
        encoders = {MessageTextEncoder.class},
        decoders = {MessageTextDecoder.class, MessageBinaryDecoder.class}
)
public class ChatEndpoint {

    ChatConnections connections = ChatConnections.getInstance(); 
    private String user;

    @OnOpen
    public void onOpen(Session session,
            @PathParam("signalcarrier") String signalcarrier,
            @PathParam("username") String user) {

        session.getUserProperties().put("username", user);
        this.user = user;
        System.out.println("onOpen: " + session + "---" + signalcarrier + "---" + user);
        
        connections.addConnection(session, user, signalcarrier);

        // Collection<WBin> allConnections = new ArrayList<>(driver.getConnections().values());
    }

    @OnMessage
    public void onTextMessage(Session session, WsMessage message) throws IOException, EncodeException {

        if (session.isOpen()) {

            System.out.println("onMessage: " + session + " Message: " + message);

            processMessage(message);

        }
    }

    @OnMessage(maxMessageSize = 50000)
    public void onBinaryMessage(WsBinaryMessage message, Session session) throws IOException {
        System.out.println("broadcastBinary: " + message.getData());              
               
        session.getBasicRemote().sendBinary(message.getData());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Error: " + error.getMessage());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
        connections.closeConnection(session);
    }

    private void processMessage(WsMessage message) {

        String messageType = message.getType();

        try {
            switch (messageType) {
                case "WsMsgLogin":
                    connections.sendMessageToAll(message);
                    connections.sendUsersConnected(message);
                    break;
                case "WsMsgLogout":
                    connections.sendMessageToAll(message);
                    break;
                case "WsMsgMessage":
                    connections.sendMessage(message);
            }

        } catch (IOException ex) {
            Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncodeException ex) {
            Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getReciverName(ByteBuffer data){
        int length = data.get(0); 
        String reciver = "";
        for(int i = 1; i <= length; i++) {
            reciver += (char) data.get(i);
        }
        return reciver;
    }
}
