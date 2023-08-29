package de.amit.battlequest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @Column(length=32)
    private String id;
    private int points;
    private String password;        // not plain text

    //

    public String getId() {
        return id;
    }
    public int getPoints() {
        return points;
    }
    public String getPassword() {
        return password;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}