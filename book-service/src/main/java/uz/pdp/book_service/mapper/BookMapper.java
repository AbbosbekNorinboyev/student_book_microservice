package uz.pdp.book_service.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.book_service.dto.BookCreateDTO;
import uz.pdp.book_service.entity.Book;

@Component
public class BookMapper {
    public Book toEntity(BookCreateDTO bookCreateDTO) {
        return Book.builder()
                .id(bookCreateDTO.getId())
                .title(bookCreateDTO.getTitle())
                .page(bookCreateDTO.getPage())
                .studentId(bookCreateDTO.getStudentId())
                .build();
    }

    public BookCreateDTO toDto(Book book) {
        return BookCreateDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .page(book.getPage())
                .studentId(book.getStudentId())
                .build();
    }
}
