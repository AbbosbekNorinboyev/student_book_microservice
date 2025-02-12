package uz.pdp.student_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.student_service.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "select s.id from student s", nativeQuery = true)
    List<Integer> getAllStudentIda();
}
