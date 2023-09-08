package battlequest.de.amit.battlequest.controller.team;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.controller.rest.team.TeamRepository;
import de.amit.battlequest.controller.rest.team.TeamRessource;
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

import java.util.Optional;

@SpringBootTest(classes = BattlequestApplication.class)
public class TeamRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private TeamRessource teamRessource;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private PlayerRessource playerRessource;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(teamRessource).build();
    }

    @Test
    void testCreateSuccess() throws Exception {
        final String expectedAnswer = "{\"message\":\"Team wurde erstellt.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testDeleteFailure() throws Exception {
        Long id = 12345L;

        final String expectedAnswer = "{\"message\":\"Team ist nicht verfÃ¼gbar.\",\"success\":false}";

        Mockito.when(teamRessource.read(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        Long id = 12345L;
        Team mockTeam = new Team(id, "mockName", null, null);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"message\":\"Team wurde gelÃ¶scht.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void testReadIdFailure() throws Exception {
        Long id = 12345L;

        Mockito.when(teamRepository.findByTeamId(id)).thenReturn(null);

        final String expectedAnswer = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/teams/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testReadIdSuccess() throws Exception {
        Long id = 12345L;
        Team mockTeam = new Team(id, "mockName", null, null);

        Mockito.when(teamRepository.findByTeamId(id)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"teamId\":12345,\"teamname\":\"mockName\",\"leader\":null,\"players\":null}";

        mockMvc.perform(MockMvcRequestBuilders.get("/teams/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testReadFailure() throws Exception {
        String teamname = "mockName";

        Mockito.when(teamRepository.findByTeamname(teamname)).thenReturn(null);

        final String expectedAnswer = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/teams", teamname)
                .contentType(MediaType.TEXT_PLAIN)
                .content("mockName")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testReadSuccess() throws Exception {
        String teamname = "mockName";
        Team mockTeam = new Team(12345L, teamname, null, null);

        Mockito.when(teamRepository.findByTeamname(teamname)).thenReturn(mockTeam);

        final String expectedAnswer = "{\"teamId\":12345,\"teamname\":\"mockName\",\"leader\":null,\"players\":null}";

        mockMvc.perform(MockMvcRequestBuilders.get("/teams", teamname)
                .contentType(MediaType.TEXT_PLAIN)
                .content("mockName")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }
}

