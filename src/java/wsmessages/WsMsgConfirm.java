
package wsmessages;


public class WsMsgConfirm {
    private String username;
    private String signalcarrier;

    public WsMsgConfirm(String username, String signalcarrier) {
        this.username = username;
        this.signalcarrier = signalcarrier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSignalcarrier() {
        return signalcarrier;
    }

    public void setSignalcarrier(String signalcarrier) {
        this.signalcarrier = signalcarrier;
    }
    
}
