package adriangarciao.ai_job_app_assistant.mapper;

import adriangarciao.ai_job_app_assistant.dto.ApplicationCreateDTO;
import adriangarciao.ai_job_app_assistant.dto.ApplicationDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    // Entity -> DTO (lift user.id into userId)
    @Mapping(source = "user.id", target = "userId")
    ApplicationDTO toDto(adriangarciao.ai_job_app_assistant.model.Application application);

    // Create DTO -> Entity (DB generates id; service sets user)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    adriangarciao.ai_job_app_assistant.model.Application fromCreateDto(ApplicationCreateDTO dto);

    // Update: ignore nulls and never touch id via DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true) // keep association updates explicit in service
    void updateApplicationFromDto(
            ApplicationDTO dto,
            @MappingTarget adriangarciao.ai_job_app_assistant.model.Application application
    );
}