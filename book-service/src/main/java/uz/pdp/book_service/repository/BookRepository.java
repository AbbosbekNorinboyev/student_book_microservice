package uz.pdp.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.book_service.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from book b where b.student_id = ?1", nativeQuery = true)
    void deleteBookByStudentId(Long studentId);
}
