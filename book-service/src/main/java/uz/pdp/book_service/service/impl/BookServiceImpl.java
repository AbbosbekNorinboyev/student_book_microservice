package uz.pdp.book_service.service.impl;

import org.slf4j.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.book_service.config.StudentClient;
import uz.pdp.book_service.dto.BookDto;
import uz.pdp.book_service.dto.response.Response;
import uz.pdp.book_service.entity.Book;
import uz.pdp.book_service.exception.ResourceNotFoundException;
import uz.pdp.book_service.mapper.BookMapper;
import uz.pdp.book_service.mapper.interfaces.BookInterfaceMapper;
import uz.pdp.book_service.repository.BookRepository;
import uz.pdp.book_service.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookInterfaceMapper bookInterfaceMapper;
    private final StudentClient studentClient;
    private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public Response<?> createBook(BookDto bookDto) {
        try {
            Book book;
            List<Integer> allStudentIds = getAllStudentIds();
            if (allStudentIds.contains(bookDto.getStudentId())) {
                book = bookMapper.toEntity(bookDto);
                bookRepository.save(book);
            } else {
                return Response.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .message("Student not found: " + bookDto.getStudentId())
                        .success(false)
                        .build();
            }
            logger.info("Book successfully saved");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Ok")
                    .success(true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Response<?> getBookById(Long id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
            logger.info("Book successfully found: " + id);
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Ok")
                    .success(true)
                    .data(bookInterfaceMapper.toDto(book))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Response<?> getAllBook() {
        List<Book> books = bookRepository.findAll();
        logger.info("Book list successfully saved");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Ok")
                .success(true)
                .data(books.stream().map(bookMapper::toDto).toList())
                .build();
    }

    @Override
    public Response<?> updateBook(BookDto bookDto) {
        Book existingBook = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookDto.getId()));
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setPage(bookDto.getPage());
        bookRepository.save(existingBook);
        logger.info("Book successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Book successfully updated")
                .success(true)
                .data(bookInterfaceMapper.toDto(existingBook))
                .build();
    }

    @Override
    public Response<?> deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        bookRepository.delete(book);
        logger.info("Book successfully deleted");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Book successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public Response<?> deleteBookByStudentId(Long studentId) {
        bookRepository.deleteBookByStudentId(studentId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Book successfully deleted by studentId")
                .success(true)
                .build();
    }

    @Override
    public List<Integer> getAllStudentIds() {
        return studentClient.getIds();
    }
}
