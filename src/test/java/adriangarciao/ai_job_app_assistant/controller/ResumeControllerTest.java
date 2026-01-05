package adriangarciao.ai_job_app_assistant.controller;

import adriangarciao.ai_job_app_assistant.service.ResumeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResumeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ResumeControllerTest.TestConfig.class)
class ResumeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResumeService resumeService;
    

    @Test
    void getResumeById_returnsOk() throws Exception {
        when(resumeService.get(anyLong())).thenReturn(Mockito.mock(adriangarciao.ai_job_app_assistant.dto.ResumeDTO.class));
        mockMvc.perform(get("/api/resumes/1"))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ResumeService resumeService() { return Mockito.mock(ResumeService.class); }

        @Bean
        public adriangarciao.ai_job_app_assistant.repository.UserRepository userRepository() {
            return Mockito.mock(adriangarciao.ai_job_app_assistant.repository.UserRepository.class);
        }

        @Bean
        public adriangarciao.ai_job_app_assistant.service.JwtService jwtService() {
            return Mockito.mock(adriangarciao.ai_job_app_assistant.service.JwtService.class);
        }

        @Bean
        public adriangarciao.ai_job_app_assistant.service.ai.ParserService parserService() {
            return Mockito.mock(adriangarciao.ai_job_app_assistant.service.ai.ParserService.class);
        }
    }
}

