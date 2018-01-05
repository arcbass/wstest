
package ws;

import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import wsmessages.WsMessage;

/**
 *
 * @author Arnau
 */
public class MessageTextEncoder implements Encoder.Text<WsMessage>{
    
     private final Gson jsonProcessor = new Gson();   

    @Override
    public String encode(WsMessage object) throws EncodeException {
        return jsonProcessor.toJson(object);
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
