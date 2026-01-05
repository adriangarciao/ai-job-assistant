package adriangarciao.ai_job_app_assistant.dto;

import java.util.List;

public record ResumeParseResponse(
        String rawText,
        List<String> skills,
        List<String> experiences
) {}
