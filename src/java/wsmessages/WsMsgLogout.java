package wsmessages;

public class WsMsgLogout implements WsMsg{
    
    private String signalcarrier;
    private String username;

    public WsMsgLogout(String signalcarrier, String username) {
        this.username = username;
        this.signalcarrier = signalcarrier;
    }

    public String getSignalcarrier() {
        return signalcarrier;
    }

    public String getUsername() {
        return username;
    }

    public void setSignalcarrier(String signalcarrier) {
        this.signalcarrier = signalcarrier;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
