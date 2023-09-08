package battlequest.de.amit.battlequest.controller.team;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.controller.rest.team.TeamPlayerRessource;
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

import java.util.UUID;

@SpringBootTest(classes = BattlequestApplication.class)
public class TeamPlayerRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private TeamPlayerRessource teamPlayerRessource;

    @MockBean
    private TeamRessource teamRessource;

    @MockBean
    private PlayerRessource playerRessource;

    private Long id;

    private UUID uuid;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(teamPlayerRessource).build();
        id = 12345L;
        uuid = UUID.randomUUID();
    }

    @Test
    void testDeleteFailure() throws Exception {
        Mockito.when(teamRessource.checkUser(id, uuid)).thenReturn(false);

        final String expectedAnswer = "{\"message\":\"Team oder Spieler ist nicht verfÃ¼gbar.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}/players/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testDeleteSuccess() throws Exception {
        Team mockTeam = new Team();
        mockTeam.setTeamId(id);
        Player mockPlayer = new Player(uuid, "mockPlayer", "nickname", 0, "password", null, mockTeam);
        mockTeam.addPlayer(mockPlayer);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);
        Mockito.when(teamRessource.checkUser(id, uuid)).thenReturn(true);

        final String expectedAnswer = "{\"message\":\"Der Spieler wurde entfernt.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}/players/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testUpdateNullFailure() throws Exception {
        Mockito.when(teamRessource.checkUser(id, uuid)).thenReturn(false);

        final String expectedAnswer = "{\"message\":\"Lobby oder Spieler ist nicht verfÃ¼gbar.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/players/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testUpdateAlreadyFailure() throws Exception {
        Team mockTeam = new Team();
        mockTeam.setTeamId(id);

        Team otherTeam = new Team();
        otherTeam.setTeamId(13579L);

        Player mockPlayer = new Player(uuid, "mockPlayer", "nickname", 0, "password", null, otherTeam);
        otherTeam.addPlayer(mockPlayer);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);
        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.checkUser(id, uuid)).thenReturn(true);
        Mockito.when(teamRessource.getTeam(mockPlayer)).thenReturn(otherTeam);

        final String expectedAnswer = "{\"message\":\"Spieler ist schon Teil eines Teams.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/players/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        Team mockTeam = new Team();
        mockTeam.setTeamId(id);
        Player mockPlayer = new Player(uuid, "mockPlayer", "nickname", 0, "password", null, null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);
        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.checkUser(id, uuid)).thenReturn(true);
        Mockito.when(teamRessource.getTeam(mockPlayer)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Spieler wurde erfolgreich zum Team hinzugefÃ¼gt.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/players/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }
}
