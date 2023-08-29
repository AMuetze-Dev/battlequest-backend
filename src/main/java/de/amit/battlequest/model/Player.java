package de.amit.battlequest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=32)
    private String username;
    private int points;
    private String password;        // not plain text

    //

    public Long getId() {
        return id;
    }
    public String getUsername() { return username; }
    public int getPoints() {
        return points;
    }
    public String getPassword() {
        return password;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username) { this.username = username; }
    public void setPoints(int points) {
        this.points = points;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}