package de.amit.battlequest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;
    @ManyToOne
    @JoinColumn(name = "master_id")
    private Player master;

    public void setSessionId(Long sessionId){ this.sessionId = sessionId; }
    public void setMaster(Player master){ this.master= master; }
    public Long getSessionId(){ return sessionId; }
    public Player getMaster(){ return master; }
}
