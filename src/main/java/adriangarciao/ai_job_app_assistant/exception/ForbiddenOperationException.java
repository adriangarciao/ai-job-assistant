package adriangarciao.ai_job_app_assistant.exception;

public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException(String msg) {
        super(msg);
    }
}