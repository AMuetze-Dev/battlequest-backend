package battlequest.de.amit.battlequest.controller.team;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.controller.rest.team.LeaderRessource;
import de.amit.battlequest.controller.rest.team.TeamRepository;
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

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest(classes = BattlequestApplication.class)
public class LeaderRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private LeaderRessource leaderRessource;

    @MockBean
    private TeamRessource teamRessource;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private PlayerRessource playerRessource;

    private Long id;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(leaderRessource).build();
        id = 12345L;
    }

    @Test
    void testDeleteNullFailure() throws Exception {
        Mockito.when(teamRessource.read(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team existiert nicht.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}/leader", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteNoneFailure() throws Exception {
        Team mockTeam = new Team(id, "mockName", null, new ArrayList<>());

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.getLeader(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team hat keinen zu Entfernenden Leader.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}/leader", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        final Player mockPlayer = new Player(UUID.randomUUID(), "testUser", "DummyUser12345", 0, "password", null, null);
        Team mockTeam = new Team(id, "mockName", mockPlayer, new ArrayList<>());

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.getLeader(id)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"message\":\"Der Leader wurde entfernt.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}/leader", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testReadFailure() throws Exception {
        Team mockTeam = new Team(id, "mockName", null, new ArrayList<>());

        Mockito.when(teamRessource.getLeader(id)).thenReturn(null);

        final String expectedAnswer = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/teams/{id}/leader", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testReadSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, null);
        Team mockTeam = new Team(id, "mockName", mockPlayer, new ArrayList<>());

        Mockito.when(teamRessource.getLeader(id)).thenReturn(mockPlayer);

        final String expectedAnswer = "{\"uuid\":\"" + uuid + "\",\"nickname\":\"DummyUser12345\",\"points\":0}";

        mockMvc.perform(MockMvcRequestBuilders.get("/teams/{id}/leader", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testRandomUpdateNullFailure() throws Exception {
        Mockito.when(teamRessource.read(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team existiert nicht.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/random", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testRandomUpdateNoneFailure() throws Exception {
        Team mockTeam = new Team(id, "mockName", null, new ArrayList<>());

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.getNumberPlayers(mockTeam)).thenReturn(0);
        Mockito.when(teamRessource.getPlayers(mockTeam)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Es gibt keine Spieler in diesem Team\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/random", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testRandomUpdateOtherFailure() throws Exception {
        UUID uuid = UUID.randomUUID();
        Team mockTeam = new Team(id, "mockName", null, new ArrayList<>());
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, mockTeam);
        final Player otherPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, mockTeam);
        mockTeam.setLeader(otherPlayer);
        mockTeam.addPlayer(otherPlayer);
        mockTeam.addPlayer(mockPlayer);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.getNumberPlayers(mockTeam)).thenReturn(1);
        Mockito.when(teamRessource.getPlayers(mockTeam)).thenReturn(mockTeam.getPlayers());
        Mockito.when(teamRessource.getLeader(id)).thenReturn(otherPlayer);

        final String expectedAnswer = "{\"message\":\"Es gibt bereits einen Leader.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/random", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testRandomUpdateSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        Team mockTeam = new Team(id, "mockName", null, new ArrayList<>());
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, mockTeam);
        final Player otherPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, mockTeam);
        mockTeam.addPlayer(otherPlayer);
        mockTeam.addPlayer(mockPlayer);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.getNumberPlayers(mockTeam)).thenReturn(1);
        Mockito.when(teamRessource.getPlayers(mockTeam)).thenReturn(mockTeam.getPlayers());
        Mockito.when(teamRessource.getLeader(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Der Spieler wurde zum Leader ernannt.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/random", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testUuidUpdatePlayerNullFailure() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(playerRessource.read(uuid)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Der Spieler existiert nicht.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUuidUpdateTeamNullFailure() throws Exception {
        UUID uuid = UUID.randomUUID();
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, null);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);
        Mockito.when(teamRessource.read(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team existiert nicht\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUuidUpdateOtherFailure() throws Exception {
        UUID uuid = UUID.randomUUID();
        Team mockTeam = new Team(id, "mockName", null, new ArrayList<>());
        Team otherTeam = new Team(13579L, "otherTeam", null, new ArrayList<>());
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, otherTeam);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);
        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.getTeam(mockPlayer)).thenReturn(otherTeam);

        final String expectedAnswer = "{\"message\":\"Dieser Spieler ist kein Mitglied des Teams.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUuidUpdateSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        Team mockTeam = new Team(id, "mockName", null, new ArrayList<>());
        final Player mockPlayer = new Player(uuid, "testUser", "DummyUser12345", 0, "password", null, mockTeam);
        mockTeam.addPlayer(mockPlayer);

        Mockito.when(playerRessource.read(uuid)).thenReturn(mockPlayer);
        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.getTeam(mockPlayer)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"Der Spieler wurde als Leader gesetzt.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/leader/{uuid}", id, uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.times(1)).save(Mockito.any());
    }
}
