package ws;


import javax.websocket.Session;


public class WBin {
    
    private String signalcarrier;
    private Session session;
    private String user;

    public WBin(String signalcarrier, Session session, String user) {
        this.signalcarrier = signalcarrier;
        this.session = session;       
        this.user = user;
    }

    public String getSignalcarrier() {
        return signalcarrier;
    }

    public void setSignalcarrier(String signalcarrier) {
        this.signalcarrier = signalcarrier;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    
}
