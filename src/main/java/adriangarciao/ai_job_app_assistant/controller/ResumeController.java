package adriangarciao.ai_job_app_assistant.controller;

import adriangarciao.ai_job_app_assistant.dto.ResumeDTO;
import adriangarciao.ai_job_app_assistant.dto.ResumeUploadResponse;
import adriangarciao.ai_job_app_assistant.service.ResumeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private static final Logger log = LoggerFactory.getLogger(ResumeController.class);

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    /**
     * Upload resume for the current authenticated user.
     * No userId in the request body/query anymore.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeDTO> upload(
            @RequestPart("file") @NotNull MultipartFile file,
            Authentication auth
    ) {
        log.info("Resume upload endpoint called for user: {}", auth.getName());
        // principal is email per your JwtAuthFilter
        String email = auth.getName();
        ResumeDTO saved = resumeService.storeForEmail(file, email);

        // 201 Created + Location: /api/resumes/{id}
        return ResponseEntity.created(URI.create("/api/resumes/" + saved.id()))
                .body(saved);
    }

    /**
     * Get a single resume by id (owner or admin).
     */
    @GetMapping("/{id}")
    @PreAuthorize("@authzService.ownsResume(authentication, #id) or hasRole('ADMIN')")
    public ResponseEntity<ResumeDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.get(id));
    }

    /**
     * List *my* resumes (authenticated user).
     */
    @GetMapping
    public ResponseEntity<List<ResumeDTO>> listMine(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(resumeService.listByEmail(email));
    }

    /**
     * OPTIONAL: Admin-only—list resumes for a given userId.
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResumeDTO>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(resumeService.listByUserId(userId));
    }

    /**
     * Download (owner or admin).
     */
    @GetMapping("/{id}/download")
    @PreAuthorize("@authzService.ownsResume(authentication, #id) or hasRole('ADMIN')")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        return resumeService.download(id);
    }

    /**
     * Delete (owner or admin).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@authzService.ownsResume(authentication, #id) or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resumeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}