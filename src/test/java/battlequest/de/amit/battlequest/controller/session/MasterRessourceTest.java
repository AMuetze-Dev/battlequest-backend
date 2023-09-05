package battlequest.de.amit.battlequest.controller.session;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.controller.rest.session.MasterRessource;
import de.amit.battlequest.controller.rest.session.SessionRepository;
import de.amit.battlequest.controller.rest.session.SessionRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Session;
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
public class MasterRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private MasterRessource masterRessource;

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private SessionRessource sessionRessource;

    @MockBean
    private PlayerRessource playerRessource;

    private Session mockSession;
    private String code;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(masterRessource).build();
        mockSession = new Session();
        code = "rightCode";
        mockSession.setCode(code);
    }

    @Test
    void testDeleteNullFailure() throws Exception {
        String wrongCode = "wrongCode";

        Mockito.when(sessionRessource.read(wrongCode)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Lobby existiert nicht\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/session/{code}/master", wrongCode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteNoneFailure() throws Exception {
        Mockito.when(sessionRessource.read(code)).thenReturn(mockSession);

        final String expectedAnswer = "{\"message\":\"Es gibt keinen Spielleiter\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/session/{code}/master", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        mockSession.setMaster(new Player("mockMaster", "password"));

        Mockito.when(sessionRessource.read(code)).thenReturn(mockSession);

        final String expectedAnswer = "{\"message\":\"Spielleiter wurde entfernt\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/session/{code}/master", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testUpdateLobbyNullFailure() throws Exception {
        UUID uuid = UUID.randomUUID();

        String wrongCode = "wrongCode";

        Mockito.when(sessionRessource.read(wrongCode)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Lobby existiert nicht\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/session/{code}/master", wrongCode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("\"" + uuid + "\""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateMasterNullFailure() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(sessionRessource.read(code)).thenReturn(mockSession);
        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Spielleiter existiert nicht\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/session/{code}/master", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("\"" + uuid + "\""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateMasterIsFailure() throws Exception {
        UUID uuid = UUID.randomUUID();

        final Player mockMaster = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        mockMaster.setSession(mockSession);
        mockSession.setMaster(mockMaster);

        Mockito.when(sessionRessource.read(code)).thenReturn(mockSession);
        Mockito.when(playerRessource.read(uuid)).thenReturn(mockMaster);

        final String expectedAnswer = "{\"message\":\"Ist bereits Spielleiter der Lobby\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/session/{code}/master", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("\"" + uuid + "\""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateMasterIsNotFailure() throws Exception {
        UUID uuid = UUID.randomUUID();

        final Player mockMaster = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        Session wrongSession = new Session();

        mockMaster.setSession(wrongSession);

        Mockito.when(sessionRessource.read(code)).thenReturn(mockSession);
        Mockito.when(playerRessource.read(uuid)).thenReturn(mockMaster);

        final String expectedAnswer = "{\"message\":\"Spielleiter gehÃ¶rt einer anderen Lobby an\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/session/{code}/master", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("\"" + uuid + "\""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.never()).save(Mockito.any());

        assert mockMaster.getSession() != mockSession;
    }

    @Test
    void testUpdateSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();

        final Player mockMaster = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null);

        mockMaster.setSession(mockSession);

        Mockito.when(sessionRessource.read(code)).thenReturn(mockSession);
        Mockito.when(playerRessource.read(uuid)).thenReturn(mockMaster);

        final String expectedAnswer = "{\"message\":\"Spielleiter wurde gesetzt\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/session/{code}/master", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("\"" + uuid + "\""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any());

        assert mockMaster.getSession() == mockSession;
    }
}
