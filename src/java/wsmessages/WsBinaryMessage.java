
package wsmessages;

import java.nio.ByteBuffer;

/**
 *
 * @author arnau
 */
public class WsBinaryMessage {
    
    private String sender;
    private final String reciver;
    private final ByteBuffer data;

    public WsBinaryMessage(String reciver, ByteBuffer data) {
        this.reciver = reciver;
        this.data = data;
        this.sender = null;
    }

    public String getSender() {
        return sender;
    }

    public String getReciver() {
        return reciver;
    }

    public ByteBuffer getData() {
        return data;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    
    
    
}
