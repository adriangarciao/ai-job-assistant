package adriangarciao.ai_job_app_assistant.controller;
import static org.mockito.Mockito.when;
import adriangarciao.ai_job_app_assistant.model.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(MeControllerTest.TestConfig.class)
class MeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private adriangarciao.ai_job_app_assistant.repository.UserRepository userRepository;
    

    @Test
    void getMe_returnsOk() throws Exception {
        var user = Mockito.mock(adriangarciao.ai_job_app_assistant.model.User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getEmail()).thenReturn("test@example.com");
        when(user.getName()).thenReturn("Test User");
        when(user.getRole()).thenReturn(Role.USER);
        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(user));

        org.springframework.security.core.Authentication auth = Mockito.mock(org.springframework.security.core.Authentication.class);
        Mockito.when(auth.getName()).thenReturn("test@example.com");

        mockMvc.perform(get("/api/me").principal(auth))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public adriangarciao.ai_job_app_assistant.repository.UserRepository userRepository() {
            return Mockito.mock(adriangarciao.ai_job_app_assistant.repository.UserRepository.class);
        }

        @Bean
        public adriangarciao.ai_job_app_assistant.service.JwtService jwtService() {
            return Mockito.mock(adriangarciao.ai_job_app_assistant.service.JwtService.class);
        }
    }
}

