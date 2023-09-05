package battlequest.de.amit.battlequest.controller.player;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.PasswordRessource;
import de.amit.battlequest.controller.rest.player.PlayerRepository;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Credentials;
import de.amit.battlequest.model.Player;
import org.checkerframework.checker.units.qual.C;
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
public class PasswordRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private PasswordRessource passwordRessource;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private PlayerRessource playerRessource;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(passwordRessource).build();
    }

    @Test
    void testChangeNullFailure() throws Exception {
        final UUID uuid = UUID.randomUUID();

        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Der angegebene Spieler konnte nicht gefunden werden\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/password/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"oldPassword\",\"newPassword\":\"newPassword\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testChangeWrongFailure() throws Exception {
        final UUID uuid = UUID.randomUUID();

        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Das alte Passwort ist nicht korrekt\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/password/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"wrongPassword\",\"newPassword\":\"newPassword\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testChangeSuccess() throws Exception {
        final UUID uuid = UUID.randomUUID();

        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Das Passwort wurde erfolgreich geÃ¤ndert\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/player/password/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"password\",\"newPassword\":\"newPassword\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(playerRepository, Mockito.times(1)).save(mockPlayer);
    }

    @Test
    void testValidateFailure() throws Exception {
        final Credentials credentials = new Credentials("username", "password", "");

        Mockito.when(playerRessource.read(credentials.getUsername())).thenReturn(null);

        final String expectedAnswer = "false";

        mockMvc.perform(MockMvcRequestBuilders.get("/player/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"otherUsername\",\"password\":\"password\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testValidateSuccess() throws Exception {
        final Player mockPlayer = new Player(UUID.randomUUID(), "testUser", "DummyUser12345", 0, "password", null);
        final Credentials credentials = new Credentials(mockPlayer.getUsername(), mockPlayer.getPassword(), "");

        Mockito.when(playerRessource.read(credentials.getUsername())).thenReturn(mockPlayer);

        final String expectedAnswer = "true";

        mockMvc.perform(MockMvcRequestBuilders.get("/player/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"password\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }
}
