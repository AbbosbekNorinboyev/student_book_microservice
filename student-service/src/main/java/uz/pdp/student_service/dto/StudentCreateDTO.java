package uz.pdp.student_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StudentCreateDTO {
    private Long id;
    @NotBlank(message = "FirstName can not be null or empty")
    private String firstname;
    @NotBlank(message = "LastName can not be null or empty")
    private String lastname;
    @NotNull(message = "Age can not be null or empty")
//    @Positive(message = "The number must be positive")
    private int age;
}
