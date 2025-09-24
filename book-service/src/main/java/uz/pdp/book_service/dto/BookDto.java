package uz.pdp.book_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {
    private Long id;
    @NotBlank(message = "Title can not be null or empty")
    private String title;
    @NotNull(message = "Page can not be null or empty")
    @Positive(message = "The number must be positive")
    private int page;
    @NotNull(message = "studentId is required")
    @Positive(message = "The number must be positive")
    private Integer studentId;
}
