package adriangarciao.ai_job_app_assistant.repository;

import adriangarciao.ai_job_app_assistant.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Email findByEmail(Email email);

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
