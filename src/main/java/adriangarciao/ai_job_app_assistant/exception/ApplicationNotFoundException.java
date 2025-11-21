package adriangarciao.ai_job_app_assistant.exception;

public class ApplicationNotFoundException extends RuntimeException {
  public ApplicationNotFoundException(Long id) {
    super("Application not found with id " + id);
  }
}
