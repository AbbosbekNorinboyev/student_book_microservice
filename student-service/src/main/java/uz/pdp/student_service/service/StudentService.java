package uz.pdp.student_service.service;

import uz.pdp.student_service.dto.StudentDto;
import uz.pdp.student_service.dto.response.Response;

import java.util.List;

public interface StudentService {
    Response<?> createStudent(StudentDto studentDto);

    Response<?> getStudent(Long id);

    Response<?> getAllStudent();

    Response<?> updateStudent(StudentDto studentDto);

    Response<?> deleteStudentById(Long id);

    List<Integer> getAllStudentIds();
}
