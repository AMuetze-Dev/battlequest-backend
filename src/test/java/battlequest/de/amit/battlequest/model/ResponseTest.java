package battlequest.de.amit.battlequest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.amit.battlequest.model.Response;

public class ResponseTest {

	private Response response;

	@BeforeEach
	public void setUp() {
		response = new Response(true, "Success message");
	}

	@Test
	public void testGettersAndSetters() {
		assertTrue(response.isSuccess());
		assertEquals("Success message", response.getMessage());

		response.setSuccess(false);
		response.setMessage("Error message");

		assertTrue(!response.isSuccess());
		assertEquals("Error message", response.getMessage());
	}

	@Test
	public void testToString() {
		final String expectedToString = "Response(isSuccess=true, message=Success message)";
		assertEquals(expectedToString, response.toString());
	}
}
