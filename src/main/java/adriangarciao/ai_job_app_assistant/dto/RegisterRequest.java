package adriangarciao.ai_job_app_assistant.dto;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
