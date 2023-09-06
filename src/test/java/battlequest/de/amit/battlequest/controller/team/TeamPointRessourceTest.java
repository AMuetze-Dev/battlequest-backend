package battlequest.de.amit.battlequest.controller.team;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.controller.rest.player.PointRessource;
import de.amit.battlequest.controller.rest.team.TeamPointRessource;
import de.amit.battlequest.controller.rest.team.TeamRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Team;
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

import java.util.*;

@SpringBootTest(classes = BattlequestApplication.class)
public class TeamPointRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private TeamPointRessource teamPointRessource;

    @MockBean
    private TeamRessource teamRessource;

    @MockBean
    private PointRessource pointRessource;

    private Long id;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(teamPointRessource).build();
        id = 12345L;
    }

    @Test
    void testAddNullFailure() throws Exception {
        Mockito.when(teamRessource.read(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team ist nicht verfÃ¼gbar.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/add", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testAddNoneFailure() throws Exception {
        Team mockTeam = new Team(id, "mockName", null, null);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"In diesem Team sind keine Spieler.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/add", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testAddSuccess() throws Exception {
        final Player mockPlayer = new Player(UUID.randomUUID(), "testUser", "DummyUser12345", 0, "password", null, null);
        final Team mockTeam = new Team(id, "mockName", null, new ArrayList<Player>());

        mockTeam.addPlayer(mockPlayer);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"Die Punkte wurden erfolgreich hinzugefÃ¼gt.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/add", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testRemoveNullFailure() throws Exception {
        Mockito.when(teamRessource.read(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team ist nicht verfÃ¼gbar.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/remove", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testRemoveNoneFailure() throws Exception {
        Team mockTeam = new Team(id, "mockName", null, null);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"In diesem Team sind keine Spieler.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/remove", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testRemoveSuccess() throws Exception {
        final Player mockPlayer = new Player(UUID.randomUUID(), "testUser", "DummyUser12345", 0, "password", null, null);
        final Team mockTeam = new Team(id, "mockName", null, new ArrayList<Player>());

        mockTeam.addPlayer(mockPlayer);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"Die Punkte wurden erfolgreich abgezogen.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/remove", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testUpdateNullFailure() throws Exception {
        int number = 5;

        Mockito.when(teamRessource.read(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team ist nicht verfÃ¼gbar.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/update/{number}", id, number)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testUpdateNoneFailure() throws Exception {
        int number = 5;

        Team mockTeam = new Team(id, "mockName", null, null);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"In diesem Team sind keine Spieler.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/update/{number}", id, number)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        int number = 5;
        final Team mockTeam = new Team(id, "mockName", null, new ArrayList<Player>());

        List<Player> mockPlayers = new ArrayList<>();
        mockPlayers.add(new Player(UUID.randomUUID(), "Player1", "Nickname1", 0, "password", null, mockTeam));
        mockPlayers.add(new Player(UUID.randomUUID(), "Player2", "Nickname2", 0, "password", null,  mockTeam));
        mockPlayers.add(new Player(UUID.randomUUID(), "Player3", "Nickname3", 0, "password", null, mockTeam));

        mockTeam.setPlayers(mockPlayers);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"Die Punkte wurden erfolgreich aktualisiert\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/points/update/{number}", id, number)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }
}
