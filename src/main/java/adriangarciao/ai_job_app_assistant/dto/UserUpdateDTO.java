package adriangarciao.ai_job_app_assistant.dto;

import java.util.List;

public record UserUpdateDTO(
        String name,
        String email,
        Integer age,
        Integer gradYear,
        String college,
        String major,
        List<String> desiredJobTitle,
        List<String> skills,
        List<String> experience
) {
}
