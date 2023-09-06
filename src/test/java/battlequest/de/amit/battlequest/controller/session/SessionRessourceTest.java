package battlequest.de.amit.battlequest.controller.session;

import de.amit.battlequest.BattlequestApplication;
import de.amit.battlequest.controller.rest.session.SessionRepository;
import de.amit.battlequest.controller.rest.session.SessionRessource;
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
public class SessionRessourceTest {

    private MockMvc mockMvc;

    @Autowired
    private SessionRessource sessionRessource;

    @MockBean
    private SessionRepository sessionRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sessionRessource).build();
    }

    @Test
    void testCreateSuccess() throws Exception {
        final String expectedAnswer = "{\"message\":\"Lobby wurde angelegt\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testDeleteFailure() throws Exception {
        String code = "code";

        Mockito.when(sessionRessource.read(code)).thenReturn(null);

        final String expectedAnswer = "{\"message\":\"Lobby existiert nicht\",\"success\":false}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/session/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        String code = "code";

        Session mockSession = new Session();
        mockSession.setCode(code);

        Mockito.when(sessionRessource.read(code)).thenReturn(mockSession);

        final String expectedAnswer = "{\"message\":\"Lobby wurde gelÃ¶scht\",\"success\":true}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/session/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));

        Mockito.verify(sessionRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void testReadFailure() throws Exception {
        String code = "code";

        Mockito.when(sessionRepository.findByCode(code)).thenReturn(null);

        final String expectedAnswer = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/session/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }

    @Test
    void testReadSuccess() throws Exception {
        String code = "code";

        Session mockSession = new Session();
        mockSession.setCode(code);

        Mockito.when(sessionRepository.findByCode(code)).thenReturn(mockSession);

        final String expectedAnswer = "{\"code\":\"code\",\"master\":null,\"players\":[]}";

        mockMvc.perform(MockMvcRequestBuilders.get("/session/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedAnswer));
    }
}
