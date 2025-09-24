package uz.pdp.student_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.student_service.dto.response.Response;

@FeignClient("BOOK-SERVICE")
public interface BookClient {
    @DeleteMapping("/api/books/studentId/{studentId}")
    Response<Void> deleteBookByStudentId(@PathVariable Long studentId);
}
