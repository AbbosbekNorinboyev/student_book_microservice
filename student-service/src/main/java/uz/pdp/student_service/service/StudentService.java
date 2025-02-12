package uz.pdp.student_service.service;

import uz.pdp.student_service.dto.ApiResponse;
import uz.pdp.student_service.dto.StudentCreateDTO;

import java.util.List;

public interface StudentService {
    ApiResponse<StudentCreateDTO> createStudent(StudentCreateDTO studentCreateDTO);
    ApiResponse<StudentCreateDTO> getStudent(Long id);
    ApiResponse<List<StudentCreateDTO>> getAllStudent();
    ApiResponse<StudentCreateDTO> updateStudent(StudentCreateDTO studentCreateDTO);
    ApiResponse<Void> deleteStudentById(Long id);
    List<Integer> getAllStudentIds();

}
