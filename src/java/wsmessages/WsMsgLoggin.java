
package wsmessages;


public class WsMsgLoggin {
    private String username;
    private String signalcarrier;

    public WsMsgLoggin(String username, String signalcarrier) {
        this.username = username;
        this.signalcarrier = signalcarrier;
    }

    public String getUsername() {
        return username;
    }

    public String getSignalcarrier() {
        return signalcarrier;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSignalcarrier(String signalcarrier) {
        this.signalcarrier = signalcarrier;
    }
    
    
}
