package battlequest.de.amit.battlequest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.amit.battlequest.model.Credentials;
import de.amit.battlequest.model.Player;

public class CredentialsTest {

	private Credentials credentials;

	@BeforeEach
	public void setup() {
		credentials = new Credentials("testuser", "oldpassword", "newpassword");
	}

	@Test
	public void testCreatePlayer() {
		final Player player = credentials.createPlayer();
		assertEquals("testuser", player.getUsername());
		assertEquals("oldpassword", player.getPassword());
	}

	@Test
	public void testGettersAndSetters() {
		assertEquals("testuser", credentials.getUsername());
		assertEquals("oldpassword", credentials.getPassword());
		assertEquals("newpassword", credentials.getNewPassword());

		credentials.setUsername("newuser");
		credentials.setPassword("newpassword2");
		credentials.setNewPassword("newpassword3");

		assertEquals("newuser", credentials.getUsername());
		assertEquals("newpassword2", credentials.getPassword());
		assertEquals("newpassword3", credentials.getNewPassword());
	}

	@Test
	public void testToString() {
		final String expectedToString = "Credentials(username=testuser, password=oldpassword, newPassword=newpassword)";
		assertEquals(expectedToString, credentials.toString());
	}
}
