package battlequest.de.amit.battlequest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.amit.battlequest.model.Message;
import de.amit.battlequest.model.Status;

public class MessageTest {

	private Message message;

	@BeforeEach
	public void setUp() {
		message = new Message("senderName", "receiverTeamId", "Test message", Status.MESSAGE);
	}

	@Test
	public void testGettersAndSetters() {
		assertEquals("senderName", message.getSenderName());
		assertEquals("receiverTeamId", message.getReceiverTeamId());
		assertEquals("Test message", message.getMessage());
		assertEquals(Status.MESSAGE, message.getStatus());

		message.setSenderName("newSender");
		message.setReceiverTeamId("newReceiver");
		message.setMessage("New message");
		message.setStatus(Status.JOIN);

		assertEquals("newSender", message.getSenderName());
		assertEquals("newReceiver", message.getReceiverTeamId());
		assertEquals("New message", message.getMessage());
		assertEquals(Status.JOIN, message.getStatus());
	}

	@Test
	public void testToString() {
		final String expectedToString = "Message(senderName=senderName, receiverTeamId=receiverTeamId, message=Test message, status=MESSAGE)";
		assertEquals(expectedToString, message.toString());
	}
}
