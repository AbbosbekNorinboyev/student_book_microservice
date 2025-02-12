package uz.pdp.book_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.book_service.dto.ApiResponse;
import uz.pdp.book_service.dto.BookCreateDTO;
import uz.pdp.book_service.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ApiResponse<BookCreateDTO> createBook(@Valid @RequestBody BookCreateDTO bookCreateDTO) {
        return bookService.createBook(bookCreateDTO);
    }

    @GetMapping("/{id}")
    public ApiResponse<BookCreateDTO> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public ApiResponse<List<BookCreateDTO>> getAllBook() {
        return bookService.getAllBook();
    }

    @PutMapping
    public ApiResponse<BookCreateDTO> updateBook(@Valid @RequestBody BookCreateDTO bookCreateDTO) {
        return bookService.updateBook(bookCreateDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable Long id) {
        return bookService.deleteBookById(id);
    }

    @DeleteMapping("/studentId/{studentId}")
    public ApiResponse<Void> deleteBookByStudentId(@PathVariable Long studentId) {
        return bookService.deleteBookByStudentId(studentId);
    }

}
