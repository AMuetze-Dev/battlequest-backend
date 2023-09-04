package de.amit.battlequest.model;

import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid = UUID.randomUUID();
	private String username;
	@Column(length = 32)
	private String nickname;
	private int points;
	private String password;

	@ManyToOne
	@JoinColumn(name = "session_code")
	private Session session;

	public Player(String username, String password) {
		this.username = username;
		nickname = "DummyUser" + new Random().nextInt(10000, 99999);
		this.password = password;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	public Session getSession() {
		return session;
	}

	@JsonIgnore
	public String getUsername() {
		return username;
	}
}