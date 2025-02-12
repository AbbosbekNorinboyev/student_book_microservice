package uz.pdp.book_service.service.impl;

import org.slf4j.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pdp.book_service.config.StudentClient;
import uz.pdp.book_service.dto.ApiResponse;
import uz.pdp.book_service.dto.BookCreateDTO;
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
    public ApiResponse<BookCreateDTO> createBook(BookCreateDTO bookCreateDTO) {
        try {
            Book book;
            List<Integer> allStudentIds = getAllStudentIds();
            if (allStudentIds.contains(bookCreateDTO.getStudentId())) {
                book = bookMapper.toEntity(bookCreateDTO);
                bookRepository.save(book);
            } else {
                return ApiResponse.<BookCreateDTO>builder()
                        .code(-1)
                        .message("Student not found: " + bookCreateDTO.getStudentId())
                        .success(false)
                        .build();
            }
            logger.info("Book successfully saved");
            return ApiResponse.<BookCreateDTO>builder()
                    .code(200)
                    .message("Ok")
                    .success(true)
                    .data(bookMapper.toDto(book))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ApiResponse<BookCreateDTO> getBookById(Long id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
            logger.info("Book successfully found: " + id);
            return ApiResponse.<BookCreateDTO>builder()
                    .code(200)
                    .message("Ok")
                    .success(true)
                    .data(bookInterfaceMapper.toDto(book))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<BookCreateDTO>> getAllBook() {
        List<Book> books = bookRepository.findAll();
        logger.info("Book list successfully saved");
        return ApiResponse.<List<BookCreateDTO>>builder()
                .code(200)
                .message("Ok")
                .success(true)
                .data(books.stream().map(bookMapper::toDto).toList())
                .build();
    }

    @Override
    public ApiResponse<BookCreateDTO> updateBook(BookCreateDTO bookCreateDTO) {
        Book existingBook = bookRepository.findById(bookCreateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookCreateDTO.getId()));
        existingBook.setTitle(bookCreateDTO.getTitle());
        existingBook.setPage(bookCreateDTO.getPage());
        bookRepository.save(existingBook);
        logger.info("Book successfully updated");
        return ApiResponse.<BookCreateDTO>builder()
                .code(200)
                .message("Book successfully updated")
                .success(true)
                .data(bookInterfaceMapper.toDto(existingBook))
                .build();
    }

    @Override
    public ApiResponse<Void> deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        bookRepository.delete(book);
        logger.info("Book successfully deleted");
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Book successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteBookByStudentId(Long studentId) {
        bookRepository.deleteBookByStudentId(studentId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Book successfully deleted by studentId")
                .success(true)
                .build();
    }

    @Override
    public List<Integer> getAllStudentIds() {
        return studentClient.getIds();
    }
}
