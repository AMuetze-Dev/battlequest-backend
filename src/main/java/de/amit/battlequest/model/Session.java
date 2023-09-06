package de.amit.battlequest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session {
	@Id
	@Column(length = 8)
	private String code;
	@ManyToOne
	@JoinColumn(name = "master_id")
	private Player master;

	@OneToMany(mappedBy = "session")
	private List<Player> players;

	public Session() {
		code = generateSessionCode();
		players = new ArrayList<>();
	}

	private String generateSessionCode() {
		return new Random().ints(8, 0, 36).mapToObj(i -> Character.toString(i < 10 ? '0' + i : 'A' + i - 10))
				.collect(Collectors.joining()).toUpperCase();
	}
}
