package ws;

import wsmessages.WsMessage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import wsmessages.WsMsgLogout;
import wsmessages.WsMsgMessage;

public class ChatConnections {

    private static ChatConnections INSTANCE = null;
    private static final Set<Session> connections = new HashSet<Session>();
    private static Set<String> usersConnected = new HashSet<String>();

    private ChatConnections() {
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple 
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatConnections();
        }
    }

    public static ChatConnections getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public Set<Session> getConnections() {
        return connections;
    }

    public void addConnection(Session session, String user, String sessionid) {
        session.getUserProperties().put("user", user);
        session.getUserProperties().put("sessionid", sessionid);
        connections.add(session);
        usersConnected.add(user);
        System.out.println(connections.toString());
    }

    public void closeConnection(Session session) {
        System.out.println("Dentro closeConnection()");
        System.out.println("SIGNAL CARRIER: " + session.getUserProperties().get("sessionid"));
        String userName = (String) session.getUserProperties().get("user");
        connections.remove(session);        
        
        boolean stillConnected = false;        
        for(Session s : connections){
            if(userName.equals((String) s.getUserProperties().get("user"))){
                stillConnected = true;
                break;
            }
        }
        if(!stillConnected){
            usersConnected.remove("userName");
            WsMessage logoutMsg = new WsMessage("WsMsgLogout", new WsMsgLogout(userName));
            try {
                sendMessageToAll(logoutMsg);
            } catch (IOException ex) {
                Logger.getLogger(ChatConnections.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncodeException ex) {
                Logger.getLogger(ChatConnections.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendMessage(WsMessage message) throws IOException, EncodeException {

        System.out.println("sendChatRoomMessage");

        System.out.println("Mensaje: " + message);
        String reciver = ((WsMsgMessage) message.getMessage()).getReciver();
        
        //START WSokets: Envio del mensaje
        //Recorremos el array vara ver el numero sesiones logeadas del usuario donde queremos enviar el mensaje
        Iterator it = connections.iterator();
        Session session;
        while (it.hasNext()) {
            session = (Session) it.next();
            String sessionUser = (String) session.getUserProperties().get("user");

            if (sessionUser.equals(reciver)) {
                //Canal por el que enviar

                String sessionId = (String) session.getUserProperties().get("sessionid");
                System.out.println(session + "   " + sessionId);

                //Enviamos por WS
                session.getBasicRemote().sendObject(message);
            }
        }
    }

    public void sendMessageToAll(WsMessage message) throws IOException, EncodeException {

        System.out.println("sendChatRoomMessage");

        System.out.println("Mensaje: " + message);

        Iterator it = connections.iterator();
        Session session;
        while (it.hasNext()) {
            session = (Session) it.next();
            String sessionId = (String) session.getUserProperties().get("sessionid");
            System.out.println(session + "   " + sessionId);

            //Enviamos por WS
            session.getBasicRemote().sendObject(message);
        }
    }
    
    
}
