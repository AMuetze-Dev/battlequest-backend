package de.amit.battlequest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sessionplayer")
public class SessionIdentity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public SessionIdentity() { }
    public SessionIdentity(Session session, Player player){ this.session = session; this.player = player; }

    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
