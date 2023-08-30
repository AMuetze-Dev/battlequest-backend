package de.amit.battlequest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String sessionId;
    @ManyToOne
    @JoinColumn(name = "master_id")
    @Column(length=8)
    private Player master;

    public Session () {
        this.sessionId = generateKey();
    }

    public void setSessionId(String sessionId){ this.sessionId = sessionId; }
    public void setMaster(Player master){ this.master= master; }
    public String getSessionId(){ return sessionId; }
    public Player getMaster(){ return master; }

    public String generateKey(){
        char[] ALPHANUMERIC ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder random = new StringBuilder();
        for(int i = 0; i < 8; i++){
            int length = ALPHANUMERIC.length - 1;
            int index = (int) (Math.random() * (length));
            random.append(ALPHANUMERIC[index]);
        }
        return random.toString();
    }
}
