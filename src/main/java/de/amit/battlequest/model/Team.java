package de.amit.battlequest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32)
    private String teamname;
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Player leader;

    //

    public Long getId() {
        return id;
    }

    public Player getLeader() {
        return leader;
    }

    public String getName() {
        return teamname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public void setName(String teamname) {
        this.teamname = teamname;
    }
}
