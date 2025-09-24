package uz.pdp.student_service.mapper.interfaces;

import org.mapstruct.*;
import uz.pdp.student_service.dto.StudentDto;
import uz.pdp.student_service.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentInterfaceMapper {
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    Student toEntity(StudentDto studentDto);
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    StudentDto toDto(Student student);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    void updateToEntityFromDto(@MappingTarget Student student, StudentDto studentDto);
}
