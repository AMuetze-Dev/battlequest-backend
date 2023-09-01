package de.amit.battlequest.model;

public class Credentials {

	private String username;
	private String password;

	public Player createPlayer() {
		return new Player(username, password);
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
