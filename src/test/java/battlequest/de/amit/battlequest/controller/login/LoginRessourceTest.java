package battlequest.de.amit.battlequest.controller.login;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.login.LoginRessource;
import de.amit.battlequest.controller.rest.player.PasswordRessource;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Credentials;
import de.amit.battlequest.model.Player;

@SpringBootTest(classes = BattlequestApplication.class)
public class LoginRessourceTest {

	private MockMvc mockMvc;

	@Autowired
	private LoginRessource loginRessource;

	@MockBean
	private PlayerRessource playerRessource;

	@MockBean
	private PasswordRessource passwordRessource;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginRessource).build();
	}

	@Test
	void testLoginFailure() throws Exception {
		Mockito.when(playerRessource.read(Mockito.anyString())).thenReturn(null);
		Mockito.when(passwordRessource.validate(Mockito.any(Credentials.class))).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"notexist\", \"password\": \"wrong\"}").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(""));
	}

	@Test
	void testLoginSuccess() throws Exception {
		final Player mockPlayer = new Player(UUID.randomUUID(), "testUser", "DummyUser12345", 0, "password", null);

		Mockito.when(playerRessource.read(Mockito.anyString())).thenReturn(mockPlayer);
		Mockito.when(passwordRessource.validate(Mockito.any(Credentials.class))).thenReturn(true);

		final String expectedResponse = "\"" + mockPlayer.getUuid().toString() + "\"";

		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"testUser\", \"password\": \"password\"}").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(expectedResponse));
	}

}
