package uz.pdp.student_service.service.impl;

import org.slf4j.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.student_service.config.BookClient;
import uz.pdp.student_service.dto.StudentDto;
import uz.pdp.student_service.dto.response.Response;
import uz.pdp.student_service.entity.Student;
import uz.pdp.student_service.exception.ResourceNotFoundException;
import uz.pdp.student_service.mapper.StudentMapper;
import uz.pdp.student_service.mapper.interfaces.StudentInterfaceMapper;
import uz.pdp.student_service.repository.StudentRepository;
import uz.pdp.student_service.service.StudentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final BookClient bookClient;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Response<?> createStudent(StudentDto studentDto) {
        if (studentDto.getAge() < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (studentDto.getAge() == 0) {
            throw new IllegalArgumentException("Age cannot be zero");
        }
        Student student = studentMapper.toEntity(studentDto);
        studentRepository.save(student);
        logger.info("Student successfully saved");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully saved")
                .success(true)
                .build();
    }

    @Override
    public Response<?> getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        logger.info("Student successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Ok")
                .success(true)
                .data(studentMapper.toDto(student))
                .build();
    }

    @Override
    public Response<?> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        logger.info("Student list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Ok")
                .success(true)
                .data(students.stream().map(studentMapper::toDto).toList())
                .build();
    }

    @Override
    public Response<?> updateStudent(StudentDto studentDto) {
        Student existingStudent = studentRepository.findById(studentDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentDto.getId()));
        existingStudent.setFirstName(studentDto.getFirstname());
        existingStudent.setLastName(studentDto.getLastname());
        existingStudent.setAge(studentDto.getAge());
        studentRepository.save(existingStudent);
        logger.info("Student successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully updated")
                .success(true)
                .build();
    }

    @Override
    public Response<?> deleteStudentById(Long id) {
        bookClient.deleteBookByStudentId(id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        studentRepository.delete(student);
        logger.info("Student successfully deleted");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public List<Integer> getAllStudentIds() {
        return studentRepository.getAllStudentIda();
    }
}
