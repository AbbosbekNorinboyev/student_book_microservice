package uz.pdp.book_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentClient {
    @GetMapping("/api/students/ids/list")
    List<Integer> getIds();
}
