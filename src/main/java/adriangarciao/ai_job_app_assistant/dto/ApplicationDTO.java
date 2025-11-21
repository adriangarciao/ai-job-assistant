package adriangarciao.ai_job_app_assistant.dto;

import adriangarciao.ai_job_app_assistant.model.ApplicationStatus;

import java.time.LocalDate;

public record ApplicationDTO (
        Long id,
        String jobTitle,
        String company,
        ApplicationStatus status,
        LocalDate appliedDate,
        Integer compensation,
        Long userId
){}
