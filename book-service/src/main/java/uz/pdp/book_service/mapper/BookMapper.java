package uz.pdp.book_service.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.book_service.dto.BookDto;
import uz.pdp.book_service.entity.Book;

@Component
public class BookMapper {
    public Book toEntity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .page(bookDto.getPage())
                .build();
    }

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .page(book.getPage())
                .studentId(book.getStudentId())
                .build();
    }
}
