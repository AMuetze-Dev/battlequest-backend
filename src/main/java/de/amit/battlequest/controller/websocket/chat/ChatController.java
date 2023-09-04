package de.amit.battlequest.controller.websocket.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import de.amit.battlequest.model.Message;

@Controller
@CrossOrigin(origins = "http://aaronmuetze.ddns.net:3000")
public class ChatController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/public-message")
	@SendTo("/chatroom/public")
	public Message receivePublicMessage(@Payload Message message) {
		System.out.println("Received WebSocket message: " + message);
		return message;
	}

	@MessageMapping("/team-message")
	public Message receiveTeamMessage(@Payload Message message) {
		simpMessagingTemplate.convertAndSendToUser(message.getReceiverTeamId(), null, message);
		return message;
	}
}
