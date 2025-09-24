package uz.pdp.student_service.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.student_service.dto.StudentDto;
import uz.pdp.student_service.entity.Student;

@Component
public class StudentMapper {

    public Student toEntity(StudentDto studentDto) {
        return Student.builder()
                .id(studentDto.getId())
                .firstName(studentDto.getFirstname())
                .lastName(studentDto.getLastname())
                .age(studentDto.getAge())
                .build();
    }

    public StudentDto toDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .firstname(student.getFirstName())
                .lastname(student.getLastName())
                .age(student.getAge())
                .build();
    }
}
