package uz.pdp.student_service.service.impl;

import org.slf4j.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pdp.student_service.config.BookClient;
import uz.pdp.student_service.dto.ApiResponse;
import uz.pdp.student_service.dto.StudentCreateDTO;
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
    private final StudentInterfaceMapper studentInterfaceMapper;
    private final BookClient bookClient;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public ApiResponse<StudentCreateDTO> createStudent(StudentCreateDTO studentCreateDTO) {
        if (studentCreateDTO.getAge() < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (studentCreateDTO.getAge() == 0) {
            throw new IllegalArgumentException("Age cannot be zero");
        }
        Student student = studentMapper.toEntity(studentCreateDTO);
        studentRepository.save(student);
        logger.info("Student successfully saved");
        return ApiResponse.<StudentCreateDTO>builder()
                .code(200)
                .message("Student successfully saved")
                .success(true)
                .data(studentInterfaceMapper.toDto(student))
                .build();
    }

    @Override
    public ApiResponse<StudentCreateDTO> getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        logger.info("Student successfully found");
        return ApiResponse.<StudentCreateDTO>builder()
                .code(200)
                .message("Ok")
                .success(true)
                .data(studentMapper.toDto(student))
                .build();
    }

    @Override
    public ApiResponse<List<StudentCreateDTO>> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        logger.info("Student list successfully found");
        return ApiResponse.<List<StudentCreateDTO>>builder()
                .code(200)
                .message("Ok")
                .success(true)
                .data(students.stream().map(studentMapper::toDto).toList())
                .build();
    }

    @Override
    public ApiResponse<StudentCreateDTO> updateStudent(StudentCreateDTO studentCreateDTO) {
        Student existingStudent = studentRepository.findById(studentCreateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentCreateDTO.getId()));
        existingStudent.setFirstName(studentCreateDTO.getFirstname());
        existingStudent.setLastName(studentCreateDTO.getLastname());
        existingStudent.setAge(studentCreateDTO.getAge());
        studentRepository.save(existingStudent);
        logger.info("Student successfully updated");
        return ApiResponse.<StudentCreateDTO>builder()
                .code(200)
                .message("Student successfully updated")
                .success(true)
                .data(studentMapper.toDto(existingStudent))
                .build();
    }

    @Override
    public ApiResponse<Void> deleteStudentById(Long id) {
        bookClient.deleteBookByStudentId(id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        studentRepository.delete(student);
        logger.info("Student successfully deleted");
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Student successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public List<Integer> getAllStudentIds() {
        return studentRepository.getAllStudentIda();
    }
}
