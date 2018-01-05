
package ws;

import java.nio.ByteBuffer;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import wsmessages.WsBinaryMessage;

/**
 *
 * @author arnau
 */
public class MessageBinaryEncoder implements Encoder.Binary<WsBinaryMessage>{

    @Override
    public ByteBuffer encode(WsBinaryMessage message) throws EncodeException {
        if(message.getSender() != null) {
            
            int bufferCapacity = 
                    message.getData().capacity() 
                    + message.getSender().length() 
                    + 1;
            System.out.println("buffer size:" + bufferCapacity);
            
            
            ByteBuffer dataEncoded = ByteBuffer.allocate(bufferCapacity)
                    .put((byte) message.getSender().length())
                    .put(message.getSender().getBytes())
                    .put(message.getData());
                    
            dataEncoded.position(0);
            
            return dataEncoded;
            
        }
        else return message.getData();
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init encoder binary");
    }

    @Override
    public void destroy() {
         System.out.println("destroy encoder binary");
    }
    
}
