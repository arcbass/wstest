
package ws;

import java.nio.ByteBuffer;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import wsmessages.WsBinaryMessage;

/**
 *
 * @author arnau
 */
public class MessageBinaryDecoder implements Decoder.Binary<WsBinaryMessage>{

    @Override
    public WsBinaryMessage decode(ByteBuffer data) throws DecodeException {
         System.out.println("before decoder: " + data);  
        String reciver = getReciverName(data);
        System.out.println("Reciver: " + reciver);
        data.position(reciver.length()+1);
        ByteBuffer dataMsg= data.slice();
        return new WsBinaryMessage(reciver, dataMsg);
    }

    @Override
    public boolean willDecode(ByteBuffer data) {
        String reciver = getReciverName(data);
        return (!reciver.equals(""));
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init decoder binary");
    }

    @Override
    public void destroy() {
         System.out.println("destroy decoder binary");
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
