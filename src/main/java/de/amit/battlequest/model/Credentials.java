package de.amit.battlequest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Credentials {

	private String username;
	private String password;
	private String newPassword;

	public Player createPlayer() {
		return new Player(username, password);
	}

}
