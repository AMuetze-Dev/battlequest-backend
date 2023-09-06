package de.amit.battlequest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long teamId;

    @Column(length = 32)
    private String teamname;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Player leader;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    public Team() { }

    public void addPlayer(Player player) {
        createPlayers();
        players.add(player);
    }

    public void createPlayers(){
        if(players == null)
            players = new ArrayList<Player>();
    }
}
