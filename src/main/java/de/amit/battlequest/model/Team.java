package de.amit.battlequest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32)
    private String teamname;
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Player leader;

    @OneToMany(mappedBy = "team")
    private List<Player> players;
}
