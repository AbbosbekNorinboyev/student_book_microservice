package uz.pdp.student_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.student_service.dto.StudentDto;
import uz.pdp.student_service.dto.response.Response;
import uz.pdp.student_service.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public Response<?> createStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto);
    }

    @GetMapping("/{id}")
    public Response<?> getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping
    public Response<?> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping
    public Response<?> updateStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(studentDto);
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudentById(id);
    }

    @GetMapping("/ids/list")
    public List<Integer> getAllStudentIds() {
        return studentService.getAllStudentIds();
    }
}
