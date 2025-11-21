package adriangarciao.ai_job_app_assistant.mapper;

import adriangarciao.ai_job_app_assistant.dto.ResumeDTO;
import adriangarciao.ai_job_app_assistant.model.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResumeMapper {

    @Mapping(source = "user.id", target = "userId")
    ResumeDTO toDto(Resume resume);
}
