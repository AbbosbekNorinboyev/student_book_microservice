package uz.pdp.student_service.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.student_service.dto.StudentCreateDTO;
import uz.pdp.student_service.entity.Student;

@Component
public class StudentMapper {

    public Student toEntity(StudentCreateDTO studentCreateDTO) {
        return Student.builder()
                .id(studentCreateDTO.getId())
                .firstName(studentCreateDTO.getFirstname())
                .lastName(studentCreateDTO.getLastname())
                .age(studentCreateDTO.getAge())
                .build();
    }

    public StudentCreateDTO toDto(Student student) {
        return StudentCreateDTO.builder()
                .id(student.getId())
                .firstname(student.getFirstName())
                .lastname(student.getLastName())
                .age(student.getAge())
                .build();
    }
}
