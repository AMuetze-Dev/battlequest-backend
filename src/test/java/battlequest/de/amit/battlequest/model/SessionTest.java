package battlequest.de.amit.battlequest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.amit.battlequest.model.Session;

public class SessionTest {

	private Session session;

	@BeforeEach
	public void setUp() {
		session = new Session();
	}

	@Test
	public void testDefaultConstructor() {
		final Session defaultSession = new Session();
		assertNotNull(defaultSession.getCode());
		assertNull(defaultSession.getMaster());
		assertNotNull(defaultSession.getPlayers());
	}

	@Test
	public void testGeneratedCode() {
		final String code = session.getCode();
		assertNotNull(code);
		assertEquals(8, code.length());
		assertTrue(code.matches("[A-Z0-9]+"));
	}

	@Test
	public void testGettersAndSetters() {
		assertNotNull(session.getCode());
		assertNull(session.getMaster());
		assertNotNull(session.getPlayers());
	}
}
