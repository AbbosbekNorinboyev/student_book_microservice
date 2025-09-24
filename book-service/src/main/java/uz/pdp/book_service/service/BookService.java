package uz.pdp.book_service.service;

import uz.pdp.book_service.dto.ApiResponse;
import uz.pdp.book_service.dto.BookCreateDTO;

import java.util.List;

public interface BookService {
    ApiResponse<BookCreateDTO> createBook(BookCreateDTO bookCreateDTO);

    ApiResponse<BookCreateDTO> getBookById(Long id);

    ApiResponse<List<BookCreateDTO>> getAllBook();

    ApiResponse<BookCreateDTO> updateBook(BookCreateDTO bookCreateDTO);

    ApiResponse<Void> deleteBookById(Long studentId);

    ApiResponse<Void> deleteBookByStudentId(Long id);

    List<Integer> getAllStudentIds();
}
