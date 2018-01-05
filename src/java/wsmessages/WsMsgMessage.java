
package wsmessages;

/**
 *
 * @author arnau
 */
public class WsMsgMessage implements WsMsg{

    private String username;
    private String reciver;
    private String message;

    public WsMsgMessage(String username, String reciver, String message) {
        this.username = username;
        this.message = message;
    }    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }    

    public void setUsername(String username) {
        this.username = username;
    }   
    
    @Override
    public String getUsername() {
        return username;
    }
    
}
