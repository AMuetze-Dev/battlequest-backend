package battlequest.de.amit.battlequest.controller.player;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.PlayerRepository;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Credentials;
import de.amit.battlequest.model.Player;
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

import java.util.UUID;

@SpringBootTest(classes = BattlequestApplication.class)
public class PlayerRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private PlayerRessource playerRessource;

    @MockBean
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(playerRessource).build();
    }

    @Test
    void testCreateFailure() throws Exception {
        final Player mockPlayer = new Player(UUID.randomUUID(), "testUser", "DummyUser12345", 0, "password", null);

        Credentials credentials = new Credentials("testUser", "password", "");

        String username = credentials.getUsername();

        Mockito.when(playerRessource.read(username)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Spieler existiert bereits\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testUser\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testCreateSuccess() throws Exception {
        Credentials credentials = new Credentials("testUser", "password", "");

        String username = credentials.getUsername();

        Mockito.when(playerRessource.read(username)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Spieler wurde angelegt\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testUser\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testDeleteFailure() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Spieler existiert nicht\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/player/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();

        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Spieler wurde entfernt\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/player/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository,Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void testReadFailure() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/player/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testReadSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();

        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"uuid\":\"" + uuid + "\",\"nickname\":\"DummyUser12345\",\"points\":0}";

        mockMvc.perform(MockMvcRequestBuilders.get("/player/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }
}
