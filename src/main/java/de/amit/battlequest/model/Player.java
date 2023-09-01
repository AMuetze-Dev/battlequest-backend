package de.amit.battlequest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JoinColumn(name = "team_id")
	private Team team;
	@Column(length = 32)
	private String username;
	@Column(length = 32)
	private String nickname;
	private int points;
	private String password;

	public Player() {
	}

	public Player(String username, String password) {
		this.username = username;
		this.password = password;
	}

	//

	public Long getId() {
		return id;
	}

	public String getNickname() { return nickname; }

	public String getPassword() {
		return password;
	}

	public int getPoints() {
		return points;
	}

	public Team getTeam() { return team; }

	public String getUsername() {
		return username;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNickname(String nickname) { this.nickname = nickname; }

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setTeam(Team team) { this.team = team; }

	public void setUsername(String username) {
		this.username = username;
	}
}