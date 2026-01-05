package adriangarciao.ai_job_app_assistant.dto;

import java.time.LocalDateTime;

public record ResumeDTO(
        Long id,
        Long userId,
        String originalFilename,
        String contentType,
        Long sizeBytes,
        LocalDateTime uploadedAt
        , String parsedText
) {}
