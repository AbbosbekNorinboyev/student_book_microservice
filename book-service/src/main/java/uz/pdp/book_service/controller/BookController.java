package uz.pdp.book_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.book_service.dto.BookDto;
import uz.pdp.book_service.dto.response.Response;
import uz.pdp.book_service.service.BookService;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public Response<?> createBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @GetMapping("/{id}")
    public Response<?> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public Response<?> getAllBook() {
        return bookService.getAllBook();
    }

    @PutMapping
    public Response<?> updateBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteBook(@PathVariable Long id) {
        return bookService.deleteBookById(id);
    }

    @DeleteMapping("/studentId/{studentId}")
    public Response<?> deleteBookByStudentId(@PathVariable Integer studentId) {
        return bookService.deleteBookByStudentId(studentId);
    }
}
