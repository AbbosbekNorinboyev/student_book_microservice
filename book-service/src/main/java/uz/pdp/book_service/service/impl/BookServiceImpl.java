package uz.pdp.book_service.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.book_service.config.StudentClient;
import uz.pdp.book_service.dto.BookDto;
import uz.pdp.book_service.dto.response.ErrorResponse;
import uz.pdp.book_service.dto.response.Response;
import uz.pdp.book_service.entity.Book;
import uz.pdp.book_service.exception.ResourceNotFoundException;
import uz.pdp.book_service.mapper.BookMapper;
import uz.pdp.book_service.mapper.interfaces.BookInterfaceMapper;
import uz.pdp.book_service.repository.BookRepository;
import uz.pdp.book_service.service.BookService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.book_service.util.Util.localDateTimeFormatter;

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
                book.setStudentId(bookDto.getStudentId());
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
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
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
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
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
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
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
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @CircuitBreaker(name = "studentServiceCircuitBreaker", fallbackMethod = "fallbackStudentService")
    @Override
    public Response<?> deleteBookByStudentId(Integer studentId) {
        List<Integer> allStudentIds = getAllStudentIds();
        if (allStudentIds.contains(studentId)) {
            bookRepository.deleteBookByStudentId(Long.valueOf(studentId));
        } else {
            return Response.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .message("Student not found: " + studentId)
                    .success(false)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Book successfully deleted by studentId")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    public Response<?> fallbackStudentService(Integer studentId, Throwable throwable) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Student Service ishlamayapti, fallbackdan qaytdi. studentId = " + studentId)
                .build();
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .errors(List.of(errorResponse))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public List<Integer> getAllStudentIds() {
        return studentClient.getIds();
    }
}
