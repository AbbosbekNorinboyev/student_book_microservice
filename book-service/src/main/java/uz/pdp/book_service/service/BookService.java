package uz.pdp.book_service.service;

import uz.pdp.book_service.dto.BookDto;
import uz.pdp.book_service.dto.response.Response;

import java.util.List;

public interface BookService {
    Response<?> createBook(BookDto bookDto);

    Response<?> getBookById(Long id);

    Response<?> getAllBook();

    Response<?> updateBook(BookDto bookDto);

    Response<?> deleteBookById(Long studentId);

    Response<?> deleteBookByStudentId(Integer id);

    List<Integer> getAllStudentIds();
}
