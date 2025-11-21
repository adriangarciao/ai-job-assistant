package adriangarciao.ai_job_app_assistant.repository;

import adriangarciao.ai_job_app_assistant.model.Resume;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUserId(Long userId);

    @Query("select r.user.email from Resume r where r.id = :id")
    Optional<String> findOwnerEmailById(@Param("id") Long id);

    @Query("select r.user.id from Resume r where r.id = :id")
    Optional<Long> findOwnerIdByResumeId(@Param("id") Long id);
}
