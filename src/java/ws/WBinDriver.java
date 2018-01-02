package ws;

import wsmessages.WsMessage;
import wsmessages.WsMsgLogin;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.websocket.Session;
import wsmessages.WsMsg;

public class WBinDriver {

    private static WBinDriver INSTANCE = null;
    private static final Map<String, WBin> connections = new HashMap<String, WBin>();
    private final Gson jsonProcessor = new Gson();

    private WBinDriver() {
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple 
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WBinDriver();
        }
    }

    public static WBinDriver getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public Map<String, WBin> getConnections() {
        return connections;
    }

    public void addConnection(String signalcarrier, Session session, String user) {
        WBin connection = new WBin(signalcarrier, session, user);
        connections.put(signalcarrier, connection);
        System.out.println(connections.toString());
        //Enviar a los demas usuarios que el DEVICE esta disponible

    }

    public void closeConnection(String signalcarrier) {
        System.out.println("Dentro closeConnection()");
        System.out.println("SIGNAL CARRIER: " + signalcarrier);
        WBin connection = connections.get(signalcarrier);

        connections.remove(signalcarrier);

    }

    public void sendMessage(WsMsg message, String msgType) {

        System.out.println("sendChatRoomMessage");
        String user = message.getUsername();

        System.out.println("Mensaje: " + message);

        //START WSokets: Envio del mensaje
        //Recorremos el array vara ver el numero sesiones logeadas del usuario donde queremos enviar el mensaje
        Iterator it = connections.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            WBin con = (WBin) pair.getValue();
            if (con.getUser().equals(message.getUsername())) {

                //Canal por el que enviar
                String signalcarrier = con.getSignalcarrier();
                Session session = con.getSession();
                System.out.println(session + "   " + signalcarrier);
                WsMessage wsmessage = new WsMessage(msgType, message);
                String jsmessage = jsonProcessor.toJson(wsmessage);

                try {
                    //Enviamos por WS
                    session.getBasicRemote().sendText(jsmessage);
                } catch (IOException ex) {

                }

            }
        }

    }

    public void sendMessageToAll(WsMsg message, String msgType) {

        System.out.println("sendChatRoomMessage");

        System.out.println("Mensaje: " + message);

        //START WSokets: Envio del mensaje
        //iterate the array to send the message to all sessions
        Iterator it = connections.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            WBin con = (WBin) pair.getValue();

            //pipe which the message is sent
            String signalcarrier = con.getSignalcarrier();
            Session session = con.getSession();
            System.out.println(session + "   " + signalcarrier);
            WsMessage wsmessage = new WsMessage(msgType, message);
            String jsmessage = jsonProcessor.toJson(wsmessage);            

            try {
                //send it by ws
                session.getBasicRemote().sendText(jsmessage);
            } catch (IOException ex) {

            }

        }

    }
}
