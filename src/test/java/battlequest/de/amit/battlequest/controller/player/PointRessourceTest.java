package battlequest.de.amit.battlequest.controller.player;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.PlayerRepository;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.controller.rest.player.PointRessource;
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
public class PointRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private PointRessource pointRessource;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private PlayerRessource playerRessource;

    private UUID uuid;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pointRessource).build();
        uuid = UUID.randomUUID();
    }

    @Test
    void testDecreaseFailure() throws Exception {
        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Der angegebene Spieler konnte nicht gefunden werden\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/decrease", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDecreaseSuccess() throws Exception {
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 10, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Die Punkte wurden erfolgreich aktualisiert\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/decrease", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.times(1)).save(mockPlayer);

        assert mockPlayer.getPoints() == 9;
    }

    @Test
    void testGetFailure() throws Exception {
        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "-1";

        mockMvc.perform(MockMvcRequestBuilders.get("/player/{uuid}/points", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testGetSuccess() throws Exception {
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 10, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "10";

        mockMvc.perform(MockMvcRequestBuilders.get("/player/{uuid}/points", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testIncreaseFailure() throws Exception {
        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Der angegebene Spieler konnte nicht gefunden werden\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/increase", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testIncreaseSuccess() throws Exception {
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 10, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Die Punkte wurden erfolgreich aktualisiert\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/increase", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.times(1)).save(mockPlayer);

        assert mockPlayer.getPoints() == 11;
    }

    @Test
    void testResetFailure() throws Exception {
        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Der angegebene Spieler konnte nicht gefunden werden\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/reset", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testResetSuccess() throws Exception {
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 10, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Die Punkte wurden erfolgreich zurÃ¼ckgesetzt\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/reset", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.times(1)).save(mockPlayer);

        assert mockPlayer.getPoints() == 0;
    }

    @Test
    void testSetFailure() throws Exception {
        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Der angegebene Spieler konnte nicht gefunden werden\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/set", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content("13")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testSetSuccess() throws Exception {
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 10, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Die Punkte wurden erfolgreich gesetzt\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/points/set", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content("13")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.times(1)).save(mockPlayer);

        assert mockPlayer.getPoints() == 13;
    }
}
