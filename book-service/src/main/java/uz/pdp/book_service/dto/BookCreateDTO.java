package uz.pdp.book_service.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class BookCreateDTO {
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
