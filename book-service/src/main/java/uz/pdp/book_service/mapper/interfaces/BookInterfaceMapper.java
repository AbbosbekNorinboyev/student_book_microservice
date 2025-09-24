package uz.pdp.book_service.mapper.interfaces;

import org.mapstruct.*;
import uz.pdp.book_service.dto.BookDto;
import uz.pdp.book_service.entity.Book;

@Mapper(componentModel = "spring")
public interface BookInterfaceMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "page", target = "page")
    @Mapping(source = "studentId", target = "studentId")
    Book toEntity(BookDto bookDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "page", target = "page")
    @Mapping(source = "studentId", target = "studentId")
    BookDto toDto(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "page", target = "page")
    @Mapping(source = "studentId", target = "studentId")
    void updateToEntityFromDto(@MappingTarget Book book, BookDto bookDto);

}
