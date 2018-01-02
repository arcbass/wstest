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
import wsmessages.WsMessage;

@ServerEndpoint(
        value = "/Chat/{signalcarrier}/{username}",
        encoders = {MessageTextEncoder.class},
        decoders = {MessageTextDecoder.class}
)
public class ChatEndpoint {

    ChatConnections connections = ChatConnections.getInstance();
    String signalcarrier;
    String user;

    @OnOpen
    public void onOpen(Session session,
            @PathParam("signalcarrier") String signalcarrier,
            @PathParam("username") String user) {

        session.getUserProperties().put("username", user);

        System.out.println("onOpen: " + session + "---" + signalcarrier + "---" + user);
        this.signalcarrier = signalcarrier;
        this.user = user;
        connections.addConnection(session, user, signalcarrier);

        // Collection<WBin> allConnections = new ArrayList<>(driver.getConnections().values());
    }

    @OnMessage
    public void echoTextMessage(Session session, WsMessage message) throws IOException, EncodeException {

        if (session.isOpen()) {

            System.out.println("onMessage: " + session + " Message: " + message);

            processMessage(message);

        }
    }

    @OnMessage
    public void onBinaryMessage(ByteBuffer data, Session session) throws IOException {
        System.out.println("broadcastBinary: " + data);
        session.getBasicRemote().sendBinary(data);
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
}
