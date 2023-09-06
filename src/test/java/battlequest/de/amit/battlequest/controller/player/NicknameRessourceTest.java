package battlequest.de.amit.battlequest.controller.player;

import java.util.UUID;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.NicknameRessource;
import de.amit.battlequest.controller.rest.player.PlayerRepository;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
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

@SpringBootTest(classes = BattlequestApplication.class)
public class NicknameRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private NicknameRessource nicknameRessource;

    @MockBean
    private PlayerRessource playerRessource;

    @MockBean
    private PlayerRepository playerRepository;

    private UUID uuid;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(nicknameRessource).build();
        uuid = UUID.randomUUID();
    }

    @Test
    void testReadFailure() throws Exception {
        Mockito.when(playerRessource.read(Mockito.anyString())).thenReturn(null);

        final String expectedResponse = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/player/{uuid}/nickname", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
    }

    @Test
        void testReadSuccess() throws Exception {
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        Mockito.when(playerRessource.read(Mockito.any(UUID.class))).thenReturn(mockPlayer);

        final String expectedResponse = mockPlayer.getNickname();

        mockMvc.perform(MockMvcRequestBuilders.get("/player/{uuid}/nickname", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
    }

    @Test
    void testUpdateFailure() throws Exception {
        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedResponse = "{\"message\":\"Spieler existiert nicht\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/nickname", uuid)
                .contentType(MediaType.TEXT_PLAIN)
                .content("newNickname")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateSuccess() throws Exception {
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedResponse = "{\"message\":\"Nutzername wurde angepasst\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/{uuid}/nickname", uuid)
                .contentType(MediaType.TEXT_PLAIN)
                .content("newNickname")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));

        Mockito.verify(playerRepository, Mockito.times(1)).save(mockPlayer);
    }
}