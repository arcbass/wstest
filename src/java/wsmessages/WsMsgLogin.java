
package wsmessages;

public class WsMsgLogin implements WsMsg{
    
    private String username;

    public WsMsgLogin(String username) {
        this.username = username;
    }    

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
