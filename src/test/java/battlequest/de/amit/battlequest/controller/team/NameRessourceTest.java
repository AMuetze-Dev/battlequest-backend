package battlequest.de.amit.battlequest.controller.team;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.team.NameRessource;
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

import java.util.ArrayList;

@SpringBootTest(classes = BattlequestApplication.class)
public class NameRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private NameRessource nameRessource;

    @MockBean
    private TeamRessource teamRessource;

    @MockBean
    private TeamRepository teamRepository;

    private Long id;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(nameRessource).build();
        id = 12345L;
    }

    @Test
    void testUpdateNullFailure() throws Exception {
        String name = "mockName";

        Mockito.when(teamRessource.read(id)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Das Team existiert nicht.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/name", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"" + name + "\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateTakenFailure() throws Exception {
        String name = "mockName";
        Team mockTeam = new Team();
        mockTeam.setTeamId(id);
        Team otherTeam = new Team(13579L, name, null, new ArrayList<>());

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.read(teamRessource.readName(name))).thenReturn(otherTeam);
        System.out.println(teamRessource.read(teamRessource.readName(name)));

        final String expectedAnswer = "{\"message\":\"Der Name ist schon vergeben.\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/name", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"" + name + "\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.never()).save(Mockito.any());
    }


    @Test
    void testUpdateSuccess() throws Exception {
        String name = "mockName";
        Team mockTeam = new Team();
        mockTeam.setTeamId(id);

        Mockito.when(teamRessource.read(id)).thenReturn(mockTeam);
        Mockito.when(teamRessource.read(name)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Teamname wurde geÃ¤ndert.\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/name", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"" + name + "\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(teamRepository, Mockito.times(1)).save(Mockito.any());
    }
}
