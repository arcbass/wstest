/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            ByteBuffer senderData = ByteBuffer.wrap(message.getSender().getBytes());
            
            int bufferCapacity = message.getData().capacity() + message.getSender().length();
            return ByteBuffer.allocate(bufferCapacity).put(senderData).put(message.getData());
            
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
