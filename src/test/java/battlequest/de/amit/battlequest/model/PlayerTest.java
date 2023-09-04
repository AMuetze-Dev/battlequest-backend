package battlequest.de.amit.battlequest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.amit.battlequest.model.Player;

public class PlayerTest {

	private Player player;

	@BeforeEach
	public void setUp() {
		player = new Player("testuser", "testpassword");
	}

	@Test
	public void testConstructorWithUsernameAndPassword() {
		final Player newUser = new Player("newuser", "newpassword");
		assertNotNull(newUser.getUuid());
		assertEquals("newuser", newUser.getUsername());
		assertNotNull(newUser.getNickname());
		assertNotNull(newUser.getPassword());
		assertEquals(0, newUser.getPoints());
		assertNull(newUser.getSession());
	}

	@Test
	public void testDefaultConstructor() {
		final Player defaultPlayer = new Player();
		assertNotNull(defaultPlayer.getUuid());
		assertNull(defaultPlayer.getUsername());
		assertNull(defaultPlayer.getNickname());
		assertEquals(0, defaultPlayer.getPoints());
		assertNull(defaultPlayer.getPassword());
		assertNull(defaultPlayer.getSession());
	}

	@Test
	public void testGettersAndSetters() {
		final UUID uuid = UUID.randomUUID();
		player.setUuid(uuid);
		player.setNickname("TestNickname");
		player.setPoints(100);
		player.setPassword("newpassword");

		assertEquals(uuid, player.getUuid());
		assertEquals("TestNickname", player.getNickname());
		assertEquals(100, player.getPoints());
		assertEquals("newpassword", player.getPassword());
	}
}