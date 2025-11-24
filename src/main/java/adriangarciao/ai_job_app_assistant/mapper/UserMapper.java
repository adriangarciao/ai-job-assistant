package adriangarciao.ai_job_app_assistant.mapper;

import adriangarciao.ai_job_app_assistant.dto.UserCreateDTO;
import adriangarciao.ai_job_app_assistant.dto.UserDTO;
import adriangarciao.ai_job_app_assistant.dto.UserUpdateDTO;
import adriangarciao.ai_job_app_assistant.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "role", ignore = true)
    User fromCreateDto(UserCreateDTO dto);


    // partial updates, ignore nulls
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "role", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User user);
}
