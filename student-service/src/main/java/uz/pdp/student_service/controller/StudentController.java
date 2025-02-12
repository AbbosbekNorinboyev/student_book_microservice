package uz.pdp.student_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.student_service.dto.ApiResponse;
import uz.pdp.student_service.dto.StudentCreateDTO;
import uz.pdp.student_service.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ApiResponse<StudentCreateDTO> createStudent(@Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        return studentService.createStudent(studentCreateDTO);
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentCreateDTO> getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping
    public ApiResponse<List<StudentCreateDTO>> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping
    public ApiResponse<StudentCreateDTO> updateStudent(@Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        return studentService.updateStudent(studentCreateDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudentById(id);
    }

    @GetMapping("/ids/list")
    public List<Integer> getAllStudentIds() {
        return studentService.getAllStudentIds();
    }
}
