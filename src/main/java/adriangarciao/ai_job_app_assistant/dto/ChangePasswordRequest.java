package adriangarciao.ai_job_app_assistant.dto;

public record ChangePasswordRequest(
        @jakarta.validation.constraints.NotBlank String currentPassword,
        @jakarta.validation.constraints.Size(min=8, max=128) String newPassword
) {}
