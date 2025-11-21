package adriangarciao.ai_job_app_assistant.exception;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(Long id) {
        super("Resume not found with id " + id);
    }
}